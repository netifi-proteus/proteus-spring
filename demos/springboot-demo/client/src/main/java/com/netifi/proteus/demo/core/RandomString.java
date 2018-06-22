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
package com.netifi.proteus.demo.core;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomString {

    private final char[] ALPHAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    /**
     * Creates a random string of specified length containing only alphabetic characters.
     *
     * @param length length of the string
     * @param random random source
     * @return string of specified length containing only alphabetic characters
     */
    public String next(int length, Random random) {
        if (length < 1) {
            throw new IllegalArgumentException("length must be greater than or equal to 1");
        }

        final char[] buffer = new char[length];

        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = ALPHAS[random.nextInt(ALPHAS.length)];
        }

        return new String(buffer);
    }
}
