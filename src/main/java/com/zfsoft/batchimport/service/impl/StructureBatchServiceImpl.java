package com.zfsoft.batchimport.service.impl;

import com.zfsoft.batchimport.domain.entity.StructureBatch;
import com.zfsoft.batchimport.mapper.middle.StructureBatchMapper;
import com.zfsoft.batchimport.service.StructureBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chenyq
 * @Description:
 * @date 2020/7/4 16:27
 */
@Service
public class StructureBatchServiceImpl implements StructureBatchService {

    /**
     * 批次表mapper
     */
    @Autowired
    private StructureBatchMapper structureBatchMapper;

    @Override
    public List<StructureBatch> getBatchList(){
        return structureBatchMapper.getBatchList();
    }

    @Override
    public void updateTableBatch(StructureBatch structureBatch) {
        structureBatchMapper.updateByPrimaryKey(structureBatch);
    }

}
