package com.zfsoft.batchimport.utils;

import com.zfsoft.batchimport.common.BaseStaticParameter;
import com.zfsoft.batchimport.utils.sys.FileInterfaceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenxb
 * @Description 调用统一文件接口上传附件工具类
 * @Date 2020-06-22
 */
public class UploadAttaUtil {
    public static final Logger logger = LoggerFactory.getLogger(UploadAttaUtil.class);
    public static String uploadAttaChment(String originName, String filePath, String fileName,String realPath,String preDownload) throws Exception {
        String savePath = "";
        if ("2".equals(BaseStaticParameter.UPLOAD_TYPE)) {
            savePath = BaseStaticParameter.UPLOAD_PATH + "/";
        } else if ("3".equals(BaseStaticParameter.UPLOAD_TYPE)) {
            savePath = BaseStaticParameter.UPLOAD_PATH;
        }
        String downloadFilePath = savePath + filePath + fileName;
        File file = new File(downloadFilePath);
        if (!file.exists()) {
            logger.info("文件路径【{}】",downloadFilePath);
            return "文件不存在";
        }
        InputStream inputStream = new FileInputStream(new File(downloadFilePath));
        Map<String, String> param = new HashMap<>();
        param.put("fileName", fileName);
        param.put("fileOriginalName", originName);
        // 文件保存路径logisticsData
        param.put("downDir", preDownload+realPath);
        System.out.println("param======="+param.toString());
        String result = FileInterfaceUtil.upLoadFile(param, inputStream);
        /** 错误信息 */
        String errorInfo = "";
        if (StringUtil.isNotEmpty(result)) {
            HashMap<String, Object> reslutMap = JsonUtil.toHashMap(result);
            if (reslutMap.get("success") != null) {
                if (!"true".equals(reslutMap.get("success").toString())) {
                    errorInfo = String.valueOf(reslutMap.get("msg"));
                }
            }
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return errorInfo;
    }
}
