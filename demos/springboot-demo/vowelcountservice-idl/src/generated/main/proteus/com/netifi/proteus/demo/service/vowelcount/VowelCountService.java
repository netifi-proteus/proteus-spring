package com.netifi.proteus.demo.service.vowelcount;

import com.netifi.proteus.annotations.ProteusService;

/**
 */
@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.7.1)",
    comments = "Source: vowelcount.proto")
public interface VowelCountService {
  int NAMESPACE_ID = 788850803;
  int SERVICE_ID = 1528059860;
  int METHOD_COUNT_VOWELS = -1928649243;

  /**
   */
  reactor.core.publisher.Mono<com.netifi.proteus.demo.service.vowelcount.VowelCountResponse> countVowels(com.netifi.proteus.demo.service.vowelcount.VowelCountRequest message, io.netty.buffer.ByteBuf metadata);
}
