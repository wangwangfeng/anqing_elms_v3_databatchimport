package com.zfsoft.batchimport.service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 二维码配置信息表 service接口
 * 
 * @author : chenyq
 * @date : 2020-11-03
 */
public interface IQRcodeConfigService {

    /**
     * 获取当前机构下的所有该目录的二维码配置信息
     * @author chenyq
     * @date 20201103
     * @param licenseTypeOid 证照类型主键
     * @return
     */
    List getQRcodeConfigByOrganAndDirectoryOid(String licenseTypeOid, String configType)throws Exception;

    /**
     * 保存二维码信息
     * @author chenyq
     * @date 20201103
     * @param organOid 机构主键
     * @param directoryOid 目录主键
     * @return
     */
    String saveQRcodeConfig(String deleteQrcodeOids, String qrcodeOids, String licenseTypeOid, HttpServletRequest request)throws Exception;



}
