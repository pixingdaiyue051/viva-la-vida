package com.tequeno.netty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum NettyKeyEnum {

    READ_IDLE("read_idle", "未收到消息"),
    CHANNEL_INACTIVE("channel_inactive", "channel已断开"),
    ;
    private final String key;

    private final String desc;

    NettyKeyEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public static Map<String, String> map() {
        return NettyKeyHolder.map;
    }

    public static NettyKeyEnum fetch(String key) {
        return NettyKeyHolder.map1.get(key);
    }

    private static class NettyKeyHolder {
        private final static Map<String, String> map = new HashMap<>(11);
        private final static Map<String, NettyKeyEnum> map1 = new HashMap<>(11);

        static {
            Arrays.stream(NettyKeyEnum.values()).forEach(item -> {
                map.put(item.key, item.desc);
                map1.put(item.key, item);
            });
        }
    }
}
