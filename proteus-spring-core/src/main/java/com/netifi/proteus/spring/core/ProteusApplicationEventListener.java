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
package com.netifi.proteus.spring.core;

import java.util.Map;

import io.netifi.proteus.Proteus;
import io.rsocket.rpc.AbstractRSocketService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

public class ProteusApplicationEventListener {
    private final Proteus proteus;
    public ProteusApplicationEventListener(Proteus proteus) {
        this.proteus = proteus;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        Map<String, AbstractRSocketService> abstractRSocketServiceMap =
            context.getBeansOfType(AbstractRSocketService.class);

        abstractRSocketServiceMap.values()
                                 .forEach(proteus::addService);
    }
}
