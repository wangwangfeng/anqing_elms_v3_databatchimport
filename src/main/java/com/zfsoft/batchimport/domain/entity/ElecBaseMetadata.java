package com.zfsoft.batchimport.domain.entity;

import javax.persistence.*;

/**
 * @author: chenyq
 * @create: 2020-04-23
 * @description: 基础照面元素信息
 */

@Table(name = "T_ELEC_BASE_METADATA")
public class ElecBaseMetadata {
    /**
     * 主键
     */
    @Id
    @Column(name = "OID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String oid;
    /** 字段名 */
    @Column(name = "field_Name")
    private String fieldName;
    /** 字段编码 */
    @Column(name = "field_Code")
    private String fieldCode;
    /** 字段类型 */
    @Column(name = "field_Type")
    private String fieldType;
    /** 字段长度 */
    @Column(name = "field_Length")
    private String fieldLength;
    /** 是否可为空 0不可为空，1可为空； */
    @Column(name = "is_Null")
    private String isNull;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

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


}
