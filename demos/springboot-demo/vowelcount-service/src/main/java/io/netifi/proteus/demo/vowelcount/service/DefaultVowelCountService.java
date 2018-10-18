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
package io.netifi.proteus.demo.vowelcount.service;

import io.netifi.proteus.demo.isvowel.service.IsVowelRequest;
import io.netifi.proteus.demo.isvowel.service.IsVowelResponse;
import io.netifi.proteus.demo.isvowel.service.IsVowelService;
import io.netifi.proteus.demo.isvowel.service.IsVowelServiceClient;
import io.netifi.proteus.spring.core.annotation.Group;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Service that receives messages and keeps track of the number of vowels found in those messages.
 */
@Component
public class DefaultVowelCountService implements VowelCountService {
  private static final Logger LOG = LoggerFactory.getLogger(DefaultVowelCountService.class);

  private final IsVowelServiceClient isVowelClient;

  public DefaultVowelCountService(@Group("io.netifi.proteus.demo.isvowel") IsVowelServiceClient client) {
    isVowelClient = client;
  }

  @Override
  public Flux<VowelCountResponse> countVowels(Publisher<VowelCountRequest> messages, ByteBuf metadata) {
    AtomicLong vowelCnt = new AtomicLong(0);

    return Flux.from(messages)
            .map(vowelCountRequest -> {
              LOG.info("Received Message: " + vowelCountRequest.getMessage());
              return vowelCountRequest.getMessage();
            })
            .flatMap(message -> Flux.fromArray(message.split("(?<!^)"))
                    .flatMap(c -> {
                      IsVowelRequest vReq = IsVowelRequest.newBuilder()
                              .setCharacter(c)
                              .build();

                      return isVowelClient.isVowel(vReq)
                              .filter(IsVowelResponse::getIsVowel)
                              .map(vResp -> vowelCnt.incrementAndGet());
                    })
                    .map(count -> VowelCountResponse.newBuilder().setVowelCnt(count).build()));
  }
}
