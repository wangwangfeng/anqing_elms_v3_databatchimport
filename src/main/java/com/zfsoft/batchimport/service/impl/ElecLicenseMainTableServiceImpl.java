package com.zfsoft.batchimport.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zfsoft.batchimport.domain.dto.TableParamDto;
import com.zfsoft.batchimport.domain.entity.ElecLicenseMainTable;
import com.zfsoft.batchimport.mapper.elms.ElecLicenseMainTableMapper;
import com.zfsoft.batchimport.service.ElecLicenseMainTableService;
import com.zfsoft.batchimport.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenyq
 * @description
 * @date 2020-04-26
 * @Copyright
 */
@Service
public class ElecLicenseMainTableServiceImpl implements ElecLicenseMainTableService {
    @Autowired
    private ElecLicenseMainTableMapper elecLicenseMainTableMapper;
    @Override
    public Map<String, String> getMetadataMap(String tableOid) {
        Map<String, String> metadataMap = new HashMap<>();
        ElecLicenseMainTable table = null;//获取证照照面元素数据
        try {
            table = elecLicenseMainTableMapper.selectByPrimaryKey(tableOid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (table != null && StringUtil.isNotEmpty(table.getParamJson())) {
            JSONArray array = JSONArray.parseArray(table.getParamJson());
            for (Object obj : array.toArray()) {
                TableParamDto vo = JSON.toJavaObject((JSONObject) obj, TableParamDto.class);
                metadataMap.put(vo.getKey(), vo.getValue());
            }
        }
        return metadataMap;
    }



    @Override
    public String saveOrUpdateMetadataTable(String tableOid, List<TableParamDto> params) throws Exception {
        // 获取证照参数
        if (params == null || params.size() == 0) {
            return "照面元素数据保存失败！错误原因 无法获取照面元素数据";
        }

        String paramJson = JSON.toJSONString(params);
        String md5 = MD5Util.getMd5(paramJson);
        String key= null,val=null;
        ElecLicenseMainTable mainTable = elecLicenseMainTableMapper.selectByPrimaryKey(tableOid);
        if (mainTable == null) {
            mainTable = new ElecLicenseMainTable();
            mainTable.setOid(tableOid);
            mainTable.setParamJson(paramJson);
            mainTable.setParamJsonMD5(md5);
            mainTable.setCommonKey(key);
            mainTable.setCommonValue(val);
            elecLicenseMainTableMapper.insert(mainTable);
        } else {
            // md5 相同 不做操作
            if (!md5.equals(mainTable.getParamJsonMD5())) {
                mainTable.setParamJsonMD5(md5);
                mainTable.setLastVersionParam(mainTable.getParamJson());
                mainTable.setParamJson(paramJson);
                mainTable.setCommonKey(key);
                mainTable.setCommonValue(val);
                elecLicenseMainTableMapper.updateByPrimaryKey(mainTable);
            }
        }
        return null;
    }


}
