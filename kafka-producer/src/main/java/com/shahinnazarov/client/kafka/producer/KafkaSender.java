package com.shahinnazarov.client.kafka.producer;

import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.Future;

public interface KafkaSender<K, V> {
    Future<RecordMetadata> send(String topic, V value);
    Future<RecordMetadata> send(String topic, K key, V value);
}
