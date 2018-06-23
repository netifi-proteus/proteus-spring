package com.netifi.proteus.demo.vowelcount.service.blocking;

/**
 */
@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.7.14-SNAPSHOT)",
    comments = "Source: vowelcount.proto")
public interface VowelCountService {
  String SERVICE_ID = "com.netifi.proteus.demo.vowelcount.serviceVowelCountService";
  String METHOD_COUNT_VOWELS = "CountVowels";

  /**
   */
  Iterable<com.netifi.proteus.demo.vowelcount.service.VowelCountResponse> countVowels(Iterable<com.netifi.proteus.demo.vowelcount.service.VowelCountRequest> messages, io.netty.buffer.ByteBuf metadata);
}
