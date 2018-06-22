package com.netifi.proteus.demo.vowelcount.service;

/**
 */
@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.7.13)",
    comments = "Source: vowelcount.proto")
public interface VowelCountService {
  String SERVICE = "com.netifi.proteus.demo.vowelcount.service.VowelCountService";
  String METHOD_COUNT_VOWELS = "CountVowels";

  /**
   */
  reactor.core.publisher.Flux<com.netifi.proteus.demo.vowelcount.service.VowelCountResponse> countVowels(org.reactivestreams.Publisher<com.netifi.proteus.demo.vowelcount.service.VowelCountRequest> messages, io.netty.buffer.ByteBuf metadata);
}
