package com.zfsoft.batchimport.common;

import com.github.pagehelper.util.StringUtil;
import com.suwell.ofd.custom.agent.HTTPAgent;
import com.zfsoft.batchimport.utils.PropertiesUtil;

import java.util.*;

/**
 * 静态参数
 * 
 * @author zxx
 * @date 2020-03-01 10:50
 */

public class BaseStaticParameter {
    /**
     * 二维码字段配置数组
     */
    public static String QRCode_Field = null;
    static {
        PropertiesUtil env = new PropertiesUtil("prop/common.properties");
        PropertiesUtil pu = new PropertiesUtil("prop/"+env.readProperty ("profiles.env")+"/suwell.properties");
        QRCode_Field = pu.readProperty("Elms.QRCodeURL");
    }

    /**
     * 默认区划、用户、应用的编号
     */
    public static final String DEFAULT_ADMIN_OID = "00000000000000000000000000000000";
    /**
     * 异常状态：待处理
     */
    public static final String EXCEPTION_STATUS_PENDING = "1";
    /**
     *异常状态：已解决
     */
    public static final String EXCEPTION_STATUS_SOLVED = "2";


    public static List<HashMap>  CONFIG_MAP = new LinkedList<>();

    /** 索引名列表 必须小写 */
    public interface INDEX_NAME {
        String WORKFLOW_REGISTER_EXAMPLE = "workflow_register_example";
        String WORKFLOW_REGISTER_EXAMPLE_SENDED = "workflow_register_example_sended";
    }

    /** 索引名列表 */
    public interface INDEX_TYPE {
        String WORKFLOW_REGISTER_EXAMPLE = "workflowRegisterExample";
        String WORKFLOW_REGISTER_EXAMPLE_SENDED = "workflowRegisterExampleSended";
    }

    /** 索引分片数量 */
    public interface INDEX_PIECE {
        Integer REPLICAS_NUM= 1;
        Integer SHARDS_NUM= 3;
    }

    /** es分词器 */
    public interface INDEX_ANALYZER {
        interface IK {
            String IK_MAX_WORD = "ik_max_word";
            String IK_SMART = "ik_smart";
        }
    }

    /** es字段类型 */
    public interface INDEX_FIELD_TYPE {
        String TEXT = "text";
        String KEYWORD = "keyword";
        String LONG = "long";
        String INTEGER = "integer";
        String SHORT = "short";
        String BYTE = "byte";
        String DOUBLE = "double";
        String FLOAT = "float";
        String DATE = "date";
        String BOOLEAN = "boolean";
        String BINARY = "binary";
    }

    /** 排序方式 */
    public interface SORT_TYPE {
        /** 倒叙 */
        String DESC = "desc";

        /** 正序 */
        String ASC = "asc";
    }
    /**
     * 持证者代码类型
     */
    static final String HOLDER_CODE_001 = "统一社会信用代码";
    static final String HOLDER_CODE_099 = "其他法人或其他组织有效证件代码";
    static final String HOLDER_CODE_111 = "公民身份号码";
    static final String HOLDER_CODE_114 = "中国人民解放军军官证编号";
    static final String HOLDER_CODE_115 = "中国人民武装警察部队警官证编号";
    static final String HOLDER_CODE_118 = "中国人民解放军士兵证编号";
    static final String HOLDER_CODE_119 = "中国人民武装警察部队士兵证编号";
    static final String HOLDER_CODE_120 = "中国人民解放军文职人员证编号";
    static final String HOLDER_CODE_122 = "中国人民武装警察部队文职人员证编号";
    static final String HOLDER_CODE_411 = "外交护照护照号";
    static final String HOLDER_CODE_412 = "公务护照护照号";
    static final String HOLDER_CODE_413 = "公务普通护照护照号";
    static final String HOLDER_CODE_414 = "普通护照护照号";
    static final String HOLDER_CODE_511 = "台湾居民来往大陆通行证号码";
    static final String HOLDER_CODE_516 = "港澳居民来往内地通行证号码";
    static final String HOLDER_CODE_999 = "其他自然人有效证件代码";



