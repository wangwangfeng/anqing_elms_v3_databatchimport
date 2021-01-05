package com.zfsoft.batchimport.service;


import com.zfsoft.batchimport.domain.entity.AsynTask;

/**
 * @author: kkfan
 * @create: 2020-03-12 10:14:34
 * @description: 导入任务服务类
 */
public interface AsynTaskService {
    /**
     * 通过oid获取任务详情
     * @param taskOid
     * @return
     */
    AsynTask queryByOid(String taskOid);

    /**
     * 保存/更新
     * @param asynTask
     */
    void saveOrUpdate(AsynTask asynTask);
}
