package com.zfsoft.batchimport.utils;


/**
 * @Author chenyj
 * @Description
 * @Date create by 2019/10/22 22:51
 * 陈银杰专属测试
 */
public class Result {

    /**
     *成功标识
     */
    private boolean flag;
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 反馈信息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Result(boolean flag, Object data) {
        this.flag = flag;
        this.data = data;
    }


    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result() {
    }

    public Result(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
