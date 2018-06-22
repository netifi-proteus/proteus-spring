/**
 * Copyright 2018 Netifi Inc.
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
package com.netifi.proteus.demo.isvowel;

import com.netifi.proteus.demo.isvowel.service.IsVowelServiceServer;
import com.netifi.proteus.springboot.annotation.EnableProteus;
import com.netifi.proteus.springboot.config.ProteusSettings;
import io.netifi.proteus.Proteus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
@EnableProteus
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    @Bean
    public Proteus proteus(IsVowelServiceServer isVowelServiceServer, ProteusSettings settings) {
        Proteus proteus = Proteus.builder()
                .group("com.netifi.proteus.demo.isvowel")
                .destination("isvowel")
                .accessKey(settings.getAccessKey())
                .accessToken(settings.getAccessToken())
                .host(settings.getBrokerHostname())
                .port(settings.getBrokerPort())
                .build();

        proteus.addService(isVowelServiceServer);

        return proteus;
    }

    @Bean
    public ProteusRunner proteusRunner(Set<Proteus> proteuses) {
        return new ProteusRunner(proteuses);
    }

    public class ProteusRunner implements CommandLineRunner {

        Set<Proteus> proteuses;

        public ProteusRunner(Set<Proteus> proteuses) {
            this.proteuses = proteuses;
        }

        @Override
        public void run(String... args) throws Exception {
            Thread.currentThread().join();
        }
    }
}