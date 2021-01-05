package com.zfsoft.batchimport.domain.dto;


import java.util.LinkedHashMap;

/**
 * @author: kkfan
 * @create: 2020-02-26 13:53:43
 * @description: 通用插入对象
 */
public class CommonInsertDto implements Cloneable {

    /** 表名 */
   // private String tableName;

    /** 表字段map */
    private LinkedHashMap<String, Object> fieldMap;

    /** 记录在excel中的行号 */
    private String rowNum;

   /* public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
*/
    public LinkedHashMap<String, Object> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(LinkedHashMap<String, Object> fieldMap) {
        this.fieldMap = fieldMap;
    }

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    @Override
    public CommonInsertDto clone() throws CloneNotSupportedException {
        return (CommonInsertDto)super.clone();
    }
}
