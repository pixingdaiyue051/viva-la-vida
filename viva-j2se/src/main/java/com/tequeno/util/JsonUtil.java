package com.tequeno.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tequeno.temail.EmailResponse;

/**
 * @Desription:
 * @Author: hexk
 */
public class JsonUtil {

    public static void main(String[] args) {
        String str = "{\n" +
                "    \"1\": \"1\",\n" +
                "    \"2\": \"2\",\n" +
                "    \"obj\": {\n" +
                "        \"d\": \"d\",\n" +
                "        \"e\": \"e\"\n" +
                "    },\n" +
                "    \"arr\": [\n" +
                "        \"q\",\n" +
                "        \"w\",\n" +
                "        \"e\",\n" +
                "        \"e\"\n" +
                "    ]\n" +
                "}";
        EmailResponse r = new EmailResponse();
        r.setMsg("dew");
        r.setSuccess(true);
        String jsonString = JSON.toJSONString(r);
        System.out.println(jsonString);
        JSONObject jsonObject = JSON.parseObject(str);
        System.out.println(jsonObject);
        JSONArray arr = (JSONArray) jsonObject.get("arr");
        arr.forEach(System.out::println);

    }
}
