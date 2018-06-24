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

import com.netifi.proteus.springboot.exception.MissingAccessKeyException;
import com.netifi.proteus.springboot.exception.MissingAccessTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.MissingRequiredPropertiesException;
import org.springframework.util.StringUtils;

/**
 * Proteus configuration options that can be set via the application properties files or system properties.
 */
@ConfigurationProperties
public class ProteusSettings {
    public static final String BROKER_HOSTNAME_PROPERTY = "netifi.proteus.broker.hostname";
    public static final String BROKER_PORT_PROPERTY = "netifi.proteus.broker.port";
    public static final String PROTEUS_ACCESSKEY_PROPERTY = "netifi.proteus.accesskey";
    public static final String PROTEUS_ACCESSTOKEN_PROPERTY = "netifi.proteus.accesstoken";

    @Value("${netifi.proteus.broker.hostname:localhost}")
    private String brokerHostname;

    @Value("${netifi.proteus.broker.port:8001}")
    private Integer brokerPort;

    @Value("${netifi.proteus.accesskey}")
    private Long accessKey;

    @Value("${netifi.proteus.accesstoken}")
    private String accessToken;

    public String getBrokerHostname() {
        return brokerHostname;
    }

    public Integer getBrokerPort() {
        return brokerPort;
    }

    public Long getAccessKey() {
        if (accessKey == null) {
            throw new MissingAccessKeyException();
        }

        return accessKey;
    }

    public String getAccessToken() {
        if (StringUtils.isEmpty(accessToken)) {
            //throw new MissingAccessTokenException();

            throw new MissingRequiredPropertiesException();
        }

        return accessToken;
    }
}
