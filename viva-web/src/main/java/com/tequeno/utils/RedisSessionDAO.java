package com.tequeno.utils;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RedisSessionDAO extends AbstractSessionDAO {

    protected JedisUtil jedisUtil;

    private final String PREFIX = "REDIS-SESSION:";

    private byte[] getKey(String sessionId) {
        return (PREFIX + sessionId).getBytes();
    }

    private Serializable saveOrUpdateSession(Session session) {
        if (session != null && session.getId() != null) {
            byte[] key = this.getKey(session.getId().toString());
            byte[] value = SerializationUtils.serialize(session);
            jedisUtil.saveOrUpdate(key, value);
            jedisUtil.expire(key, 600);
            return session.getId();
        } else {
            return null;
        }
    }

    @Override
    protected Serializable doCreate(Session session) {
        System.out.println("doCreate");
//        生成sessionId，使用uuid策略，调用UUID.randomUUID().toString();
        Serializable sessionId = this.generateSessionId(session);
        System.out.println(sessionId);
//        将session和sessionI绑定，调用session.setId()方法
        this.assignSessionId(session, sessionId);
        return this.saveOrUpdateSession(session);
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        System.out.println("doReadSession");
        if (sessionId != null) {
            return null;
        }
        System.out.println("doReadSession11");
        byte[] key = this.getKey(sessionId.toString());
        return (Session) SerializationUtils.deserialize(jedisUtil.get(key));
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        System.out.println("update");
        this.saveOrUpdateSession(session);
    }

    @Override
    public void delete(Session session) {
        System.out.println("delete");
        jedisUtil.del(this.getKey(session.getId().toString()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        System.out.println("getActiveSessions");
        Set<byte[]> keys = jedisUtil.keys(PREFIX + "*");
        Set<Session> sessions = new HashSet<>();
        if (CollectionUtils.isEmpty(keys)) {
            return sessions;
        }
        for (byte[] key : keys) {
            Session session = (Session) SerializationUtils.deserialize(jedisUtil.get(key));
            sessions.add(session);
        }
        return sessions;
    }

    public JedisUtil getJedisUtil() {
        return jedisUtil;
    }

    public void setJedisUtil(JedisUtil jedisUtil) {
        this.jedisUtil = jedisUtil;
    }
}
