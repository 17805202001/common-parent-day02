package com.czxy.bos.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by 10254 on 2018/10/29.
 */
public class Test {

    public static void main(String[] args) {
        Jedis jedis=new Jedis("192.168.231.1");
        jedis.set("username","请问");
        System.out.println(jedis.get("username"));
    }
}
