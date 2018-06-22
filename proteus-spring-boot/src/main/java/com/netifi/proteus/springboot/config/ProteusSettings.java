package com.netifi.proteus.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class ProteusSettings {

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
        return accessKey;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
