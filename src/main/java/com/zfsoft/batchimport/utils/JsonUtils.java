package com.zfsoft.batchimport.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: kkfan
 * @create: 2020-02-26 18:38:42
 * @description: json帮助类
 */
public class JsonUtils {
    /**
     * 判断字符串是否为JSON类型
     * @param str
     * @return
     */
    public static boolean isJson(String str){
        try {
            JSONObject jsonStr= JSONObject.parseObject(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
