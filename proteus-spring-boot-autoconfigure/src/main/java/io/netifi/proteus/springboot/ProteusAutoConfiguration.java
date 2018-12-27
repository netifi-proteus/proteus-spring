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
package io.netifi.proteus.springboot;

import java.util.List;
import java.util.Optional;

import io.micrometer.core.instrument.MeterRegistry;
import io.netifi.proteus.Proteus;
import io.netifi.proteus.micrometer.ProteusMeterRegistrySupplier;
import io.netifi.proteus.spring.core.config.ProteusConfiguration;
import io.netifi.proteus.tracing.ProteusTracerSupplier;
import io.opentracing.Tracer;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

@SpringBootConfiguration
@EnableConfigurationProperties(ProteusProperties.class)
@AutoConfigureBefore(ProteusConfiguration.class)
public class ProteusAutoConfiguration {

    @Bean(name = "internalScanClassPathBeanDefinitionRegistryPostProcessor")
    public BeanDefinitionRegistryPostProcessor scanClassPathBeanDefinitionRegistryPostProcessor(ApplicationContext applicationContext) throws BeansException {
        return new ScanClassPathBeanDefinitionRegistryPostProcessor();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ProteusConfigurer propertiesBasedProteusConfigurer(ProteusProperties proteusProperties) {
        return builder -> {
            ProteusProperties.SslProperties ssl = proteusProperties.getSsl();
            ProteusProperties.AccessProperties access = proteusProperties.getAccess();
            ProteusProperties.BrokerProperties broker = proteusProperties.getBroker();

            if (!StringUtils.isEmpty(proteusProperties.getDestination())) {
                builder.destination(proteusProperties.getDestination());
            }

            return builder.sslDisabled(ssl.isDisabled())
                          .accessKey(access.getKey())
                          .accessToken(access.getToken())
                          .group(proteusProperties.getGroup())
                          .poolSize(proteusProperties.getPoolSize())
                          .host(broker.getHostname())
                          .port(broker.getPort());
        };
    }

    @SpringBootConfiguration
    @ConditionalOnMissingBean(MeterRegistry.class)
    @ConditionalOnClass(ProteusMeterRegistrySupplier.class)
    public static class MetricsConfigurations {

        @Bean
        public MeterRegistry meterRegistry(
            Proteus proteus,
            ProteusProperties properties
        ) {
            return new ProteusMeterRegistrySupplier(
                proteus,
                Optional.of(properties.getMetrics().getGroup()),
                Optional.of(properties.getMetrics().getReportingStepInMillis()),
                Optional.of(properties.getMetrics().isExport())
            ).get();
        }
    }


    @SpringBootConfiguration
    @ConditionalOnMissingBean(Tracer.class)
    @ConditionalOnClass(ProteusTracerSupplier.class)
    public static class TracingConfigurations {

        @Bean
        public Tracer tracer(
            Proteus proteus,
            ProteusProperties properties
        ) {
            return new ProteusTracerSupplier(
                proteus,
                Optional.of(properties.getTracing().getGroup())
            ).get();
        }
    }

    @SpringBootConfiguration
    @ConditionalOnNotWebApplication
    @ConditionalOnMissingBean(Proteus.class)
    public static class NonWebProteusConfiguration {

        @Bean
        public Proteus proteus(
            List<ProteusConfigurer> configurers
        ) {
            Proteus proteus = configureProteus(configurers);

            startDaemonAwaitThread(proteus);

            return proteus;
        }

        private void startDaemonAwaitThread(Proteus proteus) {
            Thread awaitThread = new Thread("proteus") {

                @Override
                public void run() {
                    proteus.onClose().block();
                }

            };
            awaitThread.setContextClassLoader(getClass().getClassLoader());
            awaitThread.setDaemon(false);
            awaitThread.start();
        }
    }

    @SpringBootConfiguration
    @ConditionalOnWebApplication
    @ConditionalOnMissingBean(Proteus.class)
    public static class WebProteusConfiguration {

        @Bean
        public Proteus proteus(
            List<ProteusConfigurer> configurers
        ) {
            return configureProteus(configurers);
        }
    }


    static Proteus configureProteus(List<ProteusConfigurer> configurers) {
        Proteus.Builder builder = Proteus.builder();

        AnnotationAwareOrderComparator.sort(configurers);

        for (ProteusConfigurer configurer : configurers) {
            builder = configurer.configure(builder);
        }

        return builder.build();
    }
}
