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

import io.netifi.proteus.Proteus;
import io.netifi.proteus.spring.core.config.ProteusConfiguration;
import io.netifi.proteus.springboot.ProteusAutoConfiguration;
import io.netifi.proteus.springboot.ProteusConfigurer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ClientConfigurationIntegrationTest.TestConfiguration.class)
@ImportAutoConfiguration({
    ProteusAutoConfiguration.class,
    ProteusConfiguration.class,
    ClientConfigurationIntegrationTest.TestConfiguration.class
})
public class ClientConfigurationIntegrationTest {

    @Autowired
    @Qualifier("mock")
    ProteusConfigurer configurer;

    @Test
    public void testThatConfigurerWorks() {
        ArgumentCaptor<Proteus.Builder> captor = ArgumentCaptor.forClass(Proteus.Builder.class);

        Mockito.verify(configurer).configure(captor.capture());
        Assertions.assertNotNull(captor.getValue());
    }

    @org.springframework.boot.test.context.TestConfiguration
    static class TestConfiguration {

        @Bean
        @Qualifier("mock")
        public ProteusConfigurer testProteusConfigurer() {
            ProteusConfigurer configurer = Mockito.mock(ProteusConfigurer.class);

            Mockito.when(configurer.configure(any(Proteus.Builder.class)))
                   .then(a -> a.getArgument(0));

            return configurer;
        }
    }
}
