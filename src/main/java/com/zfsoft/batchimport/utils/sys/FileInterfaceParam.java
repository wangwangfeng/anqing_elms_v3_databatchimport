package com.zfsoft.batchimport.utils.sys;

import com.zfsoft.batchimport.utils.PropertiesLoaderUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author: kkfan
 * @create: 2020-02-17 09:55:31
 * @description: 基础参数类
 */
public class FileInterfaceParam {
    // 系统Key
    public static String ApiKey;
    // 密文
    public static String PUBLIC_KEY;
    // 系统私钥（用于加密和签名）
    public static String PRIVATE_KEY;
    // 请求地址
    public static String UP_DOWN_URL;
    public static String OTHER_URL;

    private static final String URL_SFX ="/fileSer/pubInterface";

    private static PropertiesLoaderUtils propertiesUtil;


    static {
        if(propertiesUtil == null) {
            PropertiesLoaderUtils env = new PropertiesLoaderUtils("classpath:prop/common.properties");
            propertiesUtil = new PropertiesLoaderUtils("prop/"+env.getProperty("profiles.env")+"/fileSystem.properties");
        }

        if(ApiKey == null) {

            ApiKey = propertiesUtil.getProperty("apiKey");
        }
        if(PRIVATE_KEY == null) {
            PRIVATE_KEY = propertiesUtil.getProperty("privateKey");
        }
        if(PUBLIC_KEY == null) {
            PUBLIC_KEY = propertiesUtil.getProperty("publicKey");
        }
        if(UP_DOWN_URL == null) {
            UP_DOWN_URL = propertiesUtil.getProperty("updownURL")+URL_SFX;
        }
        if(OTHER_URL == null) {
            OTHER_URL = propertiesUtil.getProperty("otherUrl")+URL_SFX;
        }

    }
}
