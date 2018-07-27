/**
 * Copyright 2018 Netifi Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netifi.proteus.springboot.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.netifi.proteus.Proteus;
import io.netifi.proteus.annotations.ProteusClient;
import io.netifi.proteus.rsocket.ProteusSocket;
import io.opentracing.Tracer;
import io.rsocket.RSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.ResolvableType;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Processes custom dependency injection for fields marked with the {@link ProteusClient} annotation.
 */
public class ProteusClientFieldCallback implements ReflectionUtils.FieldCallback {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProteusClientFieldCallback.class);

    private ConfigurableListableBeanFactory beanFactory;
    private Object bean;

    public ProteusClientFieldCallback(final ConfigurableListableBeanFactory beanFactory, final Object bean) {
        this.beanFactory = beanFactory;
        this.bean = bean;
    }

    @Override
    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
        if (!field.isAnnotationPresent(ProteusClient.class)) {
            return;
        }

        LOGGER.debug("Postprocessing field '{}' annotated with @ProteusClient", field);

        ReflectionUtils.makeAccessible(field);

        final String group = field.getAnnotation(ProteusClient.class).group();
        final String destination = field.getAnnotation(ProteusClient.class).destination();
        final String beanName = getBeanName(field);
        final Object beanInstance = getBeanInstance(beanName, field.getType(), group, destination);

        field.set(bean, beanInstance);
    }

    /**
     * Generates a unique bean name for the field.
     *
     * @param field field metadata
     * @return bean name
     */
    private String getBeanName(Field field) {
        if (field.isAnnotationPresent(ProteusClient.class)) {
            String group = field.getAnnotation(ProteusClient.class).group();
            String destination = field.getAnnotation(ProteusClient.class).destination();

            String beanName = field.getType().getSimpleName() + "_" + group;

            if (!StringUtils.isEmpty(destination)) {
                beanName += "_" + destination;
            }

            return beanName;
        } else {
            return field.getType().getSimpleName();
        }
    }

    /**
     * Creates an instance of the correct Proteus client for injection into an annotated field.
     *
     * @param beanName name of bean to inject
     * @param clientClass proteus client class
     * @param group proteus client group
     * @param destination proteus client destination
     * @return an instance of a Proteus client
     */
    private Object getBeanInstance(final String beanName, final Class<?> clientClass, final String group, final String destination) {
        if (!beanFactory.containsBean(beanName)) {
            Proteus proteus = beanFactory.getBean(Proteus.class);

            // Creating ProteusSocket Instance
            ProteusSocket proteusSocket = null;
            if (StringUtils.isEmpty(destination)) {
                proteusSocket = proteus.group(group);
            } else {
                proteusSocket = proteus.destination(destination, group);
            }

            Object toRegister = null;
            try {
                String[] tracerSupplierBeanNames = beanFactory.getBeanNamesForType(ResolvableType.forClassWithGenerics(Supplier.class, Tracer.class));
                String[] meterRegistrySupplierBeanNames = beanFactory.getBeanNamesForType(ResolvableType.forClassWithGenerics(Supplier.class, MeterRegistry.class));

                Tracer tracer = null;
                MeterRegistry meterRegistry = null;

                // Tracers
                if (tracerSupplierBeanNames.length >= 1) {
                    if (tracerSupplierBeanNames.length > 1) {
                        LOGGER.warn("More than one implementation of Tracer detected on the classpath. Arbitrarily choosing one to use.");
                    }

                    Supplier<Tracer> tracerSupplier = (Supplier<Tracer>) beanFactory.getBean(tracerSupplierBeanNames[0]);
                    tracer = tracerSupplier.get();
                }

                // Meter Registries
                if (meterRegistrySupplierBeanNames.length >= 1) {
                    if (meterRegistrySupplierBeanNames.length > 1) {
                        LOGGER.warn("More than one implementation of MeterRegistry detected on the classpath. Arbitrarily choosing one to use.");
                    }

                    Supplier<MeterRegistry> meterRegistrySupplier = (Supplier<MeterRegistry>) beanFactory.getBean(meterRegistrySupplierBeanNames[0]);
                    meterRegistry = meterRegistrySupplier.get();
                } else {
                    // Fallback to MeterRegistry implementations on the classpath if we can't find any suppliers
                    Map<String, MeterRegistry> meterRegistryBeans = beanFactory.getBeansOfType(MeterRegistry.class);

                    if (!meterRegistryBeans.isEmpty()) {
                        if (meterRegistryBeans.size() > 1) {
                            LOGGER.warn("More than one implementation of MeterRegistry detected on the classpath. Arbitrarily choosing one to use.");
                        }

                        meterRegistry = (MeterRegistry) meterRegistryBeans.values().toArray()[0];
                    }
                }

                if (tracer == null && meterRegistry == null) {
                    // No Tracer or MeterRegistry
                    Constructor ctor = clientClass.getConstructor(RSocket.class);
                    toRegister = ctor.newInstance(proteusSocket);
                } else if (tracer != null && meterRegistry == null) {
                    // Tracer Only
                    Constructor ctor = clientClass.getConstructor(RSocket.class, Tracer.class);
                    toRegister = ctor.newInstance(proteusSocket, tracer);
                } else if (tracer == null && meterRegistry != null) {
                    // MeterRegistry Only
                    Constructor ctor = clientClass.getConstructor(RSocket.class, MeterRegistry.class);
                    toRegister = ctor.newInstance(proteusSocket, meterRegistry);
                } else {
                    // Both Tracer and MeterRegistry
                    Constructor ctor = clientClass.getConstructor(RSocket.class, MeterRegistry.class, Tracer.class);
                    toRegister = ctor.newInstance(proteusSocket, meterRegistry, tracer);
                }
            } catch (Exception e) {
                throw new RuntimeException(String.format("Error injecting bean '%s'", clientClass.getSimpleName()), e);
            }

            Object newInstance = beanFactory.initializeBean(toRegister, beanName);
            beanFactory.autowireBeanProperties(newInstance, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, true);
            beanFactory.registerSingleton(beanName, newInstance);

            LOGGER.debug("Bean named '{}' created successfully.", beanName);

            return newInstance;
        } else {
            LOGGER.debug("Bean named '{}' already exists, using as current bean reference.", beanName);
            return beanFactory.getBean(beanName);
        }
    }
}
