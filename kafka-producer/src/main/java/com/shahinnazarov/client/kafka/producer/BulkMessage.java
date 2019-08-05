package com.shahinnazarov.client.kafka.producer;

import com.shahinnazarov.client.kafka.producer.utils.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BulkMessage {

    public static void main(String[] args) throws IOException {
        InputStream resource = Main.class.getClassLoader().getResourceAsStream(Constants.PROPERTIES_PATH);
        assert resource != null;

        Properties properties = new Properties();
        properties.load(resource);

        try (ByteArrayProducer producer = new ByteArrayProducer(properties)) {
/*            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter topic name:");
            String topic = scanner.nextLine();*/
            for (int i = 0; i < 100000; i++) {
                producer.send("test3", "id-" + i, new ByteArrayWrapper("{\"appName\":\"appName\",\"level\":\"DEBUG\",\"thread\":\"main\",\"threadId\":1,\"loggerName\":\"com.shahinnazarov.client.kafka.producer.BulkMessage\",\"message\":\"Running with Spring Boot v2.1.6.RELEASE, Spring v5.1.8.RELEASE\"}".getBytes()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
