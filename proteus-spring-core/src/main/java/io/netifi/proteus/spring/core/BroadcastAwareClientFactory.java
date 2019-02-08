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
package io.netifi.proteus.spring.core;


import io.netifi.proteus.spring.core.ProteusClientFactory;
import io.netifi.proteus.common.tags.Tags;
import io.netifi.proteus.common.tags.Tag;
import io.netifi.proteus.spring.core.annotation.ProteusClient;

interface BroadcastAwareClientFactory<T> extends ProteusClientFactory<T> {

    default T broadcast(){
        return lookup(ProteusClient.Type.BROADCAST);
    }

    default T broadcast(String group){
        return lookup(ProteusClient.Type.BROADCAST, group);
    }

    default T broadcast(String group, Tag... tags){
        return lookup(ProteusClient.Type.BROADCAST, group, Tags.of(tags));
    }

    default T broadcast(String group, Tags tags){
        return lookup(ProteusClient.Type.BROADCAST, group, tags);
    }

    default T broadcast(Tag... tags){
        return lookup(ProteusClient.Type.BROADCAST, Tags.of(tags));
    }

    default T broadcast(Tags tags){
        return lookup(ProteusClient.Type.BROADCAST, tags);
    }
}