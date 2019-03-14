package com.czxy.bos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by 10254 on 2018/8/31.
 */
@SpringBootApplication
@EnableScheduling
public class BOS_Application {
    public static void main(String[] args) {
        SpringApplication.run(BOS_Application.class,args);
    }
}
