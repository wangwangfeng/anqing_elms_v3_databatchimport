package com.zfsoft.batchimport.base;

/**
 * @author: kkfan
 * @create: 2020-01-15 23:01:39
 * @description:    查询字段实体
 */
public class QueryFieldDto {

    /** 查询字段名 */
    private String fieldName;

    /** 查询字段值 */
    private String fieldValue;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
