package com.tequeno.utils;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

public class RedisCacheManager implements CacheManager {

    protected RedisCache redisCache;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return getRedisCache();
    }

    public RedisCache getRedisCache() {
        return redisCache;
    }

    public void setRedisCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }
}
