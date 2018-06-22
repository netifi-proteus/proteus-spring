package com.netifi.proteus.demo.isvowel.service.blocking;

@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.7.13)",
    comments = "Source: isvowel.proto")
public final class IsVowelServiceClient implements IsVowelService {
  private final com.netifi.proteus.demo.isvowel.service.IsVowelServiceClient delegate;

  public IsVowelServiceClient(io.rsocket.RSocket rSocket) {
    this.delegate = new com.netifi.proteus.demo.isvowel.service.IsVowelServiceClient(rSocket);
  }

  public IsVowelServiceClient(io.rsocket.RSocket rSocket, io.micrometer.core.instrument.MeterRegistry registry) {
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

