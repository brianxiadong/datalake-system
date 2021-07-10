package com.brianxia.utils;

/**
 * @author brianxia
 * @version 1.0
 * @date 2021/7/9 18:42
 */
public class JsonUtil {
    public static com.alibaba.fastjson.JSONObject getJsonData(String data) {
        try {
            return com.alibaba.fastjson.JSONObject.parseObject(data);
        } catch (Exception e) {
            return null;
        }
    }

}