    public static final Map<String,String> HOLDER_CODE_MAP = new HashMap<>(16);
    static {
        HOLDER_CODE_MAP.put(HOLDER_CODE_001,"001");
        HOLDER_CODE_MAP.put(HOLDER_CODE_099,"099");
        HOLDER_CODE_MAP.put(HOLDER_CODE_111,"111");
        HOLDER_CODE_MAP.put(HOLDER_CODE_114,"114");
        HOLDER_CODE_MAP.put(HOLDER_CODE_115,"115");
        HOLDER_CODE_MAP.put(HOLDER_CODE_118,"118");
        HOLDER_CODE_MAP.put(HOLDER_CODE_119,"119");
        HOLDER_CODE_MAP.put(HOLDER_CODE_120,"120");
        HOLDER_CODE_MAP.put(HOLDER_CODE_122,"122");
        HOLDER_CODE_MAP.put(HOLDER_CODE_411,"411");
        HOLDER_CODE_MAP.put(HOLDER_CODE_412,"412");
        HOLDER_CODE_MAP.put(HOLDER_CODE_413,"413");
        HOLDER_CODE_MAP.put(HOLDER_CODE_414,"414");
        HOLDER_CODE_MAP.put(HOLDER_CODE_511,"511");
        HOLDER_CODE_MAP.put(HOLDER_CODE_516,"516");
        HOLDER_CODE_MAP.put(HOLDER_CODE_999,"999");
    }
    /**
     * 替换状态
     * 0需要替换
     * 1不需要替换
     */
    public static final String REPLACE_FLAG_Y = "1";
    public static final String REPLACE_FLAG_N = "0";

  /**
     * 年
     */
    public static final String ELECLICENCE_TYPE_TIME_1 = "1";
    /**
     * 月
     */
    public static final String ELECLICENCE_TYPE_TIME_2 = "2";
    /**
     * 日
     */
    public static final String ELECLICENCE_TYPE_TIME_3 = "3";
    /**
     * 长期
     */
    public static final String ELECLICENCE_TYPE_TIME_4 = "4";
    /**
     * 自定义
     */
    public static final String ELECLICENCE_TYPE_TIME_5 = "5";


    public static final String LONG_EFFECT_YES = "0";//长期有效
    public static final String LONG_EFFECT_NO = "1";//非长期有效
    public static final String AUTO_ANNUAL_YES = "0";//需要年检
    public static final String AUTO_ANNUAL_NO = "1";//不需要年检

    public static final String AUTO_CANCEL_YES = "0";//需要注销
    public static final String AUTO_CANCEL_NO = "1";//不需要注销


    /**
     * 证照类型：证照
     */
    public static final String ZZ = "0";

    public static final String NO = "N";//不删除 / 禁用
    public static final String YES = "Y";//删除 / 启用



    /**
     * 二维码配置类型
     */
    public static final  Map<String, String> CONFIG_TYPE_MAP = new LinkedHashMap<String, String>();


    /**
     * 二维码配置类型基本信息
     */
    public static final String CONFIG_TYPE_JBXX = "0";
    /**
     * 二维码配置类型照面元素
     */
    public static final String CONFIG_TYPE_ZMYS = "1";
    /**
     * 二维码配置类型定制化信息
     */
    public static final String CONFIG_TYPE_DZH = "2";


    /**
     * 表结构下载 未开始
     */
    public static final String OPERATERSTATUS_0  = "0";
    /**
     * 表结构下载 开始启动
     */
    public static final String OPERATERSTATUS_1  = "1";
    /**
     * 表结构下载 已完成
     */
    public static final String OPERATERSTATUS_2  = "2";

