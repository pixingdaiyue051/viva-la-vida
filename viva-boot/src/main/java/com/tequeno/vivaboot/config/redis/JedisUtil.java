package com.tequeno.vivaboot.config.redis;

import com.tequeno.constants.HtCommonConstant;
import com.tequeno.constants.HtPropertyConstant;
import com.tequeno.constants.HtZeroOneConstant;
import com.tequeno.enums.JedisKeyPrefixEnum;
import com.tequeno.enums.JedisLockTimeEnum;
import com.tequeno.enums.JedisLuaScriptEnum;
import com.tequeno.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import redis.clients.jedis.*;

import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 不依赖spring的jedis服务，单例模式 使用jedisPool支持多线程
 */
public class JedisUtil {

    private final static Logger logger = LoggerFactory.getLogger(JedisUtil.class);

    private JedisPool jedisPool;

    private Map<String, String> scriptMap;

    private static class JedisUtilHolder {

        private static final JedisUtil INSTANCE;

        static {
            INSTANCE = new JedisUtil();
            logger.info("JedisUtilHolder start...");

            ClassPathResource c = new ClassPathResource("application.yml");

            try (InputStream inputStream = c.getInputStream()) {
//                Yaml yaml = new Yaml();
//                HashMap map = yaml.loadAs(inputStream, HashMap.class);
//                HashMap fileMap = (HashMap) (map.get("file"));
//
//                map = (HashMap) (map.get("spring"));
//                map = (HashMap) (map.get("redis"));
//                String host = map.get("host").toString();
//                int port = (int) map.get("port");
//                String password = map.get("password").toString();
//                int timeout = (int) map.get("timeout");
//                int database = (int) map.get("database");
//                INSTANCE.jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout, password, database);
//                INSTANCE.scriptMap = new HashMap<>();
//
//                INSTANCE.luaPath = fileMap.get("lua").toString();


                YamlPropertiesFactoryBean bean = new YamlPropertiesFactoryBean();
                bean.setResources(new InputStreamResource(inputStream));
                Properties properties = bean.getObject();
                assert properties != null;
                String host = properties.getProperty("redis.host");
                int port = Integer.parseInt(properties.getProperty("redis.port"));
                int database = Integer.parseInt(properties.getProperty("redis.database"));
                int timeout = Integer.parseInt(properties.getProperty("redis.timeout"));
                String password = properties.getProperty("redis.password");

                boolean blockWhenExhausted = Boolean.parseBoolean(properties.getProperty("redis.block-when-exhausted"));
                boolean jmxEnabled = Boolean.parseBoolean(properties.getProperty("redis.jmx-enabled"));
                int minIdle = Integer.parseInt(properties.getProperty("redis.min-idle"));
                int maxIdle = Integer.parseInt(properties.getProperty("redis.max-idle"));
                int maxTotal = Integer.parseInt(properties.getProperty("redis.max-active"));
                long maxWaitMillis = Long.parseLong(properties.getProperty("redis.max-wait"));
                JedisPoolConfig poolConfig = new JedisPoolConfig();
                poolConfig.setBlockWhenExhausted(blockWhenExhausted);
                poolConfig.setJmxEnabled(jmxEnabled);
                poolConfig.setMinIdle(minIdle);
                poolConfig.setMaxIdle(maxIdle);
                poolConfig.setMaxTotal(maxTotal);
                poolConfig.setMaxWaitMillis(maxWaitMillis);

                INSTANCE.jedisPool = new JedisPool(poolConfig, host, port, timeout, password, database);
                INSTANCE.scriptMap = new HashMap<>();

                Jedis jedis = INSTANCE.jedisPool.getResource();
                for (JedisLuaScriptEnum scriptEnum : JedisLuaScriptEnum.values()) {
                    String scriptName = scriptEnum.getScriptName();
                    String luaFilePath = scriptEnum.getLuaFilePath();
                    String script = INSTANCE.loadScriptFromDisk(luaFilePath);
                    String scriptSha = jedis.scriptLoad(script);
                    INSTANCE.scriptMap.put(scriptName, scriptSha);
                }
                INSTANCE.closeJedis(jedis);
            } catch (Exception e) {
                logger.error("JedisUtilHolder异常:", e);
            }
        }
    }

