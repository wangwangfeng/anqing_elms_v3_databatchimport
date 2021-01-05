package com.zfsoft.batchimport.base;

import java.util.Date;

/**
 * 异步处理异常
 * 
 * @author zxx
 * @date 2020-03-01 11:05
 */
public class AsynHandleException {
    /**
     * 主键
     */
    private String _id;

    /**
     * 异常次数
     */
    private int exception_count;

    /**
     * 表名称
     */
    private String table_name;

    /**
     * 异常状态
     */
    private String exception_status;
    /**
     * 数据信息（json格式）
     */
    private String data_json;
    /**
     * 异常时间
     */
    private long exception_date;

    /**
     * 创建时间
     */
    private Date createDate;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getException_count() {
        return exception_count;
    }

    public void setException_count(int exception_count) {
        this.exception_count = exception_count;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getException_status() {
        return exception_status;
    }

    public void setException_status(String exception_status) {
        this.exception_status = exception_status;
    }

    public String getData_json() {
        return data_json;
    }

    public void setData_json(String data_json) {
        this.data_json = data_json;
    }

    public long getException_date() {
        return exception_date;
    }

    public void setException_date(long exception_date) {
        this.exception_date = exception_date;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
