package com.zfsoft.batchimport.service;


import java.util.HashMap;
import java.util.List;

/**
 * @author: kkfan
 * @create: 2020-03-10 13:27:04
 * @description: 数据导入服务层
 */
public interface ElmsService {
   /**
     * 获取文件上传的方式
     * @author chenyq
     * @date 20200519
     * @return
     */
    List<HashMap>  getSysConfigList();
}
