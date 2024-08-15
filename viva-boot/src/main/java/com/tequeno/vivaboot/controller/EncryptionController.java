package com.tequeno.vivaboot.controller;

import com.tequeno.annos.EncryptionAnno;
import com.tequeno.dto.HtResultModel;
import com.tequeno.utils.HtResultUtil;
import com.tequeno.utils.RsaUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EncryptionController {

    @RequestMapping("encrypt")
    @EncryptionAnno
    public Map<String, String> encrypt() {
        String dataString = "of you";

        Map<String, String> result = new HashMap<>();
        result.put("data", dataString);
        result.put("code", "0");

        return result;
    }

    @RequestMapping("decrypt")
    @EncryptionAnno(EncryptionAnno.Type.DECRYPT)
    public Map<String, String> decrypt(@RequestBody Map<String, String> map) {
        Map<String, String> res = new HashMap<>();
        res.put("data", map.get("param1"));
        res.put("code", "0");
        return res;
    }

    @RequestMapping("handshake")
    @EncryptionAnno(EncryptionAnno.Type.HANDSHAKE)
    public Map<String, String> handshake(@RequestBody Map<String, String> map) {
        Map<String, String> res = new HashMap<>();
        res.put("data", "hasta la vista baby");
        res.put("code", "0");
        return res;
    }

    @RequestMapping("exchange")
    public HtResultModel exchange(@RequestParam String publicKey) {
        Map<String, String> keyMap = RsaUtil.genKeyPair();
        keyMap.put(RsaUtil.JS_PUBLIC_KEY, publicKey);
        return HtResultUtil.success(keyMap.get(RsaUtil.PUBLIC_KEY));
    }
}