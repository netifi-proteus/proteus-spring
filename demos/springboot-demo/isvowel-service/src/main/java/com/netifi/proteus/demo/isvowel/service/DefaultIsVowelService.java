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

import com.netifi.proteus.annotations.ProteusService;
import io.netty.buffer.ByteBuf;
import proteus.demo.service.isvowel.IsVowelRequest;
import proteus.demo.service.isvowel.IsVowelResponse;
import proteus.demo.service.isvowel.IsVowelService;
import reactor.core.publisher.Mono;

@ProteusService(group = "com.netifi.proteus.demo.isvowel")
public class DefaultIsVowelService implements IsVowelService {
    
    @Override
    public Mono<IsVowelResponse> isVowel(IsVowelRequest message, ByteBuf metadata) {
        return null;
    }
}
