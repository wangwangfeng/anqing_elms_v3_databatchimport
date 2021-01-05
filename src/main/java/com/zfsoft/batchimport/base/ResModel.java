package com.zfsoft.batchimport.base;

import java.io.Serializable;

public class ResModel implements Serializable {
    private static final long serialVersionUID = -2399353048688907090L;

    /**
     * 成功/失败 状态
     */
    private boolean flag;

    /**
     * 返回的数据
     */
    private Object data;

	/**
     * 错误码
     */
    private String state;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 默认构造函数
     */
    public ResModel() {
    }

    public ResModel(boolean flag, Object data, String errCode, String errMsg) {
        this.flag = flag;
        this.data = data;
        this.state = errCode;
        this.msg = errMsg;
    }

    /**
     * 成功响应结果
     *
     * @return
     */
    public static ResModel success() {
        return new ResModel(true, null, "1", "success");
    }

    /**
     * 请求成功时返回的对象
     */
    public static ResModel success(Object data) {
        return new ResModel(true, data, "1", "success");
    }
    /**
     * 请求失败异常，带占位符的消息
     *
     * @param message 消息类型
     *      占位数据变量
     * @return
     */
    public static ResModel fail(String message) {
        return new ResModel(false, null, "0", message);
    }
    /**
     * 请求失败时返回的对象
     */
    public static ResModel fail(String errCode, String errMsg, String errType) {
        ResModel result = new ResModel();
        result.setFlag(false);
        result.setState(errCode);
        result.setMsg(errMsg);
        return result;
    }
    
    /**
     * 请求失败时返回的对象
     */
    public static ResModel fail(String errCode, String errMsg) {
        ResModel result = new ResModel();
        result.setFlag(false);
        result.setState(errCode);
        result.setMsg(errMsg);
        return result;
    }
    
    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getState() {
        return state;
    }

    public ResModel setState(String state) {
        this.state = state == null ? null : state.trim();
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResModel setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
        return this;
    }

    public Object getData() {
        return data;
    }

}