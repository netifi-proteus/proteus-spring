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
import io.rsocket.RSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

            Map<String, MeterRegistry> metricsBeans = beanFactory.getBeansOfType(MeterRegistry.class);

            Object toRegister = null;
            try {
                // Determine if the bean registry contains a MeterRegistry implementation that needs to be set in the client constructor
                if (metricsBeans.isEmpty()) {
                    Constructor ctor = clientClass.getConstructor(RSocket.class);
                    toRegister = ctor.newInstance(proteusSocket);
                } else {
                    List<MeterRegistry> meterRegistries = new ArrayList<>(metricsBeans.values());

                    Constructor ctor = clientClass.getConstructor(RSocket.class, MeterRegistry.class);
                    toRegister = ctor.newInstance(proteusSocket, meterRegistries.get(0));
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
