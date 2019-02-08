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
package io.netifi.proteus.spring.core.annotation;

import io.netifi.proteus.spring.core.NoTagsSupplier;
import io.netifi.proteus.spring.core.TagSupplier;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE,
        ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ProteusClient {

    Type type() default Type.GROUP;

    String destination() default "";

    String group() default "";

    Class<? extends TagSupplier> tagSupplier() default NoTagsSupplier.class;

    Tag[] tags() default {};

    enum Type {
        DESTINATION, GROUP, BROADCAST,
    }
}
