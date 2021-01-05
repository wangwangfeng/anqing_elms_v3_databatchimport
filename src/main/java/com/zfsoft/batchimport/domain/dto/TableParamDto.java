package com.zfsoft.batchimport.domain.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.zfsoft.batchimport.utils.enm.ParamType;

/**
 * @author wangsh
 * @description
 * @date 2019-12-11
 * @Copyright
 */
public class TableParamDto {
    /**
     * @COLUMN_EXPLAIN : 标签
     */
    @JSONField(ordinal=1,name = "name")
    private String name;
    /**
     * @COLUMN_EXPLAIN : inputname
     */
    @JSONField(ordinal=2,name = "key")
    private String key;
    /**
     * @COLUMN_EXPLAIN : 类型
     */
    @JSONField(ordinal=4,name = "type")
    private ParamType type;
    /**
     * @COLUMN_EXPLAIN : 值
     */
    @JSONField(ordinal=4,name = "value")
    private String value;

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }

    public TableParamDto(){

    }

    public TableParamDto(String key, String name, ParamType type, String value) {
        this.key = key;
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
    public ParamType getType() {
        return type;
    }

    public void setType(ParamType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
