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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An {@link ImportBeanDefinitionRegistrar} implementation that finds and registers Proteus bean definitions.
 */
public class ProteusImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProteusImportBeanDefinitionRegistrar.class);
    private static final String PROTEUS_SPRINGBOOT_BASE_PACKAGE = "com.netifi.proteus.springboot";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        findBasePackages(importingClassMetadata)
                .forEach(basePackage -> {
                    LOGGER.debug("Scanning Package for Proteus Bean Definitions: {}", basePackage);

                    registerProteusServices(basePackage, registry);
                });
    }

    /**
     * Discovers which base packages should be scanned for Proteus bean definitions.
     *
     * @param importingClassMetadata class annotated with the @Import annotation that triggered
     *                               this bean definition registrar
     * @return a set containing the name of each base package that should be scanned for
     * Proteus bean definitions
     */
    private Set<String> findBasePackages(AnnotationMetadata importingClassMetadata) {
        Set<String> basePackages = new HashSet<>();

        // Default base package scanning to the package of the importing class
        basePackages.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));

        // Make sure proteus classes are scanned
        basePackages.add(PROTEUS_SPRINGBOOT_BASE_PACKAGE);

        // Check for package scanning directives on the SpringBootApplication annotation
        if (importingClassMetadata.hasAnnotation(SpringBootApplication.class.getName())) {
            Map<String, Object> annotationValues = importingClassMetadata.getAnnotationAttributes(SpringBootApplication.class.getName());

            // Getting any packages defined by the "scanBasePackages" attribute
            if (annotationValues.containsKey("scanBasePackages")) {
                basePackages.addAll(Arrays.asList((String[]) annotationValues.get("scanBasePackages")));
            }

            // Getting any packages defined by the "scanBasePackageClasses" attribute
            if (annotationValues.containsKey("scanBasePackageClasses")) {
                Arrays.asList((Class<?>[]) annotationValues.get("scanBasePackageClasses"))
                        .forEach(clazz -> basePackages.add(ClassUtils.getPackageName(clazz)));
            }
        }

        return basePackages;
    }

    private void registerProteusServices(String basePackage, BeanDefinitionRegistry registry) {
        ProteusClassPathScanningCandidateComponentProvider serviceScanner = new ProteusClassPathScanningCandidateComponentProvider();

        serviceScanner.findCandidateComponents(basePackage)
                .forEach(beanDefinition -> {
                    LOGGER.debug("Registering Proteus Bean: {}", beanDefinition.getBeanClassName());
                    registry.registerBeanDefinition(beanDefinition.getBeanClassName(), beanDefinition);
                });
    }
}
