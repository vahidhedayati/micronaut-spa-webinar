package com.objectcomputing.util;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("jhipster")
public class MyApplicationProperties {

    private final Cache cache;
    private final Mail mail;
    private final Logging logging;
    private final Metrics metrics;

    public MyApplicationProperties(Cache cache, Mail mail, Logging logging, Metrics metrics) {
        this.cache = cache;
        this.mail = mail;
        this.logging = logging;
        this.metrics = metrics;
    }

    public Cache getCache() {
        return cache;
    }

    public Mail getMail() {
        return mail;
    }

    public Logging getLogging() {
        return logging;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    @ConfigurationProperties("logging")
    public static class Logging {
        private boolean useJsonFormat = false;
        private final Logstash logstash = new Logstash();

        public Logging() {
        }

        public boolean isUseJsonFormat() {
            return this.useJsonFormat;
        }

        public void setUseJsonFormat(boolean useJsonFormat) {
            this.useJsonFormat = useJsonFormat;
        }

        public Logstash getLogstash() {
            return this.logstash;
        }

        @ConfigurationProperties("logstash")
        public static class Logstash {
            private boolean enabled = false;
            private String host = "localhost";
            private int port = 5000;
            private int queueSize = 512;


            public boolean isEnabled() {
                return this.enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public String getHost() {
                return this.host;
            }

            public void setHost(String host) {
                this.host = host;
            }

            public int getPort() {
                return this.port;
            }

            public void setPort(int port) {
                this.port = port;
            }

            public int getQueueSize() {
                return this.queueSize;
            }

            public void setQueueSize(int queueSize) {
                this.queueSize = queueSize;
            }
        }
    }

    @ConfigurationProperties("metrics")
    public static class Metrics {
        private final Logs logs = new Logs();

        public Logs getLogs() {
            return this.logs;
        }

        @ConfigurationProperties("logs")
        public static class Logs {
            private boolean enabled = false;
            private long reportFrequency = 60L;

            public boolean isEnabled() {
                return this.enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public long getReportFrequency() {
                return this.reportFrequency;
            }

            public void setReportFrequency(long reportFrequency) {
                this.reportFrequency = reportFrequency;
            }
        }
    }

    @ConfigurationProperties("mail")
    public static class Mail {
        private boolean enabled = false;
        private String from = "";
        private String baseUrl = "";

        public boolean isEnabled() {
            return this.enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getFrom() {
            return this.from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getBaseUrl() {
            return this.baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }
    }

    @ConfigurationProperties("cache")
    public static class Cache {

        private final Hazelcast hazelcast = new Hazelcast();
        private final Ehcache ehcache = new Ehcache();
        private final Infinispan infinispan = new Infinispan();
        private final Memcached memcached = new Memcached();

        public Hazelcast getHazelcast() {
            return hazelcast;
        }

        public Ehcache getEhcache() {
            return ehcache;
        }

        public Infinispan getInfinispan() {
            return infinispan;
        }

        public Memcached getMemcached() {
            return memcached;
        }

        @ConfigurationProperties("memcached")
        public static class Memcached {

            private boolean enabled = false;
            private String servers = "localhost:11211";
            private int expiration = 300;
            private boolean useBinaryProtocol = true;


            public boolean isEnabled() {
                return this.enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public String getServers() {
                return this.servers;
            }

            public void setServers(String servers) {
                this.servers = servers;
            }

            public int getExpiration() {
                return this.expiration;
            }

            public void setExpiration(int expiration) {
                this.expiration = expiration;
            }

            public boolean isUseBinaryProtocol() {
                return this.useBinaryProtocol;
            }

            public void setUseBinaryProtocol(boolean useBinaryProtocol) {
                this.useBinaryProtocol = useBinaryProtocol;
            }
        }

        @ConfigurationProperties("infinispan")
        public static class Infinispan {
            private String configFile = "default-configs/default-jgroups-tcp.xml";
            private boolean statsEnabled = false;
            private final Local local = new Local();
            private final Distributed distributed = new Distributed();
            private final Replicated replicated = new Replicated();

            public String getConfigFile() {
                return this.configFile;
            }

            public void setConfigFile(String configFile) {
                this.configFile = configFile;
            }

            public boolean isStatsEnabled() {
                return this.statsEnabled;
            }

            public void setStatsEnabled(boolean statsEnabled) {
                this.statsEnabled = statsEnabled;
            }

            public Local getLocal() {
                return this.local;
            }

            public Distributed getDistributed() {
                return this.distributed;
            }

            public Replicated getReplicated() {
                return this.replicated;
            }

            @ConfigurationProperties("replicated")
            public static class Replicated {
                private long timeToLiveSeconds = 60L;
                private long maxEntries = 100L;

                public long getTimeToLiveSeconds() {
                    return this.timeToLiveSeconds;
                }

                public void setTimeToLiveSeconds(long timeToLiveSeconds) {
                    this.timeToLiveSeconds = timeToLiveSeconds;
                }

                public long getMaxEntries() {
                    return this.maxEntries;
                }

                public void setMaxEntries(long maxEntries) {
                    this.maxEntries = maxEntries;
                }
            }

            @ConfigurationProperties("distributed")
            public static class Distributed {
                private long timeToLiveSeconds = 60L;
                private long maxEntries = 100L;
                private int instanceCount = 1;

                public long getTimeToLiveSeconds() {
                    return this.timeToLiveSeconds;
                }

                public void setTimeToLiveSeconds(long timeToLiveSeconds) {
                    this.timeToLiveSeconds = timeToLiveSeconds;
                }

                public long getMaxEntries() {
                    return this.maxEntries;
                }

                public void setMaxEntries(long maxEntries) {
                    this.maxEntries = maxEntries;
                }

                public int getInstanceCount() {
                    return this.instanceCount;
                }

                public void setInstanceCount(int instanceCount) {
                    this.instanceCount = instanceCount;
                }
            }

            @ConfigurationProperties("local")
            public static class Local {
                private long timeToLiveSeconds = 60L;
                private long maxEntries = 100L;

                public Local() {
                }

                public long getTimeToLiveSeconds() {
                    return this.timeToLiveSeconds;
                }

                public void setTimeToLiveSeconds(long timeToLiveSeconds) {
                    this.timeToLiveSeconds = timeToLiveSeconds;
                }

                public long getMaxEntries() {
                    return this.maxEntries;
                }

                public void setMaxEntries(long maxEntries) {
                    this.maxEntries = maxEntries;
                }
            }
        }

        @ConfigurationProperties("ehcache")
        public static class Ehcache {

            private int timeToLiveSeconds = 3600;
            private long maxEntries = 100L;

            public int getTimeToLiveSeconds() {
                return this.timeToLiveSeconds;
            }

            public void setTimeToLiveSeconds(int timeToLiveSeconds) {
                this.timeToLiveSeconds = timeToLiveSeconds;
            }

            public long getMaxEntries() {
                return this.maxEntries;
            }

            public void setMaxEntries(long maxEntries) {
                this.maxEntries = maxEntries;
            }
        }

        @ConfigurationProperties("hazelcast")
        public static class Hazelcast {
            private int timeToLiveSeconds = 3600;
            private int backupCount = 1;
            private final ManagementCenter managementCenter = new ManagementCenter();

            public ManagementCenter getManagementCenter() {
                return this.managementCenter;
            }

            public int getTimeToLiveSeconds() {
                return this.timeToLiveSeconds;
            }

            public void setTimeToLiveSeconds(int timeToLiveSeconds) {
                this.timeToLiveSeconds = timeToLiveSeconds;
            }

            public int getBackupCount() {
                return this.backupCount;
            }

            public void setBackupCount(int backupCount) {
                this.backupCount = backupCount;
            }

            @ConfigurationProperties("management-center")
            public static class ManagementCenter {
                private boolean enabled = false;
                private int updateInterval = 3;
                private String url = "";

                public boolean isEnabled() {
                    return this.enabled;
                }

                public void setEnabled(boolean enabled) {
                    this.enabled = enabled;
                }

                public int getUpdateInterval() {
                    return this.updateInterval;
                }

                public void setUpdateInterval(int updateInterval) {
                    this.updateInterval = updateInterval;
                }

                public String getUrl() {
                    return this.url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}
