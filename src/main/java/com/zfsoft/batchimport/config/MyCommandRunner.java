package com.zfsoft.batchimport.config;

import com.zfsoft.batchimport.common.BaseStaticParameter;
import com.zfsoft.batchimport.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther: kkfan
 * @Date: 2019/11/18 10:27
 * @Description: 启动后执行任务
 */
@Component
public class MyCommandRunner implements CommandLineRunner, InitializingBean {
    private static Logger logger = LoggerFactory.getLogger(MyCommandRunner.class);

    @Value("${server.port}")
    private String port;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private String ip;

    @Autowired
    private CommonService commonService;

    @Override
    public void run(String... args) {
        //将文件上传方式初始化到数据库中
        BaseStaticParameter.CONFIG_MAP = commonService.getSysConfigList();

        for (int i=0;i<BaseStaticParameter.CONFIG_MAP.size();i++){
            HashMap map = BaseStaticParameter.CONFIG_MAP.get(i);
            if(map.get("CODE").equals("ATTA_UPLOAD_PATH")){
                BaseStaticParameter.UPLOAD_PATH = map.get("VALUE").toString();
                continue;
            }else if(map.get("CODE").equals("ATTA_UPLOAD_FTP_IP")){
                BaseStaticParameter.UPLOAD_FTP_IP = map.get("VALUE").toString();
                continue;
            }else if(map.get("CODE").equals("ATTA_UPLOAD_FTP_PORT")){
                BaseStaticParameter.UPLOAD_FTP_PORT = map.get("VALUE").toString();
                continue;
            }else if(map.get("CODE").equals("ATTA_UPLOAD_FTP_USERNAME")){
                BaseStaticParameter.UPLOAD_FTP_USERNAME = map.get("VALUE").toString();
                continue;
            }else if(map.get("CODE").equals("ATTA_UPLOAD_FTP_PASSWORD")){
                BaseStaticParameter.UPLOAD_FTP_PASSWORD = map.get("VALUE").toString();
                continue;
            }else if(map.get("CODE").equals("ATTA_UPLOAD_TYPE")){
                BaseStaticParameter.UPLOAD_TYPE = map.get("VALUE").toString();
                continue;
            }
        }
        System.setProperty("local-ip", ip);
        StringBuffer sb = new StringBuffer(
                    "\r\n==========================================================" +
                    "\r\n= 首页 ==============================================" +
                    "\r\n=== http://{0}:{1}{2}/ ===========" +
                    "\r\n==========================================================" +
                    "\r\n======================= 启动完毕 =========================="
                    );
        logger.info(MessageFormat.format(sb.toString(), ip, port, contextPath));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ip = InetAddress.getLocalHost().getHostAddress();
    }
}