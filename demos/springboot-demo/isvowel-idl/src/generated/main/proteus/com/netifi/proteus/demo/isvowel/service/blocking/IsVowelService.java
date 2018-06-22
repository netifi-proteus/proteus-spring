package com.netifi.proteus.demo.isvowel.service.blocking;

/**
 */
@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.7.13)",
    comments = "Source: isvowel.proto")
public interface IsVowelService {
  String SERVICE_ID = "com.netifi.proteus.demo.isvowel.serviceIsVowelService";
  String METHOD_IS_VOWEL = "IsVowel";

  /**
   */
  com.netifi.proteus.demo.isvowel.service.IsVowelResponse isVowel(com.netifi.proteus.demo.isvowel.service.IsVowelRequest message, io.netty.buffer.ByteBuf metadata);
}
