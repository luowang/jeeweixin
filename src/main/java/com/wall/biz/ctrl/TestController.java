package com.wall.biz.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @author: wang.luo
 * @date 2016/11/17 15:10
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Resource
    private JedisPool jedisPool;

    @RequestMapping(value = "/test1")
    public String test1(){
        Jedis jedis = jedisPool.getResource();
        jedis.set("mmm",String.valueOf(System.currentTimeMillis()));
        String mmm = jedis.get("mmm");
        System.out.println("---》》》》"+mmm);
        jedisPool.returnResource(jedis);
        return mmm;
    }
}
