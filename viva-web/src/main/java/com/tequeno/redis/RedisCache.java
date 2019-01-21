package com.tequeno.redis;

import com.tequeno.utils.ConstantsUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.Collection;
import java.util.Set;

public class RedisCache<K, V> implements Cache<K, V> {

    protected JedisUtil jedisUtil;

    private final String PREFIX = ConstantsUtil.REDIS_CHCHE_PREFIX;

    private String getKey(K k) {
        return (PREFIX + k);
    }

    @Override
    public V get(K k) throws CacheException {
        return (V) jedisUtil.get(this.getKey(k));
    }

    @Override
    public V put(K k, V v) throws CacheException {
        String key = this.getKey(k);
        jedisUtil.saveOrUpdate(key, v);
        jedisUtil.expire(key, 600);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        String key = this.getKey(k);
        jedisUtil.delete(key);
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