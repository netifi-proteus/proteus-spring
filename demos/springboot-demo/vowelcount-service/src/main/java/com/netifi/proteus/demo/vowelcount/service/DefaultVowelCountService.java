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
import io.netifi.proteus.annotations.ProteusClient;
import io.netty.buffer.ByteBuf;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

@Component
public class DefaultVowelCountService implements VowelCountService {
    private final AtomicLong totalVowels = new AtomicLong(0);

    @ProteusClient(group = "com.netifi.proteus.demo.isvowel")
    private IsVowelServiceClient isVowelClient;

    @Override
    public Flux<VowelCountResponse> countVowels(Publisher<VowelCountRequest> messages, ByteBuf metadata) {
        return Flux.from(s ->
                Flux.from(messages)
                        // Split each incoming random string into a stream of individual characters
                        .flatMap(vowelCountRequest -> Flux.just(vowelCountRequest.getMessage().split("(?<!^)")))
                        // For each individual character create an IsVowelRequest
                        .map(c -> IsVowelRequest.newBuilder().setCharacter(c).build())
                        // Send each IsVowelRequest to the IsVowel service
                        .flatMap((Function<IsVowelRequest, Publisher<IsVowelResponse>>) isVowelClient::isVowel)
                        .doOnNext(isVowelResponse -> {
                            if (isVowelResponse.getIsVowel()) {
                                // For every vowel found by the IsVowel service, increment the vowel count and send the
                                // current vowel count to the client
                                s.onNext(VowelCountResponse.newBuilder()
                                        .setVowelCnt(totalVowels.incrementAndGet())
                                        .build());
                            }
                        })
                        .doOnComplete(s::onComplete)
                        .subscribe()
        );
    }
}
