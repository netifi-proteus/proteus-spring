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

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Proteus configuration options that can be set via the application properties files or system properties.
 */
@ConfigurationProperties("netifi.proteus")
@Validated
public class ProteusProperties {

    @Min(value = 1)
    private Integer poolSize = Runtime.getRuntime().availableProcessors();

    @NotEmpty
    @NotNull
    /**
     * The group to register this service as in the Proteus broker.
     *
     * @return proteus group name
     */
    private String group;

    /**
     * The destination to register this service as in the Proteus Broker. If not specified, Proteus will automatically
     * generate a globally unique name for you.
     *
     * @return proteus destination name
     */
    private String destination;

    @Valid
    private AccessProperties access = new AccessProperties();

    @Valid
    private BrokerProperties broker = new BrokerProperties();

    public AccessProperties getAccess() {
        return access;
    }

    public BrokerProperties getBroker() {
        return broker;
    }

    public String getDestination() {
        return destination;
    }

    public String getGroup() {
        return group;
    }

    public void setAccess(AccessProperties access) {
        this.access = access;
    }

    public void setBroker(BrokerProperties broker) {
        this.broker = broker;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(Integer poolSize) {
        this.poolSize = poolSize;
    }


    public static final class AccessProperties {

        @NotNull
        private Long key;

        @NotEmpty
        @NotNull
        private String token;

        public Long getKey() {
            return key;
        }

        public String getToken() {
            return token;
        }

        public void setKey(Long key) {
            this.key = key;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public static final class BrokerProperties {

        @NotEmpty
        @NotNull
        private String hostname = "localhost";

        @Min(value = 0)
        @Max(value = 65_535)
        private Integer port = 8001;

        public String getHostname() {
            return hostname;
        }

        public Integer getPort() {
            return port;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }

        public void setPort(Integer port) {
            this.port = port;
        }
    }
}
