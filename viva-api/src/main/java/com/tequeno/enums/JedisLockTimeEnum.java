package com.tequeno.enums;

/**
 * @Desription:
 * @Author: hexk
 */
public enum JedisLockTimeEnum {

    /**
     * 每秒尝试一次，最长等待1分钟
     */
    QUICK(5 * 60 * 1000L, 1000L, 60 * 1000L),
    /**
     * 每5秒尝试一次，最长等待1分钟
     */
    SLOW(5 * 60 * 1000L, 5 * 1000L, 60 * 1000L),
    /**
     * 每3秒尝试一次，最长等待1分钟
     */
    COMMON(5 * 60 * 1000L, 3000L, 60 * 1000L);

    /**
     * redis锁过期时间
     */
    private final long expireTime;

    /**
     * 重复获取锁间隔
     */
    private final long retryEvictTime;

    /**
     * 最大等待时间
     */
    private final long evictTime;

    JedisLockTimeEnum(long expireTime, long retryEvictTime, long evictTime) {
        this.expireTime = expireTime;
        this.retryEvictTime = retryEvictTime;
        this.evictTime = evictTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public long getRetryEvictTime() {
        return retryEvictTime;
    }

    public long getEvictTime() {
        return evictTime;
    }

}
