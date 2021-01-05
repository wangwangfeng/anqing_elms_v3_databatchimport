package com.zfsoft.batchimport.mapper.middle;

import com.zfsoft.batchimport.domain.dto.CommonCreateTableDto;
import com.zfsoft.batchimport.domain.dto.CommonInsertDto;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author chenyq
 * @Description: 中间库mapper
 * @date 2020/6/23 14:32
 */
public interface MiddleMapper {


    /**
     * 数据插入
     * @param commonInsertDto
     */
    void insertByCommonInsertDto(CommonInsertDto commonInsertDto);

    /**
     * 判断表是否存在  0 -- 不存在  1 -- 存在
     * @param tableName 表名称
     * @return
     */
    int existTable(String tableName);

    /**
     * 删除表
     * @param tableName 表名
     * @return
     */
    int dropTable(@Param("tableName") String tableName);

    /**
     * 创建一个新表
     * @param commonCreateTableDto
     * @return
     */
    int createNewTable(CommonCreateTableDto commonCreateTableDto);

    /**
     * 通过证照编号查询证照信息
     * @param licenseCode
     * @return
     */
    int existData(@Param("tableName") String tableName, @Param("licenseCode") String licenseCode);


    /**
     * 根据批次号查询数据(正常情况下是1000条)
     * @author chenyq
     * @date 20200509
     * @param tableName
     * @return
     */
    List<HashMap<String,Object>> selectTableByBatchNo(@Param("tableName") String tableName,@Param("batchNo") String batchNo );

    /**
     * 更新当前数据的状态
     * @param operaterMessage
     * @param tableName
     * @param licenseNumber
     * @param operaterStatus
     */
    void  updateTableByLicenseNumber(@Param("operaterMessage") String operaterMessage, @Param("tableName") String tableName, @Param("licenseNumber") String licenseNumber, @Param("operaterStatus") String operaterStatus);

    /**
     * 获取启动中的表结构 只获取一条
     * @author chenyq
     * @date 20200508
     * @return
     */
    void doneCallProduce(HashMap tableName);

    /**
     * 根据表名称和获取总数量
     * @author: chenyq
     * @create: 2020-07-06
     * @param tableName
     * @param operaterStatus
     * @return
     */
    int getStatusNumber(String tableName,String operaterStatus);


}