    /**
     * 表不存在
     */
    public static final String OPERATERSTATUS_3  = "3";
    /**
     * 启动中
     */
    public static final String OPERATERSTATUS_4  = "4";

    public static final Map<String, String> OPERATERSTATUS_MAP = new HashMap<String, String>(16);

    static{
        OPERATERSTATUS_MAP.put(OPERATERSTATUS_0,"未开始");
        OPERATERSTATUS_MAP.put(OPERATERSTATUS_1,"启动");
        OPERATERSTATUS_MAP.put(OPERATERSTATUS_2,"已完成");
        OPERATERSTATUS_MAP.put(OPERATERSTATUS_3,"表不存在");
        OPERATERSTATUS_MAP.put(OPERATERSTATUS_4,"启动中");


        CONFIG_TYPE_MAP.put(CONFIG_TYPE_JBXX, "基本信息");
        CONFIG_TYPE_MAP.put(CONFIG_TYPE_ZMYS, "照面元素信息");
        CONFIG_TYPE_MAP.put(CONFIG_TYPE_DZH, "定制化信息");
    }
    /**
     * 表内容 未操作状态
     */
    public static final String TABLEOPERATERSTATUS_0  = "0";
    /**
     * 表内容 操作成功入库
     */
    public static final String TABLEOPERATERSTATUS_1  = "1";
    /**
     * 表内容 操作失败
     */
    public static final String TABLEOPERATERSTATUS_2  = "2";

    /**
     *表内容  数据删除
     */
    public static final String TABLEOPERATERSTATUS_3  = "3";

    // 附件存储的路径，当为项目中时，为文件夹名称，当为磁盘中，需要写全路径，当为FTP中，填写FTP的文件路径
    public static String UPLOAD_PATH = "elms_file";
    // FTP存储，服务器地址
    public static String UPLOAD_FTP_IP = "";
    // FTP存储，服务器端口
    public static String UPLOAD_FTP_PORT= "" ;
    // FTP用户名
    public static String UPLOAD_FTP_USERNAME= "" ;
    // FTP密码
    public static String UPLOAD_FTP_PASSWORD= "" ;
    // 附件存储的路径，当为项目中时，为文件夹名称，当为磁盘中，需要写全路径，当为FTP中，填写FTP的文件路径
    public static String UPLOAD_TYPE = "4" ;

    /**
     * 二维码照面元素配置字段
     */
    public static String QRCode="ewm";


    /**
     * 是否保密1不保密0
     */
    public static String COMFIDENTIAL_FLAG_Y = "1";

    /**
     * 是否保密0保密
     */
    public static String COMFIDENTIAL_FLAG_N = "0";

    /**
     * 证照文档项目存储路径
     */
    public static final String ELEC_LICENSE_LICE_PATH = "/upload/lice/";


    /**
     * 系统临时文件存储路径
     */
    public static final String ELMS_TEMP = "D:\\elms_file\\temp";

    /**
     * 持证人类型-自然人
     */
    public static final String HOLDER_TYPE_PERSON = "0";
    /**
     * 持证人类型-法人
     */
    public static final String HOLDER_TYPE_LEGAR_PERSONAL = "1";
    /**
     * 持证人类型-混合
     */
    public static final String HOLDER_TYPE_HUN_HE = "2";
    /**
     * 持证人类型-其他
     */
    public static final String HOLDER_TYPE_QI_TA = "3";

    /**
     * 持证主体代码类型
     */
    // 法人-统一社会信用代码
    public static final String HOLDER_TYPE_FAREN_XYDM = "001";
    // 法人-其他法人或其他组织有效证件代码
    public static final String HOLDER_TYPE_FAREN_XYDM_OTHER = "099";
    // 自然人-公民身份号码
    public static final String HOLDER_TYPE_ZIRANREN_SFZ = "111";
    // 自然人-其他自然人有效证件代码
    public static final String HOLDER_TYPE_ZIRANREN_SFZ_OTHER = "999";

}
