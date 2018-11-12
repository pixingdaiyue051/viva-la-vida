package com.tequeno.utils;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.util.SerializationUtils;

import java.util.Collection;
import java.util.Set;

public class RedisCache<K, V> implements Cache<K, V> {

    protected JedisUtil jedisUtil;

    private final String PREFIX = "REDIS-AUTH";

    private byte[] getKey(K k) {
        return (PREFIX + k).getBytes();
    }

    @Override
    public V get(K k) throws CacheException {
        byte[] value = jedisUtil.get(this.getKey(k));
        return (V) SerializationUtils.deserialize(value);
    }

    @Override
    public V put(K k, V v) throws CacheException {
        byte[] key = this.getKey(k);
        byte[] value = SerializationUtils.serialize(v);
        jedisUtil.saveOrUpdate(key, value);
        jedisUtil.expire(key, 600);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        byte[] key = this.getKey(k);
        jedisUtil.del(key);
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    public JedisUtil getJedisUtil() {
        return jedisUtil;
    }

    public void setJedisUtil(JedisUtil jedisUtil) {
        this.jedisUtil = jedisUtil;
    }
}
