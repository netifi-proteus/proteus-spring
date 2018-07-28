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

import com.netifi.proteus.springboot.EnableProteus;
import com.netifi.proteus.springboot.ProteusRunner;
import io.micrometer.core.instrument.MeterRegistry;
import io.netifi.proteus.Proteus;
import io.opentracing.Tracer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.function.Supplier;

@Configuration
@ComponentScan(basePackages = { "com.netifi.proteus.springboot", "io.netifi.proteus" })
public class ProteusAutoConfiguration implements ImportAware {
    protected final ProteusSettings settings;
    protected AnnotationAttributes enableProteus;

    public ProteusAutoConfiguration(ProteusSettings settings) {
        this.settings = settings;
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        this.enableProteus = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableProteus.class.getName(), false));
    }

    @Bean
    @ConditionalOnMissingBean
    public Proteus proteus() {
        Proteus.Builder builder = Proteus.builder();

        if (!StringUtils.isEmpty(enableProteus.getString("destination"))) {
            builder.destination(enableProteus.getString("destination"));
        }

        if (settings.getPoolSize() != null) {
            builder.poolSize(settings.getPoolSize());
        }

        Proteus proteus = builder.accessKey(settings.getAccessKey())
                .group(enableProteus.getString("group"))
                .accessToken(settings.getAccessToken())
                .host(settings.getBrokerHostname())
                .port(settings.getBrokerPort())
                .build();

        return proteus;
    }

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

    @Bean
    @ConditionalOnNotWebApplication
    @ConditionalOnMissingBean
    public ProteusRunner proteusRunner() {
        return new ProteusRunner();
    }
}
