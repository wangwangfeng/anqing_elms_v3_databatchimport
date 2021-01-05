package com.zfsoft.batchimport.service.impl;

import com.zfsoft.batchimport.common.SpringBeanUtil;
import com.zfsoft.batchimport.domain.entity.TTSysAtta;
import com.zfsoft.batchimport.jdbc.SysAttaDao;
import com.zfsoft.batchimport.mapper.original.TTSysAttaMapper;
import com.zfsoft.batchimport.service.AsynAttaFileService;
import com.zfsoft.batchimport.thread.ReadSysAtta;
import com.zfsoft.batchimport.thread.SysAttaThread;
import com.zfsoft.batchimport.thread.WriteSysAtta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xuqiang
 * @date 2020/7/22 15:55
 * 将ftp中的附件同步到fastDFS
 */

@Service
public class AsynAttaFileServiceImpl implements AsynAttaFileService {

    private TTSysAttaMapper sysAttaMapper = SpringBeanUtil.getAppContext().getBean(TTSysAttaMapper.class);;

    //消费线程计数器
    public static AtomicInteger tashCounter = new AtomicInteger();

    //生产线程计数器
    public static AtomicInteger readCounter = new AtomicInteger();

    @Autowired
    private SysAttaDao sysAttaDao;


    @Value("${elms.prePath}")
    private String prePath;
    @Value("${elms.preDownload}")
    private String preDownload;

    private static Logger logger = LoggerFactory.getLogger(AsynAttaFileServiceImpl.class);

    private static int corePoolSize = Runtime.getRuntime().availableProcessors();

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(50, 50, 60l, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>());

    private static int start = 0;

    private static int len = 10000;

    private static int threadNum = 2;

    private static Stack<String> s = new Stack<>();

    @Override
    public void uploadAttaChment() throws InterruptedException {
//        splitList(start, len);
        //生产，从库中读取所有符合条件数据入栈

        List<TTSysAtta> attaList = sysAttaMapper.selectALLAttaOid(start,len);
        for(int i=0; i<attaList.size(); i++){
            TTSysAtta atta = attaList.get(i);
            s.push(atta.getOid()+";"+atta.getFilePath()+";"+atta.getOriginName()+";"+atta.getName());
        }

        //消费栈内数据
        while (!s.empty()) {
            while(readCounter.get()<=5 && !"end".equals(s.peek())){
                logger.info("开始生产任务========================");
                readCounter.incrementAndGet();
                executor.execute(new ReadSysAtta(s, start++,len));

            }
            logger.info("队列剩余数量============================"+s.size());
            while(tashCounter.get() <=10000){
                String sta = s.pop();
                tashCounter.incrementAndGet();
                executor.execute(new WriteSysAtta(sta, prePath,preDownload));

            }
        }
    }

    /**
     * 创建线程处理业务
     *
     * @param sysAttaList
     * @param start
     * @param len
     * @throws InterruptedException
     */
    private void createThread(List<TTSysAtta> sysAttaList, int start, int len) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(threadNum);
        long star = System.currentTimeMillis();
        List<List<TTSysAtta>> lists = averageAssign(sysAttaList, threadNum);
        long end = System.currentTimeMillis();
        System.out.println("数据分组用时=================="+(end-star));
        executor.allowCoreThreadTimeOut(true);
        for (int i = 0; i < threadNum; i++) {
            executor.execute(new SysAttaThread(lists.get(i), latch, prePath,preDownload,sysAttaDao));
        }
        latch.await();
        //单次任务结束继续查询
        logger.info("线程等待========");
        splitList(start, len);
    }

    public void splitList(int start, int len) throws InterruptedException {
        long star = System.currentTimeMillis();
        List<TTSysAtta> ttSysAttas = sysAttaMapper.selectByNum(start, len);
        long end = System.currentTimeMillis();
        System.out.println("附件表数据查询用时=================="+(end-star));
        if (ttSysAttas.isEmpty()) {
            return;
        }
        createThread(ttSysAttas, start, ttSysAttas.size());
    }


    /**
     * 数据分组
     *
     * @param source
     * @param n
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<>();
        int remainder = source.size() % n;  //(先计算出余数)
        int number = source.size() / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }




    public void test (){
        List<TTSysAtta> ttSysAttas = sysAttaMapper.selectByNum(start, len);
        long start = System.currentTimeMillis();
        sysAttaDao.batchSuccessUpdate(ttSysAttas);
        long total = System.currentTimeMillis() - start;
        System.out.println("耗时："+total);
    }
}
