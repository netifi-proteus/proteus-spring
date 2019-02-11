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
package io.netifi.proteus.spring.core.annotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import io.micrometer.core.instrument.MeterRegistry;
import io.netifi.proteus.Proteus;
import io.netifi.proteus.common.tags.Tags;
import io.netifi.proteus.rsocket.ProteusSocket;
import io.netifi.proteus.spring.core.*;
import io.opentracing.Tracer;
import io.rsocket.RSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AutowireCandidateQualifier;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.ResolvableType;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Processes custom dependency injection for fields marked with the {@link ProteusClient} annotation.
 */
public class ProteusClientStaticFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProteusClientStaticFactory.class);

    /**
     * Creates an instance of the correct Proteus client for injection into an annotated field.
     *
     * @param targetClass proteus client class
     * @return an instance of a Proteus client
     */
    public static Object getBeanInstance(
        final DefaultListableBeanFactory beanFactory,
        final Class<?> targetClass,
        final ProteusClient proteusClientAnnotation
    ) {
        final String beanName = getBeanName(proteusClientAnnotation, targetClass);

        if (!beanFactory.containsBean(beanName)) {
            Proteus proteus = beanFactory.getBean(Proteus.class);

            //Tags reconciliation
            TagSupplier tagSupplier = new NoTagsSupplier();
            if(proteusClientAnnotation.tagSupplier() != null && !proteusClientAnnotation.tagSupplier().equals(NoTagsSupplier.class)){
                tagSupplier = beanFactory.getBean(proteusClientAnnotation.tagSupplier());
            }

            Tags suppliedTags = tagSupplier.get();
            for (Tag t :
                    proteusClientAnnotation.tags()) {
                if(!suppliedTags.stream().anyMatch(tag -> tag.getKey().equals(t.name()))){
                    suppliedTags = suppliedTags.and(io.netifi.proteus.common.tags.Tag.of(t.name(), t.value()));
                }
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

                Class<?> clientClass = proteusClientAnnotation.clientClass();
                if(targetClass.isAssignableFrom(ProteusClientFactory.class)){

                    if(clientClass.equals(NoClass.class)){
                        throw new RuntimeException("Instantiating ProteusClientFactory requires target client class");
                    }

                    ProteusClientFactory baseFactory = createBaseClientFactory(clientClass, proteus,proteusClientAnnotation.type(),
                            proteusClientAnnotation.group(),
                            proteusClientAnnotation.destination(),
                            suppliedTags,
                            tracer,
                            meterRegistry);

                    //TODO: Lots of duplication here but I don't know how else to do this
                    if(targetClass.isAssignableFrom(GroupAwareClientFactory.class)){
                        toRegister = new GroupAwareClientFactory(){
                            @Override
                            public Object lookup(ProteusClient.Type type, String group, Tags tags) {
                                return baseFactory.lookup(type, group, tags);
                            }

                            @Override
                            public Object lookup(ProteusClient.Type type, String group, String... tags) {
                                return baseFactory.lookup(type, group, tags);
                            }

                            @Override
                            public Object lookup(ProteusClient.Type type) {
                                return baseFactory.lookup(type);
                            }

                            @Override
                            public Object lookup(ProteusClient.Type type, Tags tags) {
                                return baseFactory.lookup(type, tags);
                            }
                        };
                    } else if(targetClass.isAssignableFrom(DestinationAwareClientFactory.class)){
                        toRegister = new DestinationAwareClientFactory() {
                            @Override
                            public Object lookup(ProteusClient.Type type, String group, Tags tags) {
                                return baseFactory.lookup(type, group, tags);
                            }

                            @Override
                            public Object lookup(ProteusClient.Type type, String group, String... tags) {
                                return baseFactory.lookup(type, group, tags);
                            }

                            @Override
                            public Object lookup(ProteusClient.Type type) {
                                return baseFactory.lookup(type);
                            }

                            @Override
                            public Object lookup(ProteusClient.Type type, Tags tags) {
                                return baseFactory.lookup(type, tags);
                            }
                        };
                    } else if(targetClass.isAssignableFrom(BroadcastAwareClientFactory.class)){
                        toRegister = new BroadcastAwareClientFactory() {
                            @Override
                            public Object lookup(ProteusClient.Type type, String group, Tags tags) {
                                return baseFactory.lookup(type, group, tags);
                            }

                            @Override
                            public Object lookup(ProteusClient.Type type, String group, String... tags) {
                                return baseFactory.lookup(type, group, tags);
                            }

                            @Override
                            public Object lookup(ProteusClient.Type type) {
                                return baseFactory.lookup(type);
                            }

                            @Override
                            public Object lookup(ProteusClient.Type type, Tags tags) {
                                return baseFactory.lookup(type, tags);
                            }
                        };
                    } else {
                        toRegister = baseFactory;
                    }

                } else {
                    toRegister = createProteusClient(proteus,
                            proteusClientAnnotation.type(),
                            proteusClientAnnotation.group(),
                            proteusClientAnnotation.destination(),
                            suppliedTags,
                            tracer,
                            meterRegistry,
                            targetClass);
                }
            } catch (Exception e) {
                throw new RuntimeException(String.format("Error injecting bean '%s'", targetClass.getSimpleName()), e);
            }

            Object newInstance = beanFactory.initializeBean(toRegister, beanName);
            beanFactory.autowireBeanProperties(newInstance, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, true);
            AnnotatedGenericBeanDefinition beanDefinition = new AnnotatedGenericBeanDefinition(targetClass);
            AutowireCandidateQualifier qualifier = new AutowireCandidateQualifier(Qualifier.class);

            qualifier.setAttribute("value", "client");
            beanDefinition.addQualifier(qualifier);

            beanFactory.registerBeanDefinition(beanName, beanDefinition);
            beanFactory.registerSingleton(beanName, newInstance);

            LOGGER.debug("Bean named '{}' created successfully.", beanName);

            return newInstance;
        } else {
            LOGGER.debug("Bean named '{}' already exists, using as current bean reference.", beanName);
            return beanFactory.getBean(beanName);
        }
    }

    /**
     * Generates a unique bean name for the field.
     *
     * @param proteusClientAnnotation annotation data
     * @param clazz target class
     * @return bean name
     */
    private static String getBeanName(
            ProteusClient proteusClientAnnotation,
            Class<?> clazz
    ) {
        Assert.hasText(proteusClientAnnotation.group(), "@ProteusClient.group() must be specified");

        String beanName =
                clazz.getSimpleName() + "_" + proteusClientAnnotation.type().toString().toLowerCase() + "_" + proteusClientAnnotation.group();

        if (!StringUtils.isEmpty(proteusClientAnnotation.destination())) {
            beanName += "_" + proteusClientAnnotation.destination();
        }

        return beanName;
    }

    private static <T> T createProteusClient(Proteus proteus,
                                              ProteusClient.Type routeType,
                                              String group,
                                              String destination,
                                              Tags tags,
                                              Tracer tracer,
                                              MeterRegistry meterRegistry,
                                              Class<T> clientClass)
                                              throws NoSuchMethodException,
                                                     InstantiationException,
                                                     IllegalAccessException,
                                                     InvocationTargetException {
        // Creating default ProteusSocket Instance
        ProteusSocket proteusSocket = null;

        switch (routeType) {
            case BROADCAST:
                proteusSocket = proteus.broadcastServiceSocket(group, tags);
                break;
            case GROUP:
                proteusSocket = proteus.groupServiceSocket(group, tags);
                break;
            case DESTINATION:
                proteusSocket = proteus.groupServiceSocket(group,
                        tags.and(Tags.of("destination", destination)));
                break;
        }

        T toRegister;

        if (tracer == null && meterRegistry == null) {
            // No Tracer or MeterRegistry
            Constructor ctor = clientClass.getConstructor(RSocket.class);
            toRegister = (T)ctor.newInstance(proteusSocket);
        } else if (tracer != null && meterRegistry == null) {
            // Tracer Only
            Constructor ctor = clientClass.getConstructor(RSocket.class, Tracer.class);
            toRegister = (T)ctor.newInstance(proteusSocket, tracer);
        } else if (tracer == null && meterRegistry != null) {
            // MeterRegistry Only
            Constructor ctor = clientClass.getConstructor(RSocket.class, MeterRegistry.class);
            toRegister = (T)ctor.newInstance(proteusSocket, meterRegistry);
        } else {
            // Both Tracer and MeterRegistry
            Constructor ctor = clientClass.getConstructor(RSocket.class, MeterRegistry.class, Tracer.class);
            toRegister = (T)ctor.newInstance(proteusSocket, meterRegistry, tracer);
        }
        return toRegister;
    }

    private static ProteusClientFactory createBaseClientFactory(Class<?> clientClass,
                                                                Proteus proteus,
                                                                ProteusClient.Type routeType,
                                                                String group,
                                                                String destination,
                                                                Tags tags,
                                                                Tracer tracer,
                                                                MeterRegistry meterRegistry) {

        ProteusClientFactory baseFactory = new ProteusClientFactory(){

            Map<String, Object> instantiatedClients = new HashMap<>();

            @Override
            public Object lookup(ProteusClient.Type type, String methodGroup, Tags methodTags) {
                String key = key(type, methodGroup, methodTags);
                Object client = null;
                if(instantiatedClients.containsKey(key)){
                    client = instantiatedClients.get(key);
                } else {
                    try {
                        client = createProteusClient(proteus, routeType, methodGroup, destination, methodTags, tracer, meterRegistry, clientClass);
                        instantiatedClients.put(key, client);
                    } catch (Exception e) {
                        throw new RuntimeException(String.format("Error instantiating Proteus Client for '%s'", clientClass.getSimpleName()), e);
                    }
                }
                return client;
            }

            @Override
            public Object lookup(ProteusClient.Type type, String methodGroup, String... methodTags) {
                return lookup(type, methodGroup, Tags.of(methodTags));
            }

            @Override
            public Object lookup(ProteusClient.Type type) {
                return lookup(type, group, tags);
            }

            @Override
            public Object lookup(ProteusClient.Type type, Tags methodTags) {
                return lookup(type, group, methodTags);
            }

            //TODO: Do something smarter
            private String key(ProteusClient.Type type, String group, Tags tags){
                return type.name() + group + destination + tags.hashCode();
            }
        };

        return baseFactory;
    }
}
