package com.shahinnazarov.client.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Future;

public class ByteArrayProducer extends AbstractKafkaProducer<String, ByteArrayWrapper> {

    private final KafkaProducer<String, ByteArrayWrapper> kafkaProducer;

    public ByteArrayProducer(Properties properties) {
        super("org.apache.kafka.common.serialization.StringSerializer",
                "org.apache.kafka.common.serialization.ByteArraySerializer",
                properties);
        kafkaProducer = super.generateProducer();
    }

    @Override
    public Future<RecordMetadata> send(String topic, ByteArrayWrapper message) {
        return kafkaProducer.send(new ProducerRecord(topic, message.getData()), (r, e) -> {
            e.printStackTrace();
        });
    }

    @Override
    public Future<RecordMetadata> send(String topic, String key, ByteArrayWrapper value) {
        return kafkaProducer.send(new ProducerRecord(topic, key, value.getData()), (r, e) -> {
            e.printStackTrace();
        });
    }

    @Override
    public void close() throws IOException {
        kafkaProducer.close();
    }
}
