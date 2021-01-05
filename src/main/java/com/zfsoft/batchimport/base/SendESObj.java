package com.zfsoft.batchimport.base;

import java.io.Serializable;
import java.util.List;

/**
 * @author: kkfan
 * @create: 2020-02-13 10:48:41
 * @description: 发送ES保存对象
 */
public class SendESObj implements Serializable {
    /** 索引名（只在第一次创建有效） */
    private String indexName;

    /** 索引类型（只在第一次创建有效） */
    private String type;

    /** 主分片数（只在第一次创建有效） */
    private Integer replicasNum;

    /** 从分片数（只在第一次创建有效） */
    private Integer shardsNum;

    /** 字段详情 */
    private List<FieldDetail> fieldDtoList;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getReplicasNum() {
        return replicasNum;
    }

    public void setReplicasNum(Integer replicasNum) {
        this.replicasNum = replicasNum;
    }

    public Integer getShardsNum() {
        return shardsNum;
    }

    public void setShardsNum(Integer shardsNum) {
        this.shardsNum = shardsNum;
    }

    public List<FieldDetail> getFieldDtoList() {
        return fieldDtoList;
    }

    public void setFieldDtoList(List<FieldDetail> fieldDtoList) {
        this.fieldDtoList = fieldDtoList;
    }
}
