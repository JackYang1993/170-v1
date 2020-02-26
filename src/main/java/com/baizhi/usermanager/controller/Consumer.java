package com.baizhi.usermanager.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;


@SpringBootApplication
public class Consumer {

    /**
     * 消费者DEMO
     */
    @KafkaListener(topics = "t3", groupId = "g1")
    public void receive(ConsumerRecord<String, String> record) {
        System.out.println(
                record.key()
                        + "\t"
                        + record.value()
                        + "\t"
                        + record.timestamp()
                        + "\t"
                        + record.offset()
                        + "\t"
                        + record.partition()
                        + "\t"
                        + record.topic()
        );
    }


    public static void main(String[] args) {
        SpringApplication.run(Consumer.class, args);
    }

}
