package com.netifi.proteus.springwebflux.demo;

import com.netifi.bootproteus.example.service.hello.HelloRequest;
import com.netifi.bootproteus.example.service.hello.HelloResponse;
import com.netifi.bootproteus.example.service.hello.HelloService;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DefaultHelloService implements HelloService {

    @Override
    public Mono<HelloResponse> getHello(HelloRequest message, ByteBuf metadata) {
        return null;
    }
}
