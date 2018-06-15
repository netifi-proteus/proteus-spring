package com.netifi.bootproteus.example.service.hello;

import javax.inject.Singleton;

@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.7.1)",
    comments = "Source: hello.proto")
@Singleton
public final class HelloServiceClient implements HelloService {
  private final io.rsocket.RSocket rSocket;
  private final java.util.function.Function<? super org.reactivestreams.Publisher<com.netifi.bootproteus.example.service.hello.HelloResponse>, ? extends org.reactivestreams.Publisher<com.netifi.bootproteus.example.service.hello.HelloResponse>> getHello;

  public HelloServiceClient(io.rsocket.RSocket rSocket) {
    this.rSocket = rSocket;
    this.getHello = java.util.function.Function.identity();
  }

  public HelloServiceClient(io.rsocket.RSocket rSocket, io.micrometer.core.instrument.MeterRegistry registry) {
    this.rSocket = rSocket;
    this.getHello = io.netifi.proteus.metrics.ProteusMetrics.timed(registry, "proteus.client", "namespace", "com.netifi.bootproteus.example.service.hello", "service", "HelloService", "method", "getHello");
  }

  public reactor.core.publisher.Mono<com.netifi.bootproteus.example.service.hello.HelloResponse> getHello(com.netifi.bootproteus.example.service.hello.HelloRequest message) {
    return getHello(message, io.netty.buffer.Unpooled.EMPTY_BUFFER);
  }

  @java.lang.Override
  public reactor.core.publisher.Mono<com.netifi.bootproteus.example.service.hello.HelloResponse> getHello(com.netifi.bootproteus.example.service.hello.HelloRequest message, io.netty.buffer.ByteBuf metadata) {
    return reactor.core.publisher.Mono.defer(new java.util.function.Supplier<reactor.core.publisher.Mono<io.rsocket.Payload>>() {
      @java.lang.Override
      public reactor.core.publisher.Mono<io.rsocket.Payload> get() {
        final io.netty.buffer.ByteBuf metadataBuf = io.netifi.proteus.frames.ProteusMetadata.encode(io.netty.buffer.ByteBufAllocator.DEFAULT, HelloService.NAMESPACE_ID, HelloService.SERVICE_ID, HelloService.METHOD_GET_HELLO, metadata);
        io.netty.buffer.ByteBuf data = serialize(message);
        return rSocket.requestResponse(io.rsocket.util.ByteBufPayload.create(data, metadataBuf));
      }
    }).map(deserializer(com.netifi.bootproteus.example.service.hello.HelloResponse.parser())).transform(getHello);
  }

  private static io.netty.buffer.ByteBuf serialize(final com.google.protobuf.MessageLite message) {
    int length = message.getSerializedSize();
    io.netty.buffer.ByteBuf byteBuf = io.netty.buffer.ByteBufAllocator.DEFAULT.buffer(length);
    try {
      message.writeTo(com.google.protobuf.CodedOutputStream.newInstance(byteBuf.internalNioBuffer(0, length)));
      byteBuf.writerIndex(length);
      return byteBuf;
    } catch (Throwable t) {
      byteBuf.release();
      throw new RuntimeException(t);
    }
  }

  private static <T> java.util.function.Function<io.rsocket.Payload, T> deserializer(final com.google.protobuf.Parser<T> parser) {
    return new java.util.function.Function<io.rsocket.Payload, T>() {
      @java.lang.Override
      public T apply(io.rsocket.Payload payload) {
        try {
          com.google.protobuf.CodedInputStream is = com.google.protobuf.CodedInputStream.newInstance(payload.getData());
          return parser.parseFrom(is);
        } catch (Throwable t) {
          throw new RuntimeException(t);
        } finally {
          payload.release();
        }
      }
    };
  }
}
