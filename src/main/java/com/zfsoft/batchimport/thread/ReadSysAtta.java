package com.zfsoft.batchimport.thread;

import com.zfsoft.batchimport.common.SpringBeanUtil;
import com.zfsoft.batchimport.domain.entity.TTSysAtta;
import com.zfsoft.batchimport.mapper.original.TTSysAttaMapper;
import com.zfsoft.batchimport.service.impl.AsynAttaFileServiceImpl;
import com.zfsoft.batchimport.utils.UploadAttaUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Stack;

public class ReadSysAtta implements Runnable {

    private static Stack<String> stack;

    public static final Logger logger = LoggerFactory.getLogger(SysAttaThread.class);

    private TTSysAttaMapper sysAttaMapper = SpringBeanUtil.getAppContext().getBean(TTSysAttaMapper.class);

    private int start;

    private int len;

    public ReadSysAtta(Stack<String> stack, int start, int len){
        this.start = start;
        this.len = len;
        this.stack = stack;
    }

    @Override
    public void run() {
        logger.info("生产者开始生产==================start==="+start+"======len======="+len);
        List<TTSysAtta> attaList = sysAttaMapper.selectALLAttaOid(start,len);
        if(attaList.size()==0){
            stack.push("end");
        }else {
            for (int i = 0; i < attaList.size(); i++) {
                TTSysAtta atta = attaList.get(i);
                stack.push(atta.getOid() + ";" + atta.getFilePath() + ";" + atta.getOriginName() + ";" + atta.getName());
            }
        }
        logger.info("生产者生产待处理数据，栈中剩余任务数=================="+stack.size());
        AsynAttaFileServiceImpl.readCounter.decrementAndGet();
    }

}
