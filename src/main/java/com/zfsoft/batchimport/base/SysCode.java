package com.zfsoft.batchimport.base;

/**
 * @author: kkfan
 * @create: 2019-12-30 11:15:02
 * @description: 系统静态参数
 */
public class SysCode {
    /** 索引前缀 */
    public static String LOGSTASH_PREFIX = "logstash.es.";

    /** 索引type */
    public static String ES_TYPE = "fluentd";

    /** 时间统计字段 */
    public static String TIMESTAMP = "timestamp";

    /** 默认分组字段 - 方法名 */
    public static String ANALYZE_FILED = "contextMap.methodName";

    // session保存登录时RSA加密私钥的key值
    public static final String LOGIN_PRIVATE_KEY = "LOGIN_PRIVATE_KEY";

    /** 监视状态 - 1 监控  0 不监控 */
    public interface MONITOR_STUT {
        String ALL = "2";
        String YES = "1";
        String NO = "0";
    }

    /** 发送邮件状态 - 1 发送  0 不发送 */
    public interface SEND_MAIL_STUT {
        String YES = "1";
        String NO = "0";
    }

    /** 逻辑删除状态 - 1 删除  0 未删除 */
    public interface DELETE_STUT {
        String YES = "1";
        String NO = "0";
    }

    /** 定时任务状态 - 0 暂停  1 正常 */
    public interface DISABLED {
        String YES = "1";
        String NO = "0";
    }

    public interface DATE_TYPE {
        String HOUR = "1";
        String DAY = "2";
        String WEEK = "3";
    }

    /**
     * 删除/启用状态
      */
    public interface DELETE_STATUS {
        String YES = "Y";
        String NO = "N";
    }

    /**
     * 任务执行状态
     */
    public interface TASK_STATUS {
        String TASK_STATUS_INIT = "0";
        String TASK_IN_QUEUE = "1";
        String TASK_STATUS_BEGIN = "2";
        String TASK_STATUS_END = "3";
        String TASK_STATUS_FAIL = "4";
    }
}
