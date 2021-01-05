package com.zfsoft.batchimport.thread;

import com.zfsoft.batchimport.common.SpringBeanUtil;
import com.zfsoft.batchimport.domain.entity.TTSysAtta;
import com.zfsoft.batchimport.mapper.original.TTSysAttaMapper;
import com.zfsoft.batchimport.service.impl.AsynAttaFileServiceImpl;
import com.zfsoft.batchimport.utils.UploadAttaUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List; 
import java.util.Stack;

public class WriteSysAtta implements Runnable {

    private String stack;

    public static final Logger logger = LoggerFactory.getLogger(SysAttaThread.class);

    private TTSysAttaMapper sysAttaMapper = SpringBeanUtil.getAppContext().getBean(TTSysAttaMapper.class);

    private String prePath;

    private String preDownload;

    public WriteSysAtta(String stack,String prePath,String preDownload){
        this.prePath = prePath;
        this.preDownload = preDownload;
        this.stack = stack;
    }

    @Override
    public void run() {
        logger.info("消费者开始消费========="+Thread.currentThread().getName());
        TTSysAtta atta = new TTSysAtta();
//        System.out.println(stack);
        String oid = stack.split(";")[0];
        String filePath = stack.split(";")[1];
        String fileOriginName = stack.split(";")[2];
        String fileName = stack.split(";")[2];
        logger.info("【SysAtta当前线程：】{}", Thread.currentThread().getName());
        logger.info("【name：】{}", fileName);
        logger.info("【originName：】{}", fileOriginName);
        atta.setOid(oid);
        atta.setFilePath(filePath);
        atta.setOriginName(fileOriginName);
        atta.setName(fileName);
        uploadAtta(atta);
        AsynAttaFileServiceImpl.tashCounter.decrementAndGet();

    }

    private void uploadAtta(TTSysAtta atta) {
        if (atta != null) {
            try {
                if (StringUtils.isEmpty(atta.getFilePath())) {
                    atta.setSynStatus("F");
                    long star = System.currentTimeMillis();
                    sysAttaMapper.updateByPrimaryKeySelective(atta);
                    long end = System.currentTimeMillis();
                    System.out.println("更新数据用时=================="+(end-star));
                    logger.error("文件路径为空");
                    return;
                }
                String filePath = "";
                String realPath = atta.getFilePath();

                if (realPath.startsWith("/")) {
                    realPath = realPath.replaceFirst("(?:/)+", "");
                    filePath = prePath + realPath;
                } else if (realPath.contains(":")) {
                    filePath = realPath;
                }else {
                    filePath = prePath + realPath;
                }
                //Boolean flag = FileInterfaceUtil.existFile(FileUtil.getFileName(filePath, atta.getName()));
                //Boolean flag = false;
                if (true) {
                    logger.info("filePath======="+filePath);
                    long star = System.currentTimeMillis();
                    String errorInfo = UploadAttaUtil.uploadAttaChment(atta.getOriginName(), filePath, atta.getName(),realPath,preDownload);
                    long end = System.currentTimeMillis();
                    System.out.println("文件上传用时=================="+(end-star));
                    if (StringUtils.isNotEmpty(errorInfo)) {
                        //N未同步，Y已同步，F同步失败
                        atta.setSynStatus("F");
                        long star1 = System.currentTimeMillis();
                        sysAttaMapper.updateByPrimaryKeySelective(atta);
                        long end1 = System.currentTimeMillis();
                        System.out.println("更新数据用时=================="+(end1-star1));
                        logger.error("【上传错误信息{}】", errorInfo);
                        return;

                    }
                }
                atta.setSynStatus("Y");
                long star = System.currentTimeMillis();
                sysAttaMapper.updateByPrimaryKeySelective(atta);
                long end = System.currentTimeMillis();
                System.out.println("更新数据用时=================="+(end-star));
                logger.info("【文件同步成功{}】",atta.getName());

            } catch (Exception e) {
                e.printStackTrace();
                logger.error("【上传错误信息{}】",e.getMessage());
                //同步失败，给个失败标志
                atta.setSynStatus("F");
                long star = System.currentTimeMillis();
                sysAttaMapper.updateByPrimaryKeySelective(atta);
                long end = System.currentTimeMillis();
                System.out.println("更新数据用时=================="+(end-star));
            }
        }
    }
}
