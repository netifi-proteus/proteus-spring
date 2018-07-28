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

import io.netifi.proteus.AbstractProteusService;
import io.netifi.proteus.Proteus;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class ProteusApplicationEventListener {
    private final Proteus proteus;
    private final Optional<Set<AbstractProteusService>> proteusServices;

    public ProteusApplicationEventListener(Proteus proteus, Optional<Set<AbstractProteusService>> proteusServices) {
        this.proteus = proteus;
        this.proteusServices = proteusServices;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        proteusServices.ifPresent(s -> s.forEach(proteus::addService));
    }
}
