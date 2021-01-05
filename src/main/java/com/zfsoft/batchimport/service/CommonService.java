package com.zfsoft.batchimport.service;


import com.zfsoft.batchimport.domain.dto.CommonCreateTableDto;
import com.zfsoft.batchimport.domain.dto.CommonInsertDto;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * @author: kkfan
 * @create: 2020-03-10 13:27:04
 * @description: 数据导入服务层
 */
public interface CommonService {

    /**
     * 插入数据
     * @param list
     * @param commonCreateTableDto
     */
    Future<List<LinkedHashMap<Integer, String>>> saveList(List<CommonInsertDto> list, CommonCreateTableDto commonCreateTableDto, CountDownLatch countDownLatch);
    /**
     * 获取文件上传的方式
     * @author chenyq
     * @date 20200519
     * @return
     */
    List<HashMap>  getSysConfigList();
}
