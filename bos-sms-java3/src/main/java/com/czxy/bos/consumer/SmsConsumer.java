package com.czxy.bos.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import sun.plugin2.message.Message;

import javax.jms.MapMessage;

/**
 * Created by 10254 on 2018/9/17.
 */
@Component
public class SmsConsumer {

    //消费者实时监控
    @JmsListener(destination = "czxy.queue")
    public void receiveQueue(Message message){
        try {
            MapMessage mapMessage= (MapMessage) message;
            String phone=mapMessage.getString("phone");
            String code=mapMessage.getString("code");
            System.out.println("消费者consumer："+phone+":"+code);

        }catch (Exception e){

        }
    }
}
