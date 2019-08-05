package com.shahinnazarov.client.kafka.producer;

import com.shahinnazarov.client.kafka.producer.utils.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws IOException {
        InputStream resource = Main.class.getClassLoader().getResourceAsStream(Constants.PROPERTIES_PATH);
        assert resource != null;

        Properties properties = new Properties();
        properties.load(resource);

        try (StringProducer stringProducer = new StringProducer(properties)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter topic name:");
            String topic = scanner.nextLine();
            System.out.printf("%s topic selected. Below all messages will be send.\n", topic);
            while (true) {
                String line = scanner.next();
                if (line.trim().equalsIgnoreCase("exit")) {
                    break;
                }
                stringProducer.send(topic, line).get();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
