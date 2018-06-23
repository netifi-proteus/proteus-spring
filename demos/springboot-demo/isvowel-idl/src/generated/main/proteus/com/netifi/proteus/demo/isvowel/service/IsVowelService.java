package com.netifi.proteus.demo.isvowel.service;

/**
 */
@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.7.14-SNAPSHOT)",
    comments = "Source: isvowel.proto")
public interface IsVowelService {
  String SERVICE = "com.netifi.proteus.demo.isvowel.service.IsVowelService";
  String METHOD_IS_VOWEL = "IsVowel";

  /**
   */
  reactor.core.publisher.Mono<com.netifi.proteus.demo.isvowel.service.IsVowelResponse> isVowel(com.netifi.proteus.demo.isvowel.service.IsVowelRequest message, io.netty.buffer.ByteBuf metadata);
}
