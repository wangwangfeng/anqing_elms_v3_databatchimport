package com.zfsoft.batchimport.service.impl;

import com.zfsoft.batchimport.mapper.elms.TableStructureMapper;
import com.zfsoft.batchimport.domain.entity.TableStructure;
import com.zfsoft.batchimport.service.TableStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chenyq
 * @Description: 表结构接口实现
 * @date 2020/6/23 17:13
 */
@Service
public class TableStructureServiceImpl implements TableStructureService {

    @Autowired
    private TableStructureMapper tableStructureMapper;


    /**
     * 获取启动中的表结构 只获取一条
     * @author chenyq
     * @date 20200508
     * @return
     */
    public TableStructure getTableStructureByOp1(){
       return tableStructureMapper.getTableStructureByOp1();
    }

    public void updateTableStructure(TableStructure tableStructure){
        tableStructureMapper.updateByPrimaryKey(tableStructure);
    }


}
