package com.shahinnazarov.client.kafka.producer.utils;

public final class Constants {
    private Constants() {
    }
    public static final String PROPERTIES_PATH = "kafka.properties";

    public static final String K_BOOTSTRAP_SERVERS = "bootstrap.servers";
    public static final String K_ACKS = "acks";
    public static final String K_RETRIES = "retries";
    public static final String K_BATCH_SIZE = "batch.size";
    public static final String K_LINGER_MS = "linger.ms";
    public static final String K_BUFFER_MEMORY = "buffer.memory";
}
