package com.zfsoft.batchimport.mapper.elms;

import com.zfsoft.batchimport.domain.entity.QRcodeConfig;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface QRcodeConfigMapper extends Mapper<QRcodeConfig> {

    /**
     * 获取当前机构下的所有该目录的二维码配置信息
     * @author chenyq
     * @date 20201103
     * @param licenseTypeOid 证照类型主键
     * @return
     */
    List getQRcodeConfigByOrganAndDirectoryOid( @Param("licenseTypeOid")String licenseTypeOid, @Param("configType")String configType)throws Exception;



}