package com.zfsoft.batchimport.kafka;

/**
 * @author chenjian
 * 表单流程的状态
 */
public interface FromStateConstans {

    /**
     * 未处理
     */
    String TO_BE_PROCESSED = "TO_BE_PROCESSED";

    /**
     * 已处理但发生异常
     */
    String PROCESSED_ERROR = "PROCESSED_ERROR";

    /**
     * 已处理并且成功处理
     */
    String PROCESSED_SUCCESS = "PROCESSED_SUCCESS";

    /**
     *
     */
    String FROM_TO_BE_PROCESSED_DATA_STATE = "正在处理";
}
