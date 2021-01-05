package com.zfsoft.batchimport.base;

import java.util.Date;

/**
 * 异步处理异常详细信息
 *
 * @author zxx
 * @date 2020-03-01 11:43
 */
public class AsynHandleExceptionDetail {

    /**
     * 主键
     */
    private String _id;

    /**
     * 异常信息
     */
    private String exception_info;

    /**
     * 异常主键
     */
    private String exception_id;

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

    public String getException_info() {
        return exception_info;
    }

    public void setException_info(String exception_info) {
        this.exception_info = exception_info;
    }

    public String getException_id() {
        return exception_id;
    }

    public void setException_id(String exception_id) {
        this.exception_id = exception_id;
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
