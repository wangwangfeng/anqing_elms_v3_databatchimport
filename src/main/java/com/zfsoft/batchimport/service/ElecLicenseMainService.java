package com.zfsoft.batchimport.service;

import com.zfsoft.batchimport.domain.dto.ElecDirClaim;
import com.zfsoft.batchimport.domain.dto.ElecLicenseMainData;
import com.zfsoft.batchimport.domain.entity.ElecLicenseMain;

import java.util.Map;

/**
 * @author: chenyq
 * @create: 2020-04-26
 * @description: 证照表Service
 */
public interface ElecLicenseMainService {
    /**
     * 保存ES电子证照信息并创建ofd文件
     *
     * @author chenyq
     * @date 2020年03月03日
     * @param elecLicenseMain 证照表
     * @return
     */
    String saveOrUpdateElecLicenseMainWithES(ElecLicenseMain elecLicenseMain, String districtOid,String organOid,String directoryObj,String directoryName) throws Exception;

    /**
     *根据证照主键查询证照主表当前证照编号是否存在
     * @author chenyq
     * @date 2020年04月29日
     * @param LicenseNumber
     * @return
     * @throws Exception
     */
    int viewMainByLicenseNumber(String LicenseNumber) throws Exception;

    /**
     * @author chenyq
     * @date 2020年04月29日
     * @param LicenseNumber
     * @return
     * @throws Exception
     */
    int viewSubedByLicenseNumber(String LicenseNumber) throws Exception;

    /**
     * 根据证照oid获取证照
     * @author chenyq
     * @date 2020年05月19日
     * @param oid
     * @return
     * @throws Exception
     */
    ElecLicenseMainData getElecLicenseMainDataByOid(String oid) throws Exception;

    /**
     * 更新elecLicenseMainData对象
     * @author chenyq
     * @date 2020年05月19日
     * @param elecLicenseMainData
     */
    Map<String,Object> updElecLicenseMainDataByOid(ElecLicenseMainData elecLicenseMainData)throws Exception;
    /**
     * @desc: 根据oid和证件号码查询单条数据
     * @author: chenyq
     * @date: 2020/7/31
     * @param identificateNumber
     * @return
     * @throws Exception
     */
    int countMainByIdentificateNumber(String identificateNumber,String elecLicenOid,String directoryOid) throws Exception;
    /**
     * @desc: 根据oid和证件号码查询单条数据
     * @author: chenyq
     * @date: 2020/7/31
     * @param identificateNumber
     * @return
     * @throws Exception
     */
    int countMainByIdentificateNumber(String identificateNumber,String directoryOid) throws Exception;

}
