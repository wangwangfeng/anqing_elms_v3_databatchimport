package com.zfsoft.batchimport.mapper.elms;

import com.zfsoft.batchimport.domain.entity.TableStructure;
import tk.mybatis.mapper.common.Mapper;
public interface TableStructureMapper extends Mapper<TableStructure> {
    /**
     * 获取启动中的表结构 只获取一条
     * @author chenyq
     * @date 20200508
     * @return
     */
    TableStructure getTableStructureByOp1();

}