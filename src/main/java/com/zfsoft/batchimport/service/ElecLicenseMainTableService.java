package com.zfsoft.batchimport.service;

import com.zfsoft.batchimport.domain.dto.TableParamDto;

import java.util.List;
import java.util.Map;

/**
 * @author chenyq
 * @description 照面元素内容表
 * @date 2020-04-26
 * @Copyright
 */
public interface ElecLicenseMainTableService{

    /**
     * 根据照面元素主键获取map
     * @param tableOid
     * @return
     */
    Map<String, String> getMetadataMap(String tableOid);

    /**
     * 保存table数据
     * @author chenyq
     * @date 20200426
     * @param tableOid
     * @param params
     * @return
     * @throws Exception
     */
    String saveOrUpdateMetadataTable(String tableOid, List<TableParamDto> params) throws Exception ;
}
