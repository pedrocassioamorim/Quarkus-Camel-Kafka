package net.producer.config;


import lombok.Data;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ConfigProperties(prefix = "mp.messaging.outgoing") @Data
public class KafkaProducerConfig {

    @ConfigProperty(name = "bootstrap.servers")
    public String bootstrapServers;

    @ConfigProperty(name = "topic")
    public String topic;

    @ConfigProperty(name = "key.serializer")
    public Class keySerializer;

    @ConfigProperty(name = "value.serializer")
    public Class valueSerializer;

    @ConfigProperty(name = "connector")
    public Class connector;

    @ConfigProperty(name = "retries")
    public int retries;

    @ConfigProperty(name = "batch.size")
    public int batchSize;

    @ConfigProperty(name = "lingerMs")
    public int lingerMs;

    @ConfigProperty(name = "buffer.memory")
    public int bufferMemory;


}
