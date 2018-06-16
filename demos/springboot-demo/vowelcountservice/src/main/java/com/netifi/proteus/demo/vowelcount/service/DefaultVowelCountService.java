package com.netifi.proteus.demo.vowelcount.service;

import com.netifi.proteus.annotations.ProteusService;
import com.netifi.proteus.demo.service.vowelcount.VowelCountRequest;
import com.netifi.proteus.demo.service.vowelcount.VowelCountResponse;
import com.netifi.proteus.demo.service.vowelcount.VowelCountService;
import io.netty.buffer.ByteBuf;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

@ProteusService(group = "com.netifi.proteus.demo.vowelcount")
public class DefaultVowelCountService implements VowelCountService {

    @Override
    public Flux<VowelCountResponse> countVowels(Publisher<VowelCountRequest> messages, ByteBuf metadata) {
        return null;
    }
}
