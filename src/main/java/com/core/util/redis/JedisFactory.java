package com.core.util.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;

class JedisFactory {

    @Autowired
    private  JedisPool jedisPool;

    private static JedisFactory instance = null;

    public JedisFactory() {
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        jedisPool = new JedisPool(poolConfig, "", 1);
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public static JedisFactory getInstance() throws IOException {

        if (instance == null) {
            instance = new JedisFactory();
        }
        return instance;
    }
}

