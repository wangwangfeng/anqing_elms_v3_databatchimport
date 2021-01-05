package com.zfsoft.batchimport.utils;

import com.alibaba.fastjson.JSON;
import com.zfsoft.batchimport.common.BaseStaticParameter;
import com.zfsoft.batchimport.common.SpringBeanUtil;
import com.zfsoft.batchimport.domain.entity.SysAtta;
import com.zfsoft.batchimport.utils.impl.DownLoadUtil;
import com.zfsoft.batchimport.utils.impl.FastDFSUtil;
import com.zfsoft.batchimport.utils.sys.FileInterfaceUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangsh
 * @description
 * @date 2019-9-26
 * @Copyright
 */
@Component
@DependsOn("springBeanUtil")
public class AbstractFileTool {

    protected static Log LOG = LogFactory.getLog(AbstractFileTool.class);

    /**
     * @description 获取下载目录 , uploadtype为2 下载到 项目磁盘中 , 其余下载到项目内
     * @author wangsh
     * @date
     * @param relativePath 上传相对路径
     * @return
     */
    public String getDownloadRootPath(String relativePath){
        String classPath = AbstractFileTool.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            classPath = URLDecoder.decode(classPath, "utf-8").split("WEB-INF")[0];
            LOG.debug(" 文件上传 获取文件路径 classPath 地址为 : " + classPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(StringUtil.isNotEmpty(relativePath)){
            if(relativePath.endsWith("/")) relativePath += "/";
        }else{
            relativePath ="";
        }
        return classPath + relativePath;
    }

    /**
     *
     * @param realtiveSavePath
     * @return
     */
    public InputStream getFileInputStreamByRelativePathAndFileName(String realtiveSavePath) {
        if (StringUtil.isEmpty(realtiveSavePath)) {
            throw new NullPointerException("文件路径为空");
        }
        try {
            return FastDFSUtil.downloadFileForFastDFS(realtiveSavePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param inputStream
     * @param fileName
     * @return
     */
    public String upLoad(InputStream inputStream,String fileName) {
        String result = "";
        try {
            result = FastDFSUtil.uploadFile( inputStream,fileName);
        } catch (Exception e) {
            LOG.error("文件系统_文件上传失败");
        }
        return result;
    }


    /**
     *
     * @param atta
     * @return
     */
    public InputStream getFileInputStreamByAtta(SysAtta atta) {
        if (atta == null) return null;
        try {
            if (atta == null) return null;
            String fastdsfPath = atta.getFastdfsPath();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~downLoadFile1----"+fastdsfPath);
            InputStream inputStream = FastDFSUtil.downloadFileForFastDFS(fastdsfPath);
            return inputStream;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
