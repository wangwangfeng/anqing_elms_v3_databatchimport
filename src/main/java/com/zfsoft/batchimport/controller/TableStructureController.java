package com.zfsoft.batchimport.controller;

import com.zfsoft.batchimport.common.BaseStaticParameter;
import com.zfsoft.batchimport.domain.entity.StructureBatch;
import com.zfsoft.batchimport.domain.entity.TableStructure;
import com.zfsoft.batchimport.service.MiddleService;
import com.zfsoft.batchimport.service.StructureBatchService;
import com.zfsoft.batchimport.service.TableStructureService;
import com.zfsoft.batchimport.service.impl.TableStructureServiceImpl;
import com.zfsoft.batchimport.thread.TableStructureThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author chenyq
 * @Description: 表结构
 * @date 2020/5/8 16:12
 */

@Component
@Controller
@RequestMapping("/tableStructureController")
public class TableStructureController {

    @Autowired
    private TableStructureThread tableStructureThread;
    @Autowired
    private MiddleService middleService;
    @Autowired
    private TableStructureService tableStructureService;
    @Autowired
    private TableStructureServiceImpl tableStructureServiceImpl;
    @Autowired
    private StructureBatchService structureBatchService;
    @RequestMapping("/startTableStructure")
    public void tableStructureThread() throws ExecutionException, InterruptedException {
        //读取数据库中已开启的任务，一个线程中读取1个，并对当前任务进行状态修改
        TableStructure tableStructure = tableStructureServiceImpl.getTableStructureByOp1();
        if(null != tableStructure) {
            //1、执行小程序中的方法，解析数据的批次号

            HashMap bindinfo = new HashMap();
            bindinfo.put("tableName",  tableStructure.getTableName());
            middleService.doneCallProduce(bindinfo);
            //获取表结构名称，判断表名称是否存在
            if (!middleService.existTable(tableStructure.getTableName())) {
                //1、不存在将记录的状态修改为不存在，各个数据量记录修改为0
                tableStructure.setOperaterStatus(BaseStaticParameter.OPERATERSTATUS_3);
                tableStructure.setBeginTime(new Date());
                tableStructure.setEndTime(new Date());
                tableStructure.setTotalNumber(0);
                tableStructure.setSuccessNumber(0);
                tableStructure.setFailNumber(0);
                tableStructureService.updateTableStructure(tableStructure);//修改状态为表不存在
                return ;
            } else {
                //2、存在查询数据，根据无实体查询返回java.util.HashMap
                Integer total = middleService.existData(tableStructure.getTableName(), null);
                if (total==0) {
                    tableStructure.setBeginTime(new Date());
                    tableStructure.setEndTime(new Date());
                    tableStructure.setTotalNumber(0);
                    tableStructure.setSuccessNumber(0);
                    tableStructure.setFailNumber(0);
                    tableStructure.setOperaterStatus(BaseStaticParameter.OPERATERSTATUS_2);
                    tableStructureService.updateTableStructure(tableStructure);//修改状态为已完成
                    return ;
                } else {//先将当前表修改为启动中
                    tableStructure.setBeginTime(new Date());
                    tableStructure.setTotalNumber(total);//当前总数据
                    tableStructure.setOperaterStatus(BaseStaticParameter.OPERATERSTATUS_4);
                    tableStructureService.updateTableStructure(tableStructure);//修改状态为启动中
                }
                //查询批次号表 List
                List<StructureBatch> structureBatchList = structureBatchService.getBatchList();
                Integer  errorCountTotal = 0;//总失败数
                for(StructureBatch structureBatch : structureBatchList){
                    //查询当前批次号的数据
                    List<HashMap<String, Object>> mapList = middleService.selectTableByBatchNo(tableStructure.getTableName(),structureBatch.getBatchNo());
                    Future<Integer>  errorFuture = tableStructureThread.doSaveElms(mapList,tableStructure);
                    Integer errorCount = errorFuture.get();
                    if(errorCount>0){
                        structureBatch.setBatchMessage("处理完成，当前批次号失败数量为："+errorCount+"条，详细信息请查看"+tableStructure.getTableName()+"表。");
                    }else{
                        structureBatch.setBatchMessage("处理完成，全部处理成功。");
                    }
                    structureBatch.setBatchStatus("1");
                    structureBatchService.updateTableBatch(structureBatch);
                    errorCountTotal = errorCount + errorCountTotal;
                }
                if(structureBatchList.size()<10){ //将当前表状态修改为已完成
                    tableStructure.setFailNumber(middleService.getStatusNumber(tableStructure.getTableName(),"2"));
                    tableStructure.setSuccessNumber(middleService.getStatusNumber(tableStructure.getTableName(),"1"));
                    tableStructure.setEndTime(new Date());
                    tableStructure.setOperaterStatus(BaseStaticParameter.OPERATERSTATUS_2);
                    tableStructureService.updateTableStructure(tableStructure);//修改状态为已完成
                }else{//将当前表释放，修改为启动状态，下一个定时任务可以继续执行
                    tableStructure.setFailNumber(middleService.getStatusNumber(tableStructure.getTableName(),"2"));
                    tableStructure.setSuccessNumber(middleService.getStatusNumber(tableStructure.getTableName(),"1"));
                    tableStructure.setOperaterStatus(BaseStaticParameter.OPERATERSTATUS_1);
                    tableStructureService.updateTableStructure(tableStructure);//修改状态为启动
                }
            }
        }
    }
}
