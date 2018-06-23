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

import io.netifi.proteus.annotations.ProteusService;
import io.netifi.proteus.AbstractProteusService;
import io.netifi.proteus.annotations.internal.ProteusGenerated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class ProteusBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProteusBeanDefinitionRegistryPostProcessor.class);

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) registry;

//        // Get metadata for all classes annotated with ProteusService
//        Set<Class<?>> proteusServiceClasses = new HashSet<>();
//        for (String serviceBeanName : defaultListableBeanFactory.getBeanNamesForAnnotation(ProteusService.class)) {
//            String beanClassName = registry.getBeanDefinition(serviceBeanName).getBeanClassName();
//
//            try {
//                proteusServiceClasses.add(Class.forName(beanClassName));
//            } catch (ClassNotFoundException e) {
//                LOGGER.error("Error during post processing of Proteus beans", e);
//            }
//        }

        for (String serviceServerBeanName : defaultListableBeanFactory.getBeanNamesForType(AbstractProteusService.class)) {
            try {
                Class<?> clazz = Class.forName(registry.getBeanDefinition(serviceServerBeanName).getBeanClassName());
                if (clazz.isAnnotationPresent(ProteusGenerated.class)) {
                    ProteusGenerated proteusGeneratedAnnotation = clazz.getAnnotation(ProteusGenerated.class);
                    Class<?> idlClazz = proteusGeneratedAnnotation.idlClass();

                    if(defaultListableBeanFactory.getBeanNamesForType(idlClazz).length <= 0) {
                        registry.removeBeanDefinition(serviceServerBeanName);
                    }
                }
            } catch (ClassNotFoundException e) {
                LOGGER.error("Error during post processing of Proteus beans", e);
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
