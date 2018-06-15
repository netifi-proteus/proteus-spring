package com.netifi.bootproteus.example.service.hello;

/**
 */
@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.7.1)",
    comments = "Source: hello.proto")
public interface HelloService {
  int NAMESPACE_ID = -1857459858;
  int SERVICE_ID = -1062476274;
  int METHOD_GET_HELLO = 1425779351;

  /**
   */
  reactor.core.publisher.Mono<com.netifi.bootproteus.example.service.hello.HelloResponse> getHello(com.netifi.bootproteus.example.service.hello.HelloRequest message, io.netty.buffer.ByteBuf metadata);
}
