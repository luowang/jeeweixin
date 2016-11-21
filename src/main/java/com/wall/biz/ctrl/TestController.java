package com.wall.biz.ctrl;

import net.sf.json.JSONObject;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.json.JsonObject;
import java.util.HashMap;

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

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("1","q");
        map.put("2","w");
        JSONObject json = JSONObject.fromObject(map);
        String str = json.toString();
        System.out.println(str);
    }
}
