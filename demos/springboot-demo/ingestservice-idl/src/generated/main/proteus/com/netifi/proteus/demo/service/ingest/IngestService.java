package com.netifi.proteus.demo.service.ingest;

/**
 */
@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.7.1)",
    comments = "Source: ingest.proto")
public interface IngestService {
  int NAMESPACE_ID = 1408208831;
  int SERVICE_ID = 2036268172;
  int METHOD_PROCESS = -387747791;

  /**
   */
  reactor.core.publisher.Flux<com.netifi.proteus.demo.service.ingest.TotalVowelsResponse> process(org.reactivestreams.Publisher<com.netifi.proteus.demo.service.ingest.ProcessMessageRequest> messages, io.netty.buffer.ByteBuf metadata);
}
