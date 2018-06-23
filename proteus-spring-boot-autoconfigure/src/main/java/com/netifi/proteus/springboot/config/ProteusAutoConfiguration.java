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

import com.netifi.proteus.annotations.ProteusService;
import com.netifi.proteus.springboot.ProteusRunner;
import io.netifi.proteus.AbstractProteusService;
import io.netifi.proteus.Proteus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableConfigurationProperties(ProteusSettings.class)
public class ProteusAutoConfiguration {

    private final ProteusSettings settings;

    public ProteusAutoConfiguration(ProteusSettings settings) {
        this.settings = settings;
    }

    @Bean
    @ConditionalOnMissingBean
    public Set<Proteus> proteusConnections(Set<AbstractProteusService> services) {
        Set<Proteus> connections = new HashSet<>();

        services.forEach(service -> {
            ProteusService annotation = service.getServiceClass().getAnnotation(ProteusService.class);

            if (StringUtils.isEmpty(annotation.destination())) {
                Proteus proteus = Proteus.builder()
                        .group(annotation.group())
                        .accessKey(settings.getAccessKey())
                        .accessToken(settings.getAccessToken())
                        .host(settings.getBrokerHostname())
                        .port(settings.getBrokerPort())
                        .build();

                proteus.addService(service);

                connections.add(proteus);
            } else {
                Proteus proteus = Proteus.builder()
                        .group(annotation.group())
                        .destination(annotation.destination())
                        .accessKey(settings.getAccessKey())
                        .accessToken(settings.getAccessToken())
                        .host(settings.getBrokerHostname())
                        .port(settings.getBrokerPort())
                        .build();

                proteus.addService(service);

                connections.add(proteus);
            }
        });

        return connections;
    }

    @Bean
    @ConditionalOnMissingBean
    public ProteusRunner proteusRunner(Set<Proteus> connections) {
        return new ProteusRunner(connections);
    }
}
