package com.tequeno.redis;

import org.springframework.util.SerializationUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class JedisUtil {

    protected JedisPool jedisPool;

    private final ThreadLocal<Jedis> local = new ThreadLocal<>();

    private Jedis getResource() {
        if (local.get() == null) {
            local.set(jedisPool.getResource());
        }
        return local.get();
    }

    public void saveOrUpdate(String key, Object value) {
        Jedis resource = jedisPool.getResource();
        try {
            resource.set(key.getBytes(), SerializationUtils.serialize(value));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            resource.close();
        }
    }

    public void expire(String key, int i) {
        Jedis resource = jedisPool.getResource();
        try {
            resource.expire(key.getBytes(), i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            resource.close();
        }
    }

    public void delete(String key) {
        Jedis resource = jedisPool.getResource();
        try {
            resource.del(key.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            resource.close();
        }
    }

    public Object get(String key) {
        Jedis resource = jedisPool.getResource();
        try {
            byte[] bytes = resource.get(key.getBytes());
            return SerializationUtils.deserialize(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            resource.close();
        }
        return null;
    }

    public Set<byte[]> keys(String pattern) {
        Jedis resource = jedisPool.getResource();
        try {
            return resource.keys(pattern.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            resource.close();
        }
        return null;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
