package com.zfsoft.batchimport.service;

import com.zfsoft.batchimport.domain.dto.CommonCreateTableDto;
import com.zfsoft.batchimport.domain.dto.CommonInsertDto;
import com.zfsoft.batchimport.domain.entity.TableStructure;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author: chenyq
 * @create: 2020-06-23 13:27:23
 * @description: 数据导入服务层
 */
public interface MiddleService {

    /**
     * 数据插入
     * @param commonInsertDto
     */
    void insertByCommonInsertDto(CommonInsertDto commonInsertDto);

    /**
     * 判断表是否存在  false -- 不存在  true -- 存在
     * @param tableName 表名称
     * @return
     */
    Boolean existTable(String tableName);

    /**
     * 删除表
     * @param tableName 表名
     * @return
     */
    Boolean dropTable(String tableName);

    /**
     * 创建一个新表
     * @param commonCreateTableDto
     * @return
     */
    Boolean createNewTable(CommonCreateTableDto commonCreateTableDto);
    /**
     * 返回当前表数据量
     * @param tableName 表名称
     * @return
     */
    Integer existData(String tableName,String licenseCode);

    /**
     * 根据批次号查询数据(正常情况下是1000条)
     * @author: chenyq
     * @create: 2020-07-02
     * @param tableName
     * @param batchNo
     * @return
     */
    List<HashMap<String,Object>> selectTableByBatchNo(String tableName, String batchNo );

    /**
     * 根据传入的表生成批次号和插入批次表
     * @author chenyq
     * @date 20200914
     * @return
     */
    void doneCallProduce(HashMap tableName);
    /**
     * 根据表名称和表字段获取总数量
     * @author: chenyq
     * @create: 2020-07-06
     * @param tableName
     * @param operaterStatus
     * @return
     */
    int getStatusNumber(String tableName,String operaterStatus);
}
