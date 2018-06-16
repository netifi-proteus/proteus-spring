package com.netifi.proteus.demo.isvowel.service;

import com.netifi.proteus.annotations.ProteusService;
import com.netifi.proteus.demo.service.isvowel.IsVowelRequest;
import com.netifi.proteus.demo.service.isvowel.IsVowelResponse;
import com.netifi.proteus.demo.service.isvowel.IsVowelService;
import io.netty.buffer.ByteBuf;
import reactor.core.publisher.Mono;

@ProteusService(group = "com.netifi.proteus.demo.isvowel")
public class DefaultIsVowelService implements IsVowelService {

    @Override
    public Mono<IsVowelResponse> isVowel(IsVowelRequest message, ByteBuf metadata) {
        return null;
    }
}
