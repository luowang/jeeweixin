package com.wall.biz.service;

import com.core.util.redis.CacheUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wang.luo
 * @date 2016/11/2 17:55
 */
public class WallService {

//    @Resource
//    private static JedisPool pool;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        JedisPool pool = (JedisPool) context.getBean("jedisPool");
        Jedis jedis = pool.getResource();
//        Jedis jedis = pool.getResource();
        jedis.set("mmm","1223");
        String mmm = jedis.get("mmm");
        System.out.println("---》》》》"+mmm);
        pool.returnResource(jedis);
    }
}
