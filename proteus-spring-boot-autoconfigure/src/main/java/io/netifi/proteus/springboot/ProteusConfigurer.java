package io.netifi.proteus.springboot;

import io.netifi.proteus.Proteus;

@FunctionalInterface
public interface ProteusConfigurer {

    Proteus.Builder configure(Proteus.Builder builder);
}
