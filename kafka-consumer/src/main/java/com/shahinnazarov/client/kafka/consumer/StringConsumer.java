package com.shahinnazarov.client.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class StringConsumer extends AbstractKafkaConsumer<String, String> {

    private KafkaConsumer<String, String> kafkaConsumer;

    public StringConsumer(Properties properties) {
        super("org.apache.kafka.common.serialization.StringDeserializer",
                "org.apache.kafka.common.serialization.StringDeserializer",
                properties);
        kafkaConsumer = generateConsumer();
    }

    public void subscribe(String... topics) {
        kafkaConsumer.subscribe(Arrays.asList(topics));
    }

    public ConsumerRecords<String, String> poll(int seconds) {
        return kafkaConsumer.poll(Duration.ofSeconds(seconds));
    }

    @Override
    public void close() throws IOException {
        kafkaConsumer.close();
    }
}
