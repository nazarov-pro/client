package com.shahinnazarov.client.kafka.consumer;

import com.shahinnazarov.client.kafka.consumer.utils.Constants;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static long start;

    public static void main(String[] args) throws IOException {
        InputStream resource = Main.class.getClassLoader().getResourceAsStream(Constants.PROPERTIES_PATH);
        assert resource != null;

        Properties properties = new Properties();
        properties.load(resource);

        try (StringConsumer consumer = new StringConsumer(properties)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter topic name:");
            String topic = scanner.nextLine();
            consumer.subscribe(topic);
            System.out.printf("%s topic subscribed. All messages will show below.\n", topic);
            AtomicInteger i = new AtomicInteger(0);
            while (i.get() < 100000) {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(5);
                consumerRecords.forEach(poll -> {
                            if(i.get() == 0){
                                start = System.currentTimeMillis();
                            }
                            i.getAndIncrement();
//                            System.out.println("offset = " + poll.offset() + ", value = " + poll.value() + ", key=" + poll.key() + " i=" + i.getAndIncrement());
                        }
                );
            }
            System.out.println("time " + (System.currentTimeMillis() - start));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
