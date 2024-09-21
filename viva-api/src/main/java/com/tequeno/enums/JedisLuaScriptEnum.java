package com.tequeno.enums;


import lombok.Getter;

@Getter
public enum JedisLuaScriptEnum {
    TRY_LOCK("luaTryLock", "/hexk/github/hey-jude/doc/lua/try_lock.lua"),
    RELEASE_LOCK("luaReleaseLock", "/hexk/github/hey-jude/doc/lua/release_lock.lua"),
    SEQUENCE_NUM("luaGetSequenceNum", "/hexk/github/hey-jude/doc/lua/sequence_num.lua"),
    KEYS_PATTERN("luaKeysByPattern", "/hexk/github/hey-jude/doc/lua/keys_pattern.lua"),
    DEL_KEYS_PATTERN("luaDelKeysByPattern", "/hexk/github/hey-jude/doc/lua/del_keys_pattern.lua"),
    ;
    private final String scriptName;
    private final String luaFilePath;

    JedisLuaScriptEnum(String scriptName, String luaFilePath) {
        this.scriptName = scriptName;
        this.luaFilePath = luaFilePath;
    }
}