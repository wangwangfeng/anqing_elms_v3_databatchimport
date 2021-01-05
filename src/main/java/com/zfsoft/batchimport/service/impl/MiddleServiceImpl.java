package com.zfsoft.batchimport.service.impl;

import com.zfsoft.batchimport.domain.dto.CommonCreateTableDto;
import com.zfsoft.batchimport.domain.dto.CommonInsertDto;
import com.zfsoft.batchimport.mapper.middle.MiddleMapper;
import com.zfsoft.batchimport.service.MiddleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;

/**
 * @author: chenyq
 * @create: 2020-06-23 13:27:23
 * @description: 数据导入服务层
 */
@Service
@RestControllerAdvice
public class MiddleServiceImpl implements MiddleService {

    @Autowired
    private MiddleMapper middleMapper;

    @Override
    public void insertByCommonInsertDto(CommonInsertDto commonInsertDto) {
        Assert.notNull(commonInsertDto, "插入数据不能为空！");
        this.middleMapper.insertByCommonInsertDto(commonInsertDto);
    }

    @Override
    public Boolean existTable(String tableName) {
        Assert.hasLength(tableName, "表名称不能为空！");
        int res = this.middleMapper.existTable(tableName);
        return res == 0 ? false : true;
    }

    @Override
    public Boolean dropTable(String tableName) {
        Assert.hasLength(tableName, "表名称不能为空！");
        int res = this.middleMapper.dropTable(tableName);
        return res == 0 ? true : false;
    }

    @Override
    public Boolean createNewTable(CommonCreateTableDto commonCreateTableDto) {
        Assert.notNull(commonCreateTableDto, "建表数据不能为空！");
        int res = this.middleMapper.createNewTable(commonCreateTableDto);
        return res == 0 ? true : false;
    }
    /**
     * 判断表数据是否存在  false -- 不存在  true -- 存在
     * @param tableName 表名称
     * @return
     */
    public Integer existData(String tableName,String licenseCode) {
        Assert.hasLength(tableName, "表名称不能为空！");
        int res = this.middleMapper.existData(tableName,licenseCode);
        return res ;
    }

    /**
     * 根据批次号查询数据(正常情况下是1000条)
     * @param tableName
     * @param batchNo
     * @return
     */
    public List<HashMap<String,Object>> selectTableByBatchNo(String tableName, String batchNo ){
        Assert.hasLength(tableName, "表名称不能为空！");
        List<HashMap<String,Object>>  list = this.middleMapper.selectTableByBatchNo(tableName,batchNo);
        return list;
    }
    /**
     * 根据传入的表生成批次号和插入批次表
     * @author chenyq
     * @date 20200914
     * @return
     */
    public void doneCallProduce(HashMap tableName){
        middleMapper.doneCallProduce(tableName);
    }
    /**
     * 根据传入的表生成批次号和插入批次表
     * @author chenyq
     * @date 20200914
     * @return
     */

    @Override
    public int getStatusNumber(String tableName,String operaterStatus){
        return middleMapper.getStatusNumber(tableName,operaterStatus);
    }
}
