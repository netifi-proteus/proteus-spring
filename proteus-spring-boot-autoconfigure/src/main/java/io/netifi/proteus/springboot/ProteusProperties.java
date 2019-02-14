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
package io.netifi.proteus.springboot;

import java.net.MalformedURLException;
import java.net.URL;
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
    private SslProperties ssl = new SslProperties();

    @Valid
    private AccessProperties access = new AccessProperties();

    @Valid
    private BrokerProperties broker = new BrokerProperties();

    private MetricsProperties metrics = new MetricsProperties();

    private TracingProperties tracing = new TracingProperties();

    private DiscoveryProperties discovery = new DiscoveryProperties();

    public SslProperties getSsl() {
        return ssl;
    }

    public AccessProperties getAccess() {
        return access;
    }

    public BrokerProperties getBroker() {
        return broker;
    }

    public MetricsProperties getMetrics() {
        return metrics;
    }

    public TracingProperties getTracing() {
        return tracing;
    }

    public DiscoveryProperties getDiscovery() { return discovery; }

    public String getDestination() {
        return destination;
    }

    public String getGroup() {
        return group;
    }

    public void setSsl(SslProperties ssl) {
        this.ssl = ssl;
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

    public static final class SslProperties {
        @NotNull
        private Boolean disabled = false;

        public Boolean isDisabled() {
            return disabled;
        }

        public void setDisabled(Boolean disabled) {
            this.disabled = disabled;
        }
    }

    public static final class MetricsProperties {
        @NotNull
        private String group = "com.netifi.proteus.metrics";

        private long reportingStepInMillis = 10_000L;

        private boolean export = true;

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public long getReportingStepInMillis() {
            return reportingStepInMillis;
        }

        public void setReportingStepInMillis(long reportingStepInMillis) {
            this.reportingStepInMillis = reportingStepInMillis;
        }

        public boolean isExport() {
            return export;
        }

        public void setExport(boolean export) {
            this.export = export;
        }
    }


    public static final class TracingProperties {
        @NotNull
        private String group = "com.netifi.proteus.tracing";

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }
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

        private String hostname;

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

    public static final class DiscoveryProperties {

        private String environment = "static";

        private StaticProperties staticProperties = new StaticProperties();

        private EC2Properties ec2Properties = new EC2Properties();

        private ConsulProperties consulProperties = new ConsulProperties();

        private KubernetesProperties kubernetesProperties = new KubernetesProperties();

        public String getEnvironment() { return environment; }

        public StaticProperties getStaticProperties() { return staticProperties; }

        public EC2Properties getEc2Properties() {  return ec2Properties; }

        public ConsulProperties getConsulProperties() { return consulProperties; }

        public KubernetesProperties getKubernetesProperties() { return kubernetesProperties; }

        public void setEnvironment(String environment) { this.environment = environment; }

        public void setStaticProperties(StaticProperties staticProperties) { this.staticProperties = staticProperties; }

        public void setEc2Properties(EC2Properties ec2Properties) { this.ec2Properties = ec2Properties; }

        public void setConsulProperties(ConsulProperties consulProperties) { this.consulProperties = consulProperties; }

        public void setKubernetesProperties(KubernetesProperties kubernetesProperties) { this.kubernetesProperties = kubernetesProperties; }

        public static final class StaticProperties {

            private String[] addresses = new String[]{"localhost"};

            @Min(value = 0)
            @Max(value = 65_535)
            private Integer port = 8001;

            public String[] getAddresses() { return addresses; }

            public Integer getPort() { return port; }

            public void setAddresses(String[] addresses) { this.addresses = addresses; }

            public void setPort(Integer port) { this.port = port; }
        }

        public static final class EC2Properties {

            private String tagName = "service";

            private String tagValue = "netifi-proteus-broker";

            @Min(value = 0)
            @Max(value = 65_535)
            private Integer port = 8001;

            public String getTagName() { return tagName; }

            public String getTagValue() { return tagValue; }

            public Integer getPort() { return port; }

            public void setTagName(String tagName) { this.tagName = tagName; }

            public void setTagValue(String tagValue) { this.tagValue = tagValue; }

            public void setPort(Integer port) { this.port = port; }
        }

        public static final class ConsulProperties {

            private URL consulURL;
            {
                try {
                    consulURL =
                        new URL("http", "localhost", 8500, "");
                } catch (MalformedURLException e) {
                    throw new RuntimeException("static code of default consul url is broken.");
                }
            }

            private String serviceName = "netifi-proteus-broker";

            public URL getConsulURL() { return consulURL; }

            public void setConsulURL(URL consulURL) { this.consulURL = consulURL; }

            public String getServiceName() { return serviceName; }

            public void setServiceName(String serviceName) { this.serviceName = serviceName; }
        }

        public static final class KubernetesProperties {

            private String namespace = "default";

            private String deploymentName = "netifi-proteus-broker";

            private String portName = "tcp";

            public String getNamespace() { return namespace; }

            public void setNamespace(String namespace) { this.namespace = namespace; }

            public String getDeploymentName() { return deploymentName; }

            public void setDeploymentName(String deploymentName) { this.deploymentName = deploymentName; }

            public String getPortName() { return portName; }

            public void setPortName(String portName) { this.portName = portName; }
        }
    }
}
