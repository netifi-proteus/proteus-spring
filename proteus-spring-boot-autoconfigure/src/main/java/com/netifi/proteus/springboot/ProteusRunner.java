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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

/**
 * An implementation of {@link CommandLineRunner} that blocks the main thread of execution
 * when serving long-running Proteus services outside of a web container to keep the JVM up and running.
 */
public class ProteusRunner implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProteusRunner.class);

    @Override
    public void run(String... args) throws Exception {
        Thread.currentThread().join();
    }
}
