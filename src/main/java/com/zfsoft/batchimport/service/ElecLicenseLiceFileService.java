package com.zfsoft.batchimport.service;

import com.zfsoft.batchimport.domain.dto.CommonCreateTableDto;
import com.zfsoft.batchimport.domain.entity.ElecLicenseMain;

/**
 * @author: chenyq
 * @create: 2020-04-26
 * @description: 证照文件表Service
 */
public interface ElecLicenseLiceFileService {
    /**
     * 创建ofd文件以及文件数据存储入库
     * @author chenyq
     * @date 20200427
     * @param elecLicenseMain
     * @param commonCreateTableDto
     * @param tableOid
     * @return
     * @throws Exception
     */
    String createElecLicenseMainFileLice(ElecLicenseMain elecLicenseMain, CommonCreateTableDto commonCreateTableDto, String tableOid)throws Exception;


    }
