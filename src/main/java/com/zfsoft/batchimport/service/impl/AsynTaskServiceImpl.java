package com.zfsoft.batchimport.service.impl;

import com.zfsoft.batchimport.domain.entity.AsynTask;
import com.zfsoft.batchimport.mapper.elms.AsynTaskMapper;
import com.zfsoft.batchimport.service.AsynTaskService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: kkfan
 * @create: 2020-03-12 10:14:56
 * @description: 导入任务 service层
 */
@Service
public class AsynTaskServiceImpl implements AsynTaskService {

    @Autowired
    private AsynTaskMapper asynTaskMapper;

    @Override
    public void saveOrUpdate(AsynTask asynTask) {
        if(StringUtils.isNotBlank(asynTask.getOid())){
            this.asynTaskMapper.updateByPrimaryKeySelective(asynTask);
        } else {
            this.asynTaskMapper.insertSelective(asynTask);
        }

    }

    @Override
    public AsynTask queryByOid(String taskOid) {
        return this.asynTaskMapper.selectByPrimaryKey(taskOid);
    }
}
