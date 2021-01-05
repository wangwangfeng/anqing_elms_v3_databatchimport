package com.zfsoft.batchimport.service;

import com.zfsoft.batchimport.domain.dto.ElecLicenseMainDataSubed;

import java.util.List;

public interface IZzWorkService {
    
    /**
     * 单独上报证照索引
     * 
     */
    public String suoYinShangBao() throws Exception;


    public  List queryHistoryMainDataSubed() throws Exception;

    public void doHistoryElms (List<ElecLicenseMainDataSubed> list) throws Exception;

} 
