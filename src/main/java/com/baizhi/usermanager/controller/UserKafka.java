package com.baizhi.usermanager.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("userkafkaproduct")
public class UserKafka{

    /**
     * 生产者demo
     */
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping("product")
    public  void product() {


        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("t3", "user00210", "xh210");
        // 异步处理的结果对象
        /*
        future.addCallback(
                new SuccessCallback<SendResult<String, String>>() {
                    @Override
                    public void onSuccess(SendResult<String, String> stringStringSendResult) {
                        System.out.println("发送成功！");
                    }
                }, new FailureCallback() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println("发送失败！");
                        throwable.printStackTrace();
                    }
                });
        */

        // 函数式编程
        future.addCallback(
                (stringStringSendResult) -> {
                    System.out.println("发送成功！");
                },
                (t) -> {
                    System.out.println("发送失败！");
                    t.printStackTrace();
                }
        );
    }

}
