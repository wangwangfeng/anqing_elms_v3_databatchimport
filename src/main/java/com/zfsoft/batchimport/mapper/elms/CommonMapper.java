package com.zfsoft.batchimport.mapper.elms;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
public interface CommonMapper {

    /**
     * 查询有效期
     * @author chenyq
     * @date 20200509
     * @param licenseTypeOid
     * @return
     */
    List<HashMap>  getElecLicenseDirTypeTimeList(@Param("licenseTypeOid") String licenseTypeOid);


    /**
     * 查询模板数据
     * @author chenyq
     * @date 20200509
     * @param directoryOid
     * @param organOid
     * @return
     */
    List<HashMap>  getTemplateList(@Param("directoryOid") String directoryOid, @Param("organOid") String organOid);


    /**
     * 获取文件上传的方式
     * @author chenyq
     * @date 20200519
     * @return
     */
    List<HashMap>  getSysConfigList();

    /**
     * 根据认领主键获取当前机构
     * @author chenyq
     * @date 20201105
     * @return
     */
    HashMap  getOrgan(String claimOid);

    /**
     * 根据目录主键获取证照类型主键
     * @author chenyq
     * @date 20201105
     * @return
     */
    HashMap  getLicenseTypeOidByDirMain(String directoryOid);
}