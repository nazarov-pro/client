package com.shahinnazarov.client.kafka.producer;

public class ByteArrayWrapper {
    private byte[] data;

    public ByteArrayWrapper(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
