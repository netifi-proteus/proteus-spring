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
import io.netifi.proteus.spring.core.config.EnableProteus;
import org.mockito.Mockito;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableProteus
public class TestableConfiguration {

    @Bean
    public Proteus proteus() {
        return Mockito.mock(Proteus.class);
    }

    @Bean
    public TestIdlImpl testIdlImpl() {
        return new TestIdlImpl();
    }
}
