package com.tequeno.json;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class JsonTest {

    public static void main(String[] args) {
        String json = "{\n" +
                "    \"code\":1,\n" +
                "    \"msg\":\"success\",\n" +
                "    \"data\":{\n" +
                "        \"id\":311,\n" +
                "        \"sign\":\"fsfsd\",\n" +
                "        \"role\":{\n" +
                "            \"id\":7564,\n" +
                "            \"name\":\"mkllkads\",\n" +
                "            \"permit\":{\n" +
                "                \"id\":435353,\n" +
                "                \"name\":\"456补偿办法规定\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";
        JsonTest jt = new JsonTest();
        jt.gson(json);
//        jt.fastjson(json);

    }

    private void gson(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // 1
//        RestDto dto1 = gson.fromJson(json, RestDto.class);
//        System.out.println(dto1);

        // 2
        TypeToken<UserDto> typeToken = new TypeToken<>() {
        };

        RestDto<List<UserDto>> res1 = gson.fromJson(json, typeToken.getType());
        System.out.println(res1);
    }

    private void fastjson(String json) {

        RestDto<List<UserDto>> dto1 = JSON.parseObject(json, RestDto.class);
        System.out.println(dto1);
    }
}