package com.shahinnazarov.client.kafka.consumer;

import com.shahinnazarov.client.kafka.consumer.utils.Constants;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.Closeable;
import java.util.Properties;

public abstract class AbstractKafkaConsumer<K, V> implements Closeable {
    private String keySerializer;
    private String valueSerializer;
    private String bootstrapServer;
    private String groupId;
    private boolean autoCommit;
    private int autoCommitInterval;

    public AbstractKafkaConsumer(String keySerializer, String valueSerializer, String bootstrapServer, String groupId,
                                 boolean autoCommit, int autoCommitInterval) {
        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;
        this.bootstrapServer = bootstrapServer;
        this.groupId = groupId;
        this.autoCommit = autoCommit;
        this.autoCommitInterval = autoCommitInterval;
    }

    public AbstractKafkaConsumer(String keySerializer, String valueSerializer, Properties properties) {
        this(keySerializer, valueSerializer, properties.getProperty(Constants.K_BOOTSTRAP_SERVERS), properties.getProperty(Constants.K_GROUP_ID),
                Boolean.parseBoolean(properties.getProperty(Constants.K_AUTOCOMMIT)), Integer.parseInt(properties.getProperty(Constants.K_AUTOCOMMIT_INTERVAL)));
    }

    protected KafkaConsumer<K, V> generateConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServer);
        props.put("group.id", groupId);
        props.put("enable.auto.commit", autoCommit);
        props.put("auto.commit.interval.ms", autoCommitInterval);
        props.put("key.deserializer", keySerializer);
        props.put("value.deserializer", valueSerializer);
        return new KafkaConsumer(props);
    }
}
