package com.core.util.redis;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import com.core.util.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by Bosn on 14/11/28.
 * Basic cache, need weight for string length.
 */
public class CacheUtils {
    private static final int DEFAULT_CACHE_EXPIRE_SECS = 600;
    private static final Logger logger = LogManager.getLogger(CacheUtils.class);

    public static final String KEY_MOCK_RULE = "KEY_MOCK_RULE:";
    public static final String KEY_MOCK_DATA = "KEY_MOCK_DATA";

    @Resource
    private static JedisPool jedisPool;
    private static Jedis jedis;

    public CacheUtils() {}

    public static Jedis getJedis() {
        try {
            jedisPool = JedisFactory.getInstance().getJedisPool();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        jedis = jedisPool.getResource();
        return jedis;
    }

    public static void returnJedis() {
        jedisPool.returnResourceObject(jedis);
    }

    /**
     * set Mock rule cache
     *
     * @param actionId
     * @param result
     */
    public static void setRuleCache(int actionId, String result, boolean isMockData) {
        String[] cacheKey = new String[]{isMockData ? KEY_MOCK_DATA : KEY_MOCK_RULE, new Integer(actionId).toString()};
        put(cacheKey, result);
    }

    public static void removeCacheByActionId(int id) {
        String[] cacheKey1 = new String[]{KEY_MOCK_RULE, new Integer(id).toString()};
        String[] cacheKey2 = new String[]{KEY_MOCK_DATA, new Integer(id).toString()};

        getJedis();

        jedis.del(StringUtils.join(cacheKey1, "|"));
        jedis.del(StringUtils.join(cacheKey2, "|"));

        returnJedis();
    }

    public static void put(String [] keys, String value, int expireInSecs) {
        Jedis jedis = getJedis();
        String cacheKey = StringUtils.join(keys, "|");
        jedis.set(cacheKey, value);
        if (expireInSecs > 0)
            jedis.expire(cacheKey, expireInSecs);
        returnJedis();
    }

    public static void put(String [] keys, String value) {
        put(keys, value, DEFAULT_CACHE_EXPIRE_SECS);
    }

    public static String get(String []keys) {

        String cache =  getJedis().get(StringUtils.join(keys, "|"));
        returnJedis();
        return cache;
    }

    public static void del(String[] keys) {
        String cacheKey = StringUtils.join(keys, "|");
        getJedis().del(cacheKey);
        returnJedis();
    }

    public static void init() {
        getJedis();
        jedis.flushAll();
        returnJedis();
    }
}
