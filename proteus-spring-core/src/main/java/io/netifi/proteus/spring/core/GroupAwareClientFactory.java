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
package io.netifi.proteus.spring.core;

import io.netifi.proteus.common.tags.Tags;
import io.netifi.proteus.common.tags.Tag;
import io.netifi.proteus.spring.core.annotation.ProteusClient;

public interface GroupAwareClientFactory<T> extends ProteusClientFactory<T> {
    default T group(){
        return lookup(ProteusClient.Type.GROUP);
    }

    default T group(String group){
        return lookup(ProteusClient.Type.GROUP, group);
    }

    default T group(String group, Tag... tags){
        return lookup(ProteusClient.Type.GROUP, group, Tags.of(tags));
    }

    default T group(String group, Tags tags){
        return lookup(ProteusClient.Type.GROUP, group, tags);
    }

    default T group(Tag... tags){
        return lookup(ProteusClient.Type.GROUP, Tags.of(tags));
    }

    default T group(Tags tags){
        return lookup(ProteusClient.Type.GROUP, tags);
    }
}
