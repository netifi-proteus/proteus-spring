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
package com.netifi.proteus.demo.vowelcount.service;

import com.netifi.proteus.demo.isvowel.service.IsVowelRequest;
import com.netifi.proteus.demo.isvowel.service.IsVowelResponse;
import com.netifi.proteus.demo.isvowel.service.IsVowelServiceClient;
import io.netty.buffer.ByteBuf;
import io.rsocket.rpc.annotations.Client;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Component;

@Component
public class DefaultVowelCountService implements VowelCountService {
  @Client(group = "com.netifi.proteus.demo.isvowel")
  private IsVowelServiceClient isVowelClient;

  @Override
  public Mono<VowelCountResponse> countVowels(
      Publisher<VowelCountRequest> messages, ByteBuf metadata) {
    return Flux.from(messages)
        .flatMap(
            request ->
                Flux.fromArray(request.getMessage().split("(?<!^)"))
                    .flatMap(
                        s ->
                            isVowelClient
                                .isVowel(IsVowelRequest.newBuilder().setCharacter(s).build())
                                .filter(IsVowelResponse::getIsVowel)))
        .count()
        .map(count -> VowelCountResponse.newBuilder().setVowelCnt(count).build());
  }
}
