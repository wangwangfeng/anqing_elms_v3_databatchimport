package com.zfsoft.batchimport.thread;

import com.zfsoft.batchimport.common.SpringBeanUtil;
import com.zfsoft.batchimport.domain.entity.TTSysAtta;
import com.zfsoft.batchimport.jdbc.SysAttaDao;
import com.zfsoft.batchimport.mapper.original.TTSysAttaMapper;
import com.zfsoft.batchimport.utils.FileUtil;
import com.zfsoft.batchimport.utils.UploadAttaUtil;
import com.zfsoft.batchimport.utils.sys.FileInterfaceUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author chenxb
 * @Description 多线程上传附件
 * @Date 2020-06-24
 */
public class SysAttaThread implements Runnable {

    public static final Logger logger = LoggerFactory.getLogger(SysAttaThread.class);

    private TTSysAttaMapper sysAttaMapper = SpringBeanUtil.getAppContext().getBean(TTSysAttaMapper.class);


    private List<TTSysAtta> sysAtta = new ArrayList<>();

    private CountDownLatch latch;

    private String prePath;

    private String preDownload;

    private SysAttaDao sysAttaDao;

    public SysAttaThread(List<TTSysAtta> sysAtta, CountDownLatch latch, String prePath, String preDownload, SysAttaDao sysAttaDao) {
        this.sysAtta = sysAtta;
        this.latch = latch;
        this.prePath = prePath;
        this.preDownload = preDownload;
        this.sysAttaDao = sysAttaDao;
    }

    @Override
    public void run() {
        List<TTSysAtta> successAtta = new ArrayList<>();
        List<TTSysAtta> failAtta = new ArrayList<>();
        System.out.println("【SysAtta当前线程：】{}"+Thread.currentThread().getName());
        System.out.println("【SysAtta当前线程开始时间：】{}"+ System.currentTimeMillis());
        for (TTSysAtta atta : sysAtta) {
            logger.info("【name：】{}", atta.getName());
            logger.info("【originName：】{}", atta.getOriginName());
            try {
                if (StringUtils.isEmpty(atta.getFilePath())) {
                    atta.setSynStatus("F");
                    long star = System.currentTimeMillis();
                    sysAttaMapper.updateByPrimaryKeySelective(atta);
                    failAtta.add(atta);
                    long end = System.currentTimeMillis();
                    System.out.println("更新数据用时=================="+(end-star));
                    logger.error("文件路径为空");
                    System.out.println("错误error：文件路径为空");
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
                    System.out.println("filePath======="+filePath);
                    System.out.println("filePath======="+filePath);
                    long star = System.currentTimeMillis();
                    String errorInfo = UploadAttaUtil.uploadAttaChment(atta.getOriginName(), filePath, atta.getName(),realPath,preDownload);
                    long end = System.currentTimeMillis();
                    System.out.println("文件上传用时=================="+(end-star));
                    if (StringUtils.isNotEmpty(errorInfo)) {
                        //N未同步，Y已同步，F同步失败
                        atta.setSynStatus("F");
                        long star1 = System.currentTimeMillis();
                        sysAttaMapper.updateByPrimaryKeySelective(atta);
                        failAtta.add(atta);
                        long end1 = System.currentTimeMillis();
                        System.out.println("更新数据用时=================="+(end1-star1));
                        logger.error("【上传错误信息{}】", errorInfo);
                        System.out.println("错误error：上传错误信息"+errorInfo);
                        return;
                    }
                }
                atta.setSynStatus("Y");
                long star = System.currentTimeMillis();
                sysAttaMapper.updateByPrimaryKeySelective(atta);
                successAtta.add(atta);
                long end = System.currentTimeMillis();
                System.out.println("更新数据用时=================="+(end-star));
                logger.info("【文件同步成功{}】",atta.getName());
                System.out.println("文件同步成功"+atta.getName());

            } catch (Exception e) {
                e.printStackTrace();
                logger.error("【上传错误信息{}】",e.getMessage());
                System.out.println("错误error:"+e.getMessage());
                //同步失败，给个失败标志
                atta.setSynStatus("F");
                long star = System.currentTimeMillis();
                sysAttaMapper.updateByPrimaryKeySelective(atta);
                failAtta.add(atta);
                long end = System.currentTimeMillis();
                System.out.println("更新数据用时=================="+(end-star));
            }
        }
        sysAttaDao.batchSuccessUpdate(successAtta);
        sysAttaDao.batchFailUpdate(failAtta);
        System.out.println("【SysAtta当前线程：】{}"+Thread.currentThread().getName());
        System.out.println("【SysAtta当前线程结束时间：】{}"+ System.currentTimeMillis());
        latch.countDown();//单次任务结束，计数器减一

    }
}
