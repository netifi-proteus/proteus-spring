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
package com.netifi.proteus.demo.isvowel.service;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Component
public class DefaultIsVowelService implements IsVowelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultIsVowelService.class);

    private final Set<String> VOWELS = new HashSet<>();

    public DefaultIsVowelService() {
        VOWELS.add("A");
        VOWELS.add("E");
        VOWELS.add("I");
        VOWELS.add("O");
        VOWELS.add("U");
    }

    @Override
    public Mono<IsVowelResponse> isVowel(IsVowelRequest message, ByteBuf metadata) {
        return Mono.fromSupplier(() -> {
            LOGGER.info("Received character: {}", message.getCharacter());

            return IsVowelResponse.newBuilder()
                    .setIsVowel(VOWELS.contains(message.getCharacter()))
                    .build();
        });
    }
}
