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
package com.netifi.proteus.springboot.config;

import com.netifi.proteus.annotations.ProteusService;
import io.netifi.proteus.AbstractProteusService;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;

public class ProteusClassPathScanningCandidateComponentProvider extends ClassPathScanningCandidateComponentProvider {

    public ProteusClassPathScanningCandidateComponentProvider() {
        super(true);
    }

    @Override
    protected void registerDefaultFilters() {
        super.registerDefaultFilters();
        addIncludeFilter(new AssignableTypeFilter(AbstractProteusService.class));
        addIncludeFilter(new AnnotationTypeFilter(ProteusService.class));
    }
}
