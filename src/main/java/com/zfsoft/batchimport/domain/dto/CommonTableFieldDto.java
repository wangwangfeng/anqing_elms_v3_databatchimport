package com.zfsoft.batchimport.domain.dto;

/**
 * @author: kkfan
 * @create: 2020-03-11 00:10:02
 * @description: 表用表字段dto
 */
public class CommonTableFieldDto {

    /** 字段名 */
    private String fieldName;
    /** 字段编码 */
    private String fieldCode;
    /** 字段类型 */
    private String fieldType;
    /** 字段长度 */
    private String fieldLength;
    /** 是否可为空 */
    private String isNull;
    /**
     * 是否替换：0：是，1：否
     */
    private String replaceFlag;
    /**
     *是否保密 0-保密 1-不保密
     */
    private String comfidentialFlag;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(String fieldLength) {
        this.fieldLength = fieldLength;
    }

    public String getIsNull() {
        return isNull;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }

    public String getReplaceFlag() {
        return replaceFlag;
    }

    public void setReplaceFlag(String replaceFlag) {
        this.replaceFlag = replaceFlag;
    }

    public String getComfidentialFlag() {
        return comfidentialFlag;
    }

    public void setComfidentialFlag(String comfidentialFlag) {
        this.comfidentialFlag = comfidentialFlag;
    }
}
