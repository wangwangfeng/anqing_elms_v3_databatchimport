package com.zfsoft.batchimport.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * properties配置文件的读写
 * 
 * @author gaolh
 * @date 2016-3-24
 * 
 */
public class PropertiesUtil {
    private String properiesName = "";
    private InputStream is = null;
    private Properties p;

    /**
     * 初始化帮助类
     * 
     * @param fileName
     *            配置文件路径
     */
    public PropertiesUtil(String fileName) {
        this.properiesName = fileName;
        try {
            is = PropertiesUtil.class.getClassLoader().getResourceAsStream(properiesName);
            p = new Properties();
            p.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据key，读取配置的值
     * 
     * @author gaolh
     * @date 2016-3-24
     * 
     * @param key
     *            配置文件的key值
     * @return
     */
    public String readProperty(String key) {
        return p.getProperty(key);
    }

    public Properties getProperties() {
        return p;
    }

    /**
     * 将key、value写入配置文件中
     * 
     * @author gaolh
     * @date 2016-3-24
     * @param key
     *            需要写入的key
     * @param value
     *            需要写入的值
     */
    public void writeProperty(String key, String value) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(PropertiesUtil.class.getClassLoader().getResource(properiesName).getFile());

            p.setProperty(key, value);
            p.store(os, key);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is)
                    is.close();
                if (null != os)
                    os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
