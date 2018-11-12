package com.tequeno.utils;

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

    public void saveOrUpdate(byte[] key, byte[] value) {
        Jedis resource = jedisPool.getResource();
        try {
            resource.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            resource.close();
        }
    }

    public void expire(byte[] key, int i) {
        Jedis resource = jedisPool.getResource();
        try {
            resource.expire(key, i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            resource.close();
        }
    }

    public void del(byte[] key) {
        Jedis resource = jedisPool.getResource();
        try {
            resource.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            resource.close();
        }
    }

    public byte[] get(byte[] key) {
        Jedis resource = jedisPool.getResource();
        try {
            return resource.get(key);
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
