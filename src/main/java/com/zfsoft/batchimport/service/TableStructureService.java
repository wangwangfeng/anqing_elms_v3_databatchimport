package com.zfsoft.batchimport.service;

import com.zfsoft.batchimport.domain.entity.TableStructure;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author chenyq
 * @Description: 表结构接口
 * @date 2020/6/23 17:13
 */
public interface TableStructureService {

    /**
     * 获取启动中的表结构 只获取一条
     * @author chenyq
     * @date 20200508
     * @return
     */
    TableStructure getTableStructureByOp1();

    /**
     * 获取启动中的表结构 只获取一条
     * @author chenyq
     * @date 20200704
     * @return
     */
    void updateTableStructure(TableStructure tableStructure);


}
