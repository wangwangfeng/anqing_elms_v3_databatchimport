package com.zfsoft.batchimport.base;

/**
 * @author: kkfan
 * @create: 2020-02-13 10:52:27
 * @description:    发送es服务字段详情
 */
public class FieldDetail {

    /** 字段名 */
    private String fieldName;

    /** 字段值 */
    private Object fieldValue;

    /** 字段类型 */
    private String fieldType;

    /** 分词器 */
    private String analyzer;

    /** 查询分词器 */
    private String search_analyzer;

    /** 是否开启fielddata */
    private Boolean fielddata;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
    }

    public String getSearch_analyzer() {
        return search_analyzer;
    }

    public void setSearch_analyzer(String search_analyzer) {
        this.search_analyzer = search_analyzer;
    }

    public Boolean getFielddata() {
        return fielddata;
    }

    public void setFielddata(Boolean fielddata) {
        this.fielddata = fielddata;
    }
}
