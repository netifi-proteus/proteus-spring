package com.netifi.proteus.demo.service.isvowel;

/**
 */
@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.7.1)",
    comments = "Source: isvowel.proto")
public interface IsVowelService {
  int NAMESPACE_ID = 1911563760;
  int SERVICE_ID = -122448787;
  int METHOD_IS_VOWEL = -121846395;

  /**
   */
  reactor.core.publisher.Mono<com.netifi.proteus.demo.service.isvowel.IsVowelResponse> isVowel(com.netifi.proteus.demo.service.isvowel.IsVowelRequest message, io.netty.buffer.ByteBuf metadata);
}
