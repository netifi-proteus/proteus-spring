package com.netifi.proteus.demo;

import com.netifi.proteus.annotations.ProteusService;
import com.netifi.proteus.demo.service.ingest.IngestService;
import com.netifi.proteus.demo.service.ingest.ProcessMessageRequest;
import com.netifi.proteus.demo.service.ingest.TotalVowelsResponse;
import io.netty.buffer.ByteBuf;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

@ProteusService(group = "springboot-demo-ingestservice",
                destination = "ingest-service"
)
public class DefaultIngestService implements IngestService {

    @Override
    public Flux<TotalVowelsResponse> process(Publisher<ProcessMessageRequest> messages, ByteBuf metadata) {
        return null;
    }
}
