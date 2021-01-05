package com.zfsoft.batchimport.service;

import com.zfsoft.batchimport.domain.entity.SysAtta;

/**
 * @author: chenyq
 * @create: 2020-04-26
 * @description: 证照附件Service
 */

public interface SysAttaService {
    /**
     * 查看附件信息
     * @author: chenyq
     * @create: 2020-05-19
     * @param attaOid
     * @return
     */
    SysAtta viewSysAtta(String attaOid);

    /**
     * 保存文件
     * @author: chenyq
     * @create: 2020-05-19
     * @param sysAtta
     * @return
     */
    void saveOrUpdateSysAtta(SysAtta sysAtta);
}

