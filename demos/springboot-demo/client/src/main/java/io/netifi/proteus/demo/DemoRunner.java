/**
 * Copyright 2019 Netifi Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.netifi.proteus.demo;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.ThreadLocalRandom;

import io.netifi.proteus.demo.core.RandomString;
import io.netifi.proteus.demo.vowelcount.service.VowelCountRequest;
import io.netifi.proteus.demo.vowelcount.service.VowelCountServiceClient;
import io.netifi.proteus.spring.core.annotation.Group;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoRunner implements CommandLineRunner {

  @Autowired
  private RandomString randomString;

  @Group("io.netifi.proteus.demo.vowelcount")
  private VowelCountServiceClient client;

  @Override
  public void run(String... args) throws Exception {
    // Generate stream of 100 ten random character strings
    Flux<VowelCountRequest> requests = Flux.range(1, 100)
            .delayElements(Duration.of(100, ChronoUnit.MILLIS))
            .map(v -> VowelCountRequest.newBuilder()
                    .setMessage(randomString.next(10, ThreadLocalRandom.current()))
                    .build());
  
    // Send stream of random strings to vowel count service
    client.countVowels(requests)
            .doOnError(Throwable::printStackTrace)
            .doOnComplete(() -> System.exit(0))
            .subscribe(response -> {
              System.out.print("\r");
              System.out.print("Total Vowels Found: " + response.getVowelCnt());
            });
  }
}
