package com.zfsoft.batchimport.service;

import com.zfsoft.batchimport.domain.entity.StructureBatch;
import com.zfsoft.batchimport.domain.entity.TableStructure;

import java.util.List;
public interface StructureBatchService {
    /**
     * 获取10条批次号
     * @author chenyq
     * @date 20200704
     * @return
     */
    List<StructureBatch> getBatchList();



    /**
     * 更新批次号表状态和原因
     * @author: chenyq
     * @create: 2020-07-06
     * @param structureBatch
     * @return
     */
    void updateTableBatch(StructureBatch structureBatch);


}
