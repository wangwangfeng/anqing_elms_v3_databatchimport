package com.zfsoft.batchimport.kafka;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.cache.selector.SimpleReadCacheSelector;
import com.alibaba.fastjson.JSONObject;
import com.zfsoft.batchimport.base.SysCode;
import com.zfsoft.batchimport.domain.entity.AsynTask;
import com.zfsoft.batchimport.read.VehiclePermitDataListener;
import com.zfsoft.batchimport.service.CommonService;
import com.zfsoft.batchimport.service.impl.AsynTaskServiceImpl;
import com.zfsoft.batchimport.service.impl.ElecBaseMetadataServiceImpl;
import com.zfsoft.batchimport.service.impl.SysAttaServiceImpl;
import com.zfsoft.batchimport.domain.dto.CommonCreateTableDto;
import com.zfsoft.batchimport.domain.entity.SysAtta;
import com.zfsoft.batchimport.utils.AbstractFileTool;
import com.zfsoft.batchimport.utils.JsonUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Optional;

@Component
public class KafkaReceiver {

    private static Logger logger = LoggerFactory.getLogger(KafkaReceiver.class);

    @Autowired
    private CommonService commonService;

    @Autowired
    private AsynTaskServiceImpl asynTaskService;


    @Autowired
    private SysAttaServiceImpl sysAttaService;

    //照面元素service
    @Autowired
    private ElecBaseMetadataServiceImpl elecBaseMetadataService;

    @KafkaListener(topics = {"excle-data-batch-import-elms"})
    public void workflowRegisterExampleListen(ConsumerRecord<?, ?> record) {
        logger.info(MessageFormat.format("收到消息，详细详情为：{0}", record.toString()));
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            if(JsonUtils.isJson(message.toString())) {
                try {
                    JSONObject jsonObject = JSONObject.parseObject(message.toString());
                    String taskOid = jsonObject.getString("taskOid");
                    AsynTask asynTask = this.asynTaskService.queryByOid(taskOid);
                    Assert.notNull(asynTask, "任务为空！");
                    asynTask.setTaskStatus(SysCode.TASK_STATUS.TASK_STATUS_BEGIN);
                    this.asynTaskService.saveOrUpdate(asynTask);
                    String fileId = jsonObject.getString("fileId");
                    CommonCreateTableDto commonCreateTableDto = jsonObject.getObject("tableInfo", CommonCreateTableDto.class);
                    InputStream ins = null;
                    try {
                        //下载传递过来的attaOid
                        SysAtta atta = sysAttaService.viewSysAtta(fileId);
                        AbstractFileTool tool= new AbstractFileTool();
                        ins = tool.getFileInputStreamByAtta(atta);
                        if(ins == null) {
                            throw new IllegalArgumentException("上传文件为空！");
                        }
                    } catch (Exception e) {
                        asynTask.setTaskStatus(SysCode.TASK_STATUS.TASK_STATUS_FAIL);
                        this.asynTaskService.saveOrUpdate(asynTask);
                        logger.error("任务出错！", e);
                        return;
                    }
                    InputStream finalIns = ins;
                    new Thread(() -> {EasyExcel.read(finalIns, new VehiclePermitDataListener(elecBaseMetadataService, commonService, asynTaskService
                            ,sysAttaService,taskOid, commonCreateTableDto))
                            .readCacheSelector(new SimpleReadCacheSelector(1, 100)).ignoreEmptyRow(false).sheet().doRead();}).start();
                } catch (Exception e) {
                    logger.error(e.toString(), e);
                }
            } else {
                logger.error(MessageFormat.format("发生错误，收到消息为：", record.toString()));
            }
        }

    }

}

