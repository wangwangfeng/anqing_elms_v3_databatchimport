package com.zfsoft.batchimport.service;


/**
 * @author: kkfan
 * @create: 2020-02-26 11:20:02
 * @description:
 */
public interface MongoDBService {

    /***
     * 获取mongodb中输入插入mysql
     * @param id
     * @param tableName
     */
    void insertTable(String id, String tableName);
}
