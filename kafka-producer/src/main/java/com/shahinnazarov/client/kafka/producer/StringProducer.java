package com.shahinnazarov.client.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Future;

public class StringProducer extends AbstractKafkaProducer<String, String> {

    private final KafkaProducer<String, String> kafkaProducer;

    public StringProducer(Properties properties) {
        super("org.apache.kafka.common.serialization.StringSerializer",
                "org.apache.kafka.common.serialization.StringSerializer",
                properties);
        kafkaProducer = super.generateProducer();
    }

    @Override
    public Future<RecordMetadata> send(String topic, String message) {
        return kafkaProducer.send(new ProducerRecord(topic, message), (r, e) -> {
            e.printStackTrace();
        });
    }

    @Override
    public Future<RecordMetadata> send(String topic, String key, String value) {
        return kafkaProducer.send(new ProducerRecord(topic, key, value), (r, e) -> {
            e.printStackTrace();
        });
    }

    @Override
    public void close() throws IOException {
        kafkaProducer.close();
    }

}