    public static JedisUtil getInstance() {
        return JedisUtil.JedisUtilHolder.INSTANCE;
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    public void closeJedis(Jedis jedis) {
        if (null != jedis) {
            jedis.disconnect();
            jedis.close();
        }
    }

    //string 操作//////////////////////////////////////////////////////////////////////

    /**
     * string 设置key,使用默认超时时间
     *
     * @param key
     * @param value
     * @return 除非异常否则都是true
     */
    public boolean stringSetDefault(String key, String value) {
        return stringSet(key, value, HtPropertyConstant.DEFAULT_REDIS_KEY_TIMEOUT);
    }

    /**
     * string 设置key,不超时
     *
     * @param key
     * @param value
     * @return 除非异常否则都是true
     */
    public boolean stringSetPersist(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String result = jedis.set(key, value);
            return HtCommonConstant.OK.equals(result);
        } catch (Exception e) {
            logger.info("stringSetPersist(String,String)异常:", e);
            return false;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * string 设置key,指定超时时间
     *
     * @param key
     * @param value
     * @param expiredTime 单位ms
     * @return 除非异常否则都是true
     */
    public boolean stringSet(String key, String value, long expiredTime) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String result = jedis.psetex(key, expiredTime, value);
            return HtCommonConstant.OK.equals(result);
        } catch (Exception e) {
            logger.info("stringSet(String,String,long[{}])异常:", expiredTime, e);
            return false;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * string 同时设置多个key,value,使用默认超时时间
     *
     * @param stringMap
     * @return 除非异常否则都是true
     */
    public boolean stringSetDefault(Map<String, String> stringMap) {
        return stringSet(stringMap, HtPropertyConstant.DEFAULT_REDIS_KEY_TIMEOUT);
    }

    /**
     * string 同时设置多个key,value,不超时
     *
     * @param stringMap
     * @return 除非异常否则都是true
     */
    public boolean stringSetPersist(Map<String, String> stringMap) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipe = jedis.pipelined();
            pipe.multi();
            stringMap.forEach(pipe::set);
            pipe.exec();
            pipe.sync();
            return true;
        } catch (Exception e) {
            logger.info("stringSetPersist(Map)异常:", e);
            return false;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * string 同时设置多个key,value使用指定超时时间
     *
     * @param stringMap
     * @param expiredTime 单位ms
     * @return 除非异常否则都是true
     */
    public boolean stringSet(Map<String, String> stringMap, long expiredTime) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipe = jedis.pipelined();
            pipe.multi();
            stringMap.forEach((k, v) -> pipe.psetex(k, expiredTime, v));
            pipe.exec();
            pipe.sync();
            return true;
        } catch (Exception e) {
            logger.info("stringSet(Map,long[{}])异常:", expiredTime, e);
            return false;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * string 根据key返回value
     *
     * @param key
     * @return key不存在返回null, 异常返回null
     */
    public String stringGet(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            logger.info("stringGet(String)异常:", e);
            return null;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * string 一次返回多个value,忽略不存在的key
     *
     * @param keyList
     * @return 如果所有key都不存在返回非null空集合, 异常返回null
     */
    public List<String> stringGet(List<String> keyList) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipe = jedis.pipelined();
            keyList.forEach(pipe::get);
            List<Object> txResult = pipe.syncAndReturnAll();
            return txResult.parallelStream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.info("stringGet(String)异常:", e);
            return null;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * string 同时删除多个key
     *
     * @param keys
     * @return 除非异常否则都是true
     */
    public boolean stringDel(String... keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long result = jedis.del(keys);
            logger.info("redis删除{}个key", result);
            return true;
        } catch (Exception e) {
            logger.info("stringDel(String)异常:", e);
            return false;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * string 同时删除多个key
     *
     * @param keyList
     * @return 除非异常否则都是true
     */
    public boolean stringDel(List<String> keyList) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipe = jedis.pipelined();
            pipe.multi();
            keyList.forEach(pipe::del);
            pipe.exec();
            List<Object> txResult = pipe.syncAndReturnAll();
            long result = ((ArrayList) txResult.get(txResult.size() - 1)).parallelStream().mapToLong(r -> Long.valueOf(r.toString())).sum();
            logger.info("redis删除{}个key", result);
            return true;
        } catch (Exception e) {
            logger.info("stringDel(List)异常:", e);
            return false;
        } finally {
            closeJedis(jedis);
        }
    }

    //hash 操作//////////////////////////////////////////////////////////////////////

    /**
     * hash 设置单个key的field,使用默认超时时间
     *
     * @param key
     * @param field
     * @param value
     * @return 除非异常否则都是true
     */
    public boolean hashSetDefault(String key, String field, String value) {
        return hashSet(key, field, value, HtPropertyConstant.DEFAULT_REDIS_KEY_TIMEOUT);
    }

    /**
     * hash 设置单个key的field,不超时
     *
     * @param key
     * @param field
     * @param value
     * @return 除非异常否则都是true
     */
    public boolean hashSetPersist(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hset(key, field, value);
            return true;
        } catch (Exception e) {
            logger.info("hashSetPersist(String,String,String)异常:", e);
            return false;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * hash 设置单个key的field,使用指定超时时间
     *
     * @param key
     * @param field
     * @param value
     * @param expiredTime 单位ms
     * @return 除非异常否则都是true
     */
    public boolean hashSet(String key, String field, String value, long expiredTime) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Transaction tx = jedis.multi();
            tx.hset(key, field, value);
            tx.pexpire(key, expiredTime);
            tx.exec();
            return true;
        } catch (Exception e) {
            logger.info("hashSet(String,String,String,long[{}])异常:", expiredTime, e);
            return false;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * hash 设置同一个key的多个field,使用默认超时时间
     *
     * @param key
     * @param fields
     * @return 除非异常否则都是true
     */
    public boolean hashMultiSetDefault(String key, Map<String, String> fields) {
        return hashMultiSet(key, fields, HtPropertyConstant.DEFAULT_REDIS_KEY_TIMEOUT);
    }

    /**
     * hash 设置同一个key的多个field,不超时
     *
     * @param key
     * @param fields
     * @return 除非异常否则都是true
     */
    public boolean hashMultiSetPersist(String key, Map<String, String> fields) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hmset(key, fields);
            return true;
        } catch (Exception e) {
            logger.info("hashMultiSetPersist(String,Map)异常:", e);
            return false;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * hash 设置同一个key的多个field,使用指定超时时间
     *
     * @param key
     * @param fields
     * @param expiredTime 单位ms
     * @return 除非异常否则都是true
     */
    public boolean hashMultiSet(String key, Map<String, String> fields, long expiredTime) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Transaction tx = jedis.multi();
            tx.hmset(key, fields);
            tx.pexpire(key, expiredTime);
            tx.exec();
            return true;
        } catch (Exception e) {
            logger.info("hashMultiSet(String,Map,long[{}])异常:", expiredTime, e);
            return false;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * hash 返回指定field的value
     *
     * @param key
     * @param field
     * @return 异常返回null
     */
    public String hashGet(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(key, field);
        } catch (Exception e) {
            logger.info("hashGet(String,String)异常:", e);
            return null;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * hash 返回多个field的value集合,如果只指定hashKey返回全部value结合
     *
     * @param key
     * @param fields
     * @return 异常返回null
     */
    public List<String> hashMultiGet(String key, String... fields) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (null == fields || 0 == fields.length) {
                return jedis.hvals(key);
            }
            List<String> txResult = jedis.hmget(key, fields);
            return txResult.parallelStream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.info("hashMultiGet(String,String)异常:", e);
            return null;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * hash 返回多个field的value集合
     *
     * @param key
     * @param fieldList
     * @return 异常返回null
     */
    public List<String> hashMultiGet(String key, List<String> fieldList) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipe = jedis.pipelined();
            fieldList.forEach(field -> pipe.hmget(key, field));
            List<Object> txResult = pipe.syncAndReturnAll();
            return txResult.parallelStream()
                    .map(obj -> ((ArrayList<String>) obj).get(0))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.info("hashMultiGet(String,List)异常:", e);
            return null;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * hash 删除指定field
     *
     * @param key
     * @param fields
     * @return 除非异常否则都是true
     */
    public boolean hashDel(String key, String... fields) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long result = jedis.hdel(key, fields);
            logger.info("redis删除hashKey[{}]内{}个field", key, result);
            return true;
        } catch (Exception e) {
            logger.info("hashDel(String,String)异常:", e);
            return false;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * hash 删除指定field
     *
     * @param key
     * @param fieldList
     * @return 除非异常否则都是true
     */
    public boolean hashDel(String key, List<String> fieldList) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipe = jedis.pipelined();
            pipe.multi();
            fieldList.forEach(filed -> pipe.hdel(key, filed));
            pipe.exec();
            List<Object> txResult = pipe.syncAndReturnAll();
            long result = ((ArrayList) txResult.get(txResult.size() - 1)).parallelStream().mapToLong(r -> Long.valueOf(r.toString())).sum();
            logger.info("redis删除hashKey[{}]内{}个field", key, result);
            return true;
        } catch (Exception e) {
            logger.info("hashDel(String,List)异常:", e);
            return false;
        } finally {
            closeJedis(jedis);
        }
    }


    /**
     * @return redis服务器时间
     */
    public String time() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.time().get(0);
        } catch (Exception e) {
            logger.info("time()异常:", e);
            return null;
        } finally {
            closeJedis(jedis);
        }
    }


