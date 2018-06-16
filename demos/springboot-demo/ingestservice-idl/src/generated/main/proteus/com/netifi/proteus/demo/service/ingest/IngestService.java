package com.netifi.proteus.demo.service.ingest;

import com.netifi.proteus.annotations.ProteusService;

import javax.inject.Singleton;

/**
 */
@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.7.1)",
    comments = "Source: ingest.proto")
public interface IngestService {
  int NAMESPACE_ID = 1408208831;
  int SERVICE_ID = 2036268172;
  int METHOD_INGEST_MESSAGE = -1485601629;

  /**
   */
  reactor.core.publisher.Flux<com.netifi.proteus.demo.service.ingest.TotalVowelsResponse> ingestMessage(org.reactivestreams.Publisher<com.netifi.proteus.demo.service.ingest.ProcessMessageRequest> messages, io.netty.buffer.ByteBuf metadata);
}
