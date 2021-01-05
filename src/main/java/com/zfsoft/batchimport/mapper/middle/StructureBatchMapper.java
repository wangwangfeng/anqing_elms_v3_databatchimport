package com.zfsoft.batchimport.mapper.middle;

import com.zfsoft.batchimport.domain.entity.StructureBatch;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StructureBatchMapper extends Mapper<StructureBatch> {
    /**
     * 获取10条批次号
     * @author chenyq
     * @date 20200704
     * @return
     */
    List<StructureBatch> getBatchList();



}