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
package com.netifi.proteus.springboot;

import java.util.function.Supplier;

import com.netifi.proteus.spring.core.config.ProteusConfiguration;
import io.micrometer.core.instrument.MeterRegistry;
import io.netifi.proteus.Proteus;
import io.opentracing.Tracer;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;

@SpringBootConfiguration
@EnableConfigurationProperties(ProteusProperties.class)
@Import(ProteusConfiguration.class)
public class ProteusAutoConfiguration {

    @Bean
    @Conditional(MeterRegistrySupplierCondition.class)
    public MeterRegistry meterRegistry(Supplier<MeterRegistry> supplier) {
        return supplier.get();
    }

    @Bean
    @Conditional(TracerSupplierCondition.class)
    public Tracer tracer(Supplier<Tracer> supplier) {
        return supplier.get();
    }

    @SpringBootConfiguration
    @ConditionalOnNotWebApplication
    @ConditionalOnMissingBean(Proteus.class)
    public static class NonWebProteusConfiguration {

        @Bean
        public Proteus proteus(ProteusProperties proteusProperties) {
            Proteus proteus = configureProteus(proteusProperties);

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
        public Proteus proteus(ProteusProperties proteusProperties) {
            return configureProteus(proteusProperties);
        }
    }


    static Proteus configureProteus(ProteusProperties proteusProperties) {
        ProteusProperties.AccessProperties access = proteusProperties.getAccess();
        ProteusProperties.BrokerProperties broker = proteusProperties.getBroker();

        Proteus.Builder builder = Proteus.builder();

        if (!StringUtils.isEmpty(proteusProperties.getDestination())) {
            builder.destination(proteusProperties.getDestination());
        }

        return builder
                .accessKey(access.getKey())
                .accessToken(access.getToken())
                .group(proteusProperties.getGroup())
                .poolSize(proteusProperties.getPoolSize())
                .host(broker.getHostname())
                .port(broker.getPort())
                .build();
    }
}
