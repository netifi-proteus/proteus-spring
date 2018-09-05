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
package com.netifi.proteus.spring.core.config;

import java.util.Optional;

import com.netifi.proteus.spring.core.ProteusApplicationEventListener;
import com.netifi.proteus.spring.core.ProteusBeanDefinitionRegistryPostProcessor;
import com.netifi.proteus.spring.core.ProteusClientAnnotationProcessor;
import io.micrometer.core.instrument.MeterRegistry;
import io.netifi.proteus.Proteus;
import io.netifi.proteus.broker.info.BlockingBrokerInfoService;
import io.netifi.proteus.broker.info.BlockingBrokerInfoServiceServer;
import io.netifi.proteus.broker.info.BrokerInfoService;
import io.netifi.proteus.broker.info.BrokerInfoServiceServer;
import io.opentracing.Tracer;
import io.rsocket.rpc.metrics.om.BlockingMetricsSnapshotHandler;
import io.rsocket.rpc.metrics.om.BlockingMetricsSnapshotHandlerServer;
import io.rsocket.rpc.metrics.om.MetricsSnapshotHandler;
import io.rsocket.rpc.metrics.om.MetricsSnapshotHandlerServer;
import reactor.core.scheduler.Scheduler;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProteusConfiguration {

    @Bean(name = "internalProteusBeanDefinitionRegistryPostProcessor")
    public ProteusBeanDefinitionRegistryPostProcessor proteusBeanDefinitionRegistryPostProcessor() {
        return new ProteusBeanDefinitionRegistryPostProcessor();
    }

    @Bean(name = "internalProteusClientAnnotationProcessor")
    public ProteusClientAnnotationProcessor proteusClientAnnotationProcessor(
        ConfigurableListableBeanFactory beanFactory
    ) {
        return new ProteusClientAnnotationProcessor(beanFactory);
    }

    @Bean
    public ProteusApplicationEventListener proteusApplicationEventListener(
        Proteus proteus
    ) {
        return new ProteusApplicationEventListener(proteus);
    }

    @Bean
    public BrokerInfoServiceServer brokerInfoServiceServer(
        BrokerInfoService brokerInfoService,
        Optional<MeterRegistry> registry,
        Optional<Tracer> tracer
    ) {
        return new BrokerInfoServiceServer(brokerInfoService, registry, tracer);
    }

    @Bean
    public BlockingBrokerInfoServiceServer blockingBrokerInfoServiceServer(
        BlockingBrokerInfoService blockingBrokerInfoService,
        Optional<Scheduler> scheduler,
        Optional<MeterRegistry> registry
    ) {
        return new BlockingBrokerInfoServiceServer(blockingBrokerInfoService, scheduler, registry);
    }

    @Bean
    public MetricsSnapshotHandlerServer metricsSnapshotHandlerServer(
        MetricsSnapshotHandler metricsSnapshotHandler,
        Optional<MeterRegistry> registry,
        Optional<Tracer> tracer
    ) {
        return new MetricsSnapshotHandlerServer(metricsSnapshotHandler, registry, tracer);
    }

    @Bean
    public BlockingMetricsSnapshotHandlerServer blockingMetricsSnapshotHandlerServer(
        BlockingMetricsSnapshotHandler blockingMetricsSnapshotHandler,
        Optional<Scheduler> scheduler,
        Optional<MeterRegistry> registry
    ) {
        return new BlockingMetricsSnapshotHandlerServer(blockingMetricsSnapshotHandler, scheduler, registry);
    }
}
