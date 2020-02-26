package com.baizhi.usermanager;

import com.baizhi.usermanager.util.MailUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@MapperScan("com.baizhi.usermanager.dao")
public class UsermanagerApplication {

    @KafkaListener(topics = "t9", groupId = "g1")
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


        int i = MailUtil.sendSimpleMail(record.key(), "注册成功啦", "注册成功");

        System.out.println(i);


    }


    public static void main(String[] args) {
        SpringApplication.run(UsermanagerApplication.class, args);
    }

}
