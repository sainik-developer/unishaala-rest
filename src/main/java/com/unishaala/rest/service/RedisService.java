package com.unishaala.rest.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
@AllArgsConstructor
public class RedisService {
    private final JedisPool jedisPool;

    public String getKey(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        }
    }

    public void setKeyWithExpiry(String key, String value, int expiry) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(key, expiry, value);
        }
    }

    public boolean removeKey(final String key) {
        try (final Jedis jedis = jedisPool.getResource()) {
            return jedis.del(key) > 1;
        }
    }
}
