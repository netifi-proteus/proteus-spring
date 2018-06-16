package com.netifi.proteus.demo.service.ingest;

@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.7.1)",
    comments = "Source: ingest.proto")
public final class IngestServiceClient implements IngestService {
  private final io.rsocket.RSocket rSocket;
  private final java.util.function.Function<? super org.reactivestreams.Publisher<com.netifi.proteus.demo.service.ingest.TotalVowelsResponse>, ? extends org.reactivestreams.Publisher<com.netifi.proteus.demo.service.ingest.TotalVowelsResponse>> process;

  public IngestServiceClient(io.rsocket.RSocket rSocket) {
    this.rSocket = rSocket;
    this.process = java.util.function.Function.identity();
  }

  public IngestServiceClient(io.rsocket.RSocket rSocket, io.micrometer.core.instrument.MeterRegistry registry) {
    this.rSocket = rSocket;
    this.process = io.netifi.proteus.metrics.ProteusMetrics.timed(registry, "proteus.client", "namespace", "com.netifi.proteus.demo.service.ingest", "service", "IngestService", "method", "process");
  }

  public reactor.core.publisher.Flux<com.netifi.proteus.demo.service.ingest.TotalVowelsResponse> process(org.reactivestreams.Publisher<com.netifi.proteus.demo.service.ingest.ProcessMessageRequest> messages) {
    return process(messages, io.netty.buffer.Unpooled.EMPTY_BUFFER);
  }

  @java.lang.Override
  public reactor.core.publisher.Flux<com.netifi.proteus.demo.service.ingest.TotalVowelsResponse> process(org.reactivestreams.Publisher<com.netifi.proteus.demo.service.ingest.ProcessMessageRequest> messages, io.netty.buffer.ByteBuf metadata) {
    return rSocket.requestChannel(reactor.core.publisher.Flux.from(messages).map(
      new java.util.function.Function<com.google.protobuf.MessageLite, io.rsocket.Payload>() {
        private final java.util.concurrent.atomic.AtomicBoolean once = new java.util.concurrent.atomic.AtomicBoolean(false);

        @java.lang.Override
        public io.rsocket.Payload apply(com.google.protobuf.MessageLite message) {
          io.netty.buffer.ByteBuf data = serialize(message);
          if (once.compareAndSet(false, true)) {
            final io.netty.buffer.ByteBuf metadataBuf = io.netifi.proteus.frames.ProteusMetadata.encode(io.netty.buffer.ByteBufAllocator.DEFAULT, IngestService.NAMESPACE_ID, IngestService.SERVICE_ID, IngestService.METHOD_PROCESS, metadata);
            return io.rsocket.util.ByteBufPayload.create(data, metadataBuf);
          } else {
            return io.rsocket.util.ByteBufPayload.create(data);
          }
        }
      })).map(deserializer(com.netifi.proteus.demo.service.ingest.TotalVowelsResponse.parser())).transform(process);
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
