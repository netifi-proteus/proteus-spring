package com.netifi.proteus.demo.isvowel.service;

@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.7.14-SNAPSHOT)",
    comments = "Source: isvowel.proto")
@io.netifi.proteus.annotations.internal.ProteusGenerated(
    type = io.netifi.proteus.annotations.internal.ProteusResourceType.CLIENT,
    idlClass = BlockingIsVowelService.class)
public final class BlockingIsVowelServiceClient implements BlockingIsVowelService {
  private final com.netifi.proteus.demo.isvowel.service.IsVowelServiceClient delegate;

  public BlockingIsVowelServiceClient(io.rsocket.RSocket rSocket) {
    this.delegate = new com.netifi.proteus.demo.isvowel.service.IsVowelServiceClient(rSocket);
  }

  public BlockingIsVowelServiceClient(io.rsocket.RSocket rSocket, io.micrometer.core.instrument.MeterRegistry registry) {
    this.delegate = new com.netifi.proteus.demo.isvowel.service.IsVowelServiceClient(rSocket, registry);
  }

  public com.netifi.proteus.demo.isvowel.service.IsVowelResponse isVowel(com.netifi.proteus.demo.isvowel.service.IsVowelRequest message) {
    return isVowel(message, io.netty.buffer.Unpooled.EMPTY_BUFFER);
  }

  @java.lang.Override
  public com.netifi.proteus.demo.isvowel.service.IsVowelResponse isVowel(com.netifi.proteus.demo.isvowel.service.IsVowelRequest message, io.netty.buffer.ByteBuf metadata) {
    return delegate.isVowel(message, metadata).block();
  }

}