    //lua 操作//////////////////////////////////////////////////////////////////////

    /**
     * 模式匹配key并删除key
     *
     * @param pattern ?单匹配 *全匹配 []范围匹配
     * @return 除非异常否则都是true
     */
    public boolean luaDelKeysByPattern(String pattern) {
        Jedis jedis = null;
        try {
            String scriptSha = scriptMap.get(JedisLuaScriptEnum.DEL_KEYS_PATTERN.getScriptName());
            jedis = jedisPool.getResource();
            Long result = (Long) jedis.evalsha(scriptSha, Collections.singletonList(pattern), Collections.emptyList());
            logger.info("redis删除{}个key", result);
            return true;
        } catch (Exception e) {
            logger.info("luaDelKeysByPattern(String)异常:", e);
            return false;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 模式匹配key并说返回key集合
     *
     * @param pattern ?单匹配 *全匹配 []范围匹配
     * @return key集合, 异常返回null
     */
    public List<String> luaKeysByPattern(String pattern) {
        Jedis jedis = null;
        try {
            String scriptSha = scriptMap.get(JedisLuaScriptEnum.KEYS_PATTERN.getScriptName());
            jedis = jedisPool.getResource();
            return (ArrayList<String>) jedis.evalsha(scriptSha, Collections.singletonList(pattern), Collections.emptyList());
        } catch (Exception e) {
            logger.info("luaKeysByPattern(String)异常:", e);
            return null;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 自增序列号
     *
     * @param key        序列key
     * @param value      自增初始值
     * @param expireTime 序列号超时时间
     * @return 经过自增后的序列值, 异常返回null
     */
    public Object luaGetSequenceNum(String key, long value, long expireTime) {
        Jedis jedis = null;
        try {
            key = JedisKeyPrefixEnum.SEQ.assemblyKey(key);
            String scriptSha = scriptMap.get(JedisLuaScriptEnum.SEQUENCE_NUM.getScriptName());
            jedis = jedisPool.getResource();
            return jedis.evalsha(scriptSha, Collections.singletonList(key), Arrays.asList(String.valueOf(value), String.valueOf(expireTime)));
        } catch (Exception e) {
            logger.info("luaGetSequenceNum(String,long,long)异常:", e);
            return null;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 根据日期获取序列,超时时间为1天
     *
     * @param value 自增初始值
     * @return 自增后的序列号(自带前缀), 异常返回null
     */
    public Object luaGetSequenceNum(long value) {
        String day = TimeUtil.nowDateNum();
        return luaGetSequenceNum(day, value, HtPropertyConstant.ONE_DAY);
    }

    /**
     * 分布式锁
     *
     * @param lockKey    锁的唯一key
     * @param token      随机生成token作为删除标志
     * @param expireTime 过期时间,单位ms
     * @return 是否成功加锁
     */
    public boolean luaTryLock(String lockKey, String token, long expireTime) {
        Jedis jedis = null;
        try {
            lockKey = JedisKeyPrefixEnum.LOCK.assemblyKey(lockKey);
            String scriptSha = scriptMap.get(JedisLuaScriptEnum.TRY_LOCK.getScriptName());
            jedis = jedisPool.getResource();
            Object result = jedis.evalsha(scriptSha, Collections.singletonList(lockKey), Arrays.asList(token, String.valueOf(expireTime)));
            return HtZeroOneConstant.ONE_L.equals(result);
        } catch (Exception e) {
            logger.info("luaTryLock(String,String,long)异常:", e);
            return false;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 分布式锁
     *
     * @param lockKey      锁的唯一key
     * @param token        随机生成token作为删除标志
     * @param lockTimeEnum 加锁策略
     * @return 是否成功加锁
     */
    public boolean luaTryLock(String lockKey, String token, JedisLockTimeEnum lockTimeEnum) {
        Jedis jedis = null;
        try {
            lockKey = JedisKeyPrefixEnum.LOCK.assemblyKey(lockKey);
            jedis = jedisPool.getResource();
            String scriptSha = scriptMap.get(JedisLuaScriptEnum.TRY_LOCK.getScriptName());
            boolean isLocked;
            String expireTime = String.valueOf(lockTimeEnum.getExpireTime());
            long retryEvictTime = lockTimeEnum.getRetryEvictTime();
            long evictTime = lockTimeEnum.getEvictTime();
            long startMillSecond = System.currentTimeMillis();
            do {
                Object result = jedis.evalsha(scriptSha, Collections.singletonList(lockKey), Arrays.asList(token, String.valueOf(expireTime)));
                isLocked = HtZeroOneConstant.ONE_L.equals(result);
                if (isLocked) {
                    logger.info("根据key[{}]获取锁成功", lockKey);
                    return true;
                }
                logger.info("尝试根据key[{}]获取锁失败,{}ms后重试", lockKey, retryEvictTime);
                Thread.sleep(retryEvictTime);
                if (System.currentTimeMillis() - startMillSecond > evictTime) {
                    logger.info("尝试根据key[{}]获取锁失败,已超出最大等待时间{}ms", lockKey, evictTime);
                    return false;
                }
            } while (!isLocked);
            return false;
        } catch (Exception e) {
            logger.info("luaTryLock(String,String,JedisLockTimeEnum)异常:", e);
            return false;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 释放分布式锁
     *
     * @param lockKey 锁的唯一key
     * @param token   随机生成token作为删除标志
     * @return 是否释放锁
     */
    public void luaReleaseLock(String lockKey, String token) {
        Jedis jedis = null;
        try {
            lockKey = JedisKeyPrefixEnum.LOCK.assemblyKey(lockKey);
            String scriptSha = scriptMap.get(JedisLuaScriptEnum.RELEASE_LOCK.getScriptName());
            jedis = jedisPool.getResource();
            jedis.evalsha(scriptSha, Collections.singletonList(lockKey), Collections.singletonList(token));
        } catch (Exception e) {
            logger.info("luaTryLock(String,String)异常:", e);
        } finally {
            closeJedis(jedis);
        }
    }

    private String loadScriptFromDisk(String luaFilePath) throws Exception {
        FileChannel inChannel = FileChannel.open(Paths.get(luaFilePath), StandardOpenOption.READ);
        MappedByteBuffer inMapper = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        byte[] b = new byte[inMapper.limit()];
        inMapper.get(b);
        String script = new String(b);
        inChannel.close();
        return script;
    }
}