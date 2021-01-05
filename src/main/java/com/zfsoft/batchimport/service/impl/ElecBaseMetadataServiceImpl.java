package com.zfsoft.batchimport.service.impl;


import com.zfsoft.batchimport.domain.entity.ElecBaseMetadata;
import com.zfsoft.batchimport.mapper.elms.ElecBaseMetadataMapper;
import com.zfsoft.batchimport.service.ElecBaseMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author chenyq
 * @description
 * @date 2020-04-26
 * @Copyright
 */
@Service
public class ElecBaseMetadataServiceImpl implements ElecBaseMetadataService {
    /**
     * 基础照面元素列表信息mapper
     */
    @Autowired
    private ElecBaseMetadataMapper elecBaseMetadataMapper;
    public List<ElecBaseMetadata> baseMetadataList(){
        List<ElecBaseMetadata> elecBaseMetadataList = elecBaseMetadataMapper.baseMetadataList();
        return elecBaseMetadataList;
    }
}