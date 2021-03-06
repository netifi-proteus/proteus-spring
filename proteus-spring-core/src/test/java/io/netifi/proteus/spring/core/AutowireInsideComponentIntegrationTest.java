/**
 * Copyright 2019 Netifi Inc.
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
package io.netifi.proteus.spring.core;

import io.netifi.proteus.broker.info.BrokerInfoService;
import io.netifi.proteus.broker.info.BrokerInfoServiceClient;
import io.netifi.proteus.spring.DefaultExternalIdlClient;
import io.netifi.proteus.spring.core.annotation.Broadcast;
import io.netifi.proteus.spring.core.annotation.Destination;
import io.netifi.proteus.spring.core.annotation.Group;
import io.netifi.proteus.spring.core.annotation.ProteusClient;
import io.rsocket.rpc.metrics.om.MetricsSnapshotHandler;
import io.rsocket.rpc.metrics.om.MetricsSnapshotHandlerClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    TestableConfiguration.class,
    AutowireInsideComponentIntegrationTest.AutowirableConfiguration.class
})
public class AutowireInsideComponentIntegrationTest {

    @Autowired
    TestBean testBean;

    @Test
    public void shouldFindGeneratedBean() {
        Assertions.assertEquals(DefaultClientTestIdl.class,
                testBean.broadcastTestIdlClient.getClass());
        Assertions.assertEquals(DefaultClientTestIdl.class,
                testBean.groupTestIdlClient.getClass());
        Assertions.assertEquals(DefaultClientTestIdl.class,
                testBean.destinationTestIdaClient.getClass());
        Assertions.assertEquals(MetricsSnapshotHandlerClient.class,
                testBean.metricsSnapshotHandlerClient.getClass());
        Assertions.assertEquals(BrokerInfoServiceClient.class,
                testBean.brokerInfoServiceClient.getClass());
        Assertions.assertEquals(TestIdlImpl.class,
                testBean.serviceImpl.getClass());
        Assertions.assertNotNull(testBean.destinationAwareClientFactory);
        Assertions.assertNotNull(testBean.groupAwareClientFactory);
        Assertions.assertNotNull(testBean.broadcastAwareClientFactory);
        Assertions.assertNotNull(testBean.defaultExternalIdlClient);
    }


    @Configuration
    public static class AutowirableConfiguration {

        @Bean
        public TestBean testBean(
            @Broadcast("test")
            TestIdl broadcastTestIdlClient,

            @Group("test")
            TestIdl groupTestIdlClient,

            @Destination(group = "test", destination = "test")
            TestIdl destinationTestIdaClient,

            @Group("test")
            MetricsSnapshotHandler metricsSnapshotHandlerClient,

            @Group("test")
            BrokerInfoService brokerInfoServiceClient,

            @Destination(group = "test", destination = "test")
            DefaultExternalIdlClient defaultExternalIdlClient,

            @ProteusClient(group = "test", destination = "test", clientClass = DefaultExternalIdlClient.class)
            DestinationAwareClientFactory<DefaultExternalIdlClient> destinationAwareClientFactory,

            @ProteusClient(group = "test", clientClass = DefaultExternalIdlClient.class)
            GroupAwareClientFactory<DefaultExternalIdlClient> groupAwareClientFactory,

            @ProteusClient(group = "test", clientClass = DefaultExternalIdlClient.class)
            BroadcastAwareClientFactory<DefaultExternalIdlClient> broadcastAwareClientFactory,

            TestIdl serviceImpl
        ) {
            return new TestBean(broadcastTestIdlClient,
                    groupTestIdlClient,
                    destinationTestIdaClient,
                    metricsSnapshotHandlerClient,
                    brokerInfoServiceClient,
                    defaultExternalIdlClient,
                    destinationAwareClientFactory,
                    groupAwareClientFactory,
                    broadcastAwareClientFactory,
                    serviceImpl);
        }
    }
}
