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
package com.netifi.proteus.springboot;

import io.netifi.proteus.Proteus;
import org.springframework.boot.CommandLineRunner;

import java.util.Set;

public class ProteusRunner implements CommandLineRunner {
    private final Set<Proteus> connections;

    public ProteusRunner(Set<Proteus> connections) {
        this.connections = connections;
    }

    @Override
    public void run(String... args) throws Exception {

        Thread.currentThread().join();
    }
}
