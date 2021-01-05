package com.zfsoft.batchimport.utils;

import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author: kkfan
 * @create: 2020-02-26 14:53:03
 * @description: 命名转化
 */
public class NamingConversion {
    /***
     * 下划线命名转为驼峰命名
     *
     * @param para
     *        下划线命名的字符串
     */

    public static String underlineToHump(String para){
        StringBuilder result=new StringBuilder();
        String a[]=para.split("_");
        for(String s:a){
            if (!para.contains("_")) {
                result.append(s);
                continue;
            }
            if(result.length()==0){
                result.append(s.toLowerCase());
            }else{
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }



    /***
     * 驼峰命名转为下划线命名
     *
     * @param para
     *        驼峰命名的字符串
     */

    public static String humpToUnderline(String para){
        StringBuilder sb=new StringBuilder(para);
        int temp=0;//定位
        if (!para.contains("_")) {
            for(int i=0;i<para.length();i++){
                if(Character.isUpperCase(para.charAt(i))){
                    sb.insert(i+temp, "_");
                    temp+=1;
                }
            }
        }
        return sb.toString().toUpperCase();
    }

    /**
     * map key驼峰转下划线
     * @param filedMap map
     * @return  LinkedHashMap
     */
    public static Map<String, Object> humpToUnderlineForMap(Map<String, Object> filedMap) {
        if(null == filedMap) {
            return filedMap;
        }
        Map<String, Object> newMap = Maps.newLinkedHashMap();
        filedMap.forEach((k, v) -> {
            // 排除mongodb _id
            if(!StringUtils.equalsAnyIgnoreCase(k, "_id", "SYSORGAN")) {
                if(v instanceof Date) {
                    try {
                        newMap.put(humpToUnderline(k), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    newMap.put(humpToUnderline(k), v);
                }
            }
        });
        return newMap;
    }

}
