package com.zfsoft.batchimport.service.impl;


import com.zfsoft.batchimport.service.SysAttaService;
import com.zfsoft.batchimport.domain.entity.SysAtta;
import com.zfsoft.batchimport.mapper.elms.SysAttaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysAttaServiceImpl implements SysAttaService {

    /**
     * 附件mapper
     */
    @Autowired
    private SysAttaMapper sysAttaMapper;

    @Override
    public SysAtta viewSysAtta(String attaOid){
     return   sysAttaMapper.selectByPrimaryKey(attaOid);
    }

    @Override
    public void saveOrUpdateSysAtta(SysAtta sysAtta){
        sysAttaMapper.insert(sysAtta);
    }
}