package com.zfsoft.batchimport.read;

import cn.hutool.core.lang.UUID;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zfsoft.batchimport.base.SysCode;
import com.zfsoft.batchimport.common.BaseStaticParameter;
import com.zfsoft.batchimport.domain.dto.*;
import com.zfsoft.batchimport.domain.entity.AsynTask;
import com.zfsoft.batchimport.domain.entity.ElecBaseMetadata;
import com.zfsoft.batchimport.domain.entity.SysAtta;
import com.zfsoft.batchimport.service.CommonService;
import com.zfsoft.batchimport.service.ElecBaseMetadataService;
import com.zfsoft.batchimport.service.impl.AsynTaskServiceImpl;
import com.zfsoft.batchimport.service.impl.SysAttaServiceImpl;
import com.zfsoft.batchimport.utils.AbstractFileTool;
import com.zfsoft.batchimport.utils.DateUtil;
import com.zfsoft.batchimport.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 每解析一行会回调invoke()方法。
 * 整个excel解析结束会执行doAfterAllAnalysed()方法
 */

//有个很重要的点   不能被spring管理,要每次读取excel都要new。
//这边就会有一个问题：如果UserInfoDataListener中需要用到Spring中的主键怎么办？
public class VehiclePermitDataListener extends AnalysisEventListener<LinkedHashMap<Integer, String>> {

    Logger logger = LoggerFactory.getLogger(VehiclePermitDataListener.class);

    //每次读取100条数据就进行保存操作
    private static final int BATCH_COUNT = 1000;
    //由于每次读都是新new UserInfoDataListener的，所以这个list不会存在线程安全问题
    List<CommonInsertDto> list = Lists.newArrayList();

    // 错误数据
    List<LinkedHashMap<Integer, String>> errList = Lists.newArrayList();

    // 异步处理开始时间
    private Long beginTime;

    //这个组件是Spring中的组件，这边推荐两种方法注入这个组件
    //第一种就是提供一个UserInfoDataListener的构造方法，这个方法提供一个参数是UserInfoDataListener类型
    //另外一种方法就是将 UserInfoDataListener 这个类定义成 UserService 实现类的内部类（推荐这种方式）
    private CommonService commonService;

    // excel导入任务id
    private String taskOid;

    // 任务信息服务层
    private AsynTaskServiceImpl asynTaskService;

    @Autowired
    private SysAttaServiceImpl sysAttaService;

    // 表结构数据
    private CommonCreateTableDto commonCreateTableDto;

    // 读取后的头数据
    private Map<Integer, String> headMap;

    // 封装后的
    private Map<String, Map<String, String>> headObjectMap;

    // 入库数据写excel对象
    private ExcelWriterSheetBuilder excelWriterSheetBuilder;

    // 失败数据存档excel路径
    private String errorFilePath;

    // 成功数据存档excel路径
    private String successFilePath;

    // 线程计数
    private CountDownLatch countDownLatch;

    // 入库错误记录
    private List<Future> inTableErrList;



    //构造函数
    public VehiclePermitDataListener(ElecBaseMetadataService elecBaseMetadataService, CommonService commonService, AsynTaskServiceImpl asynTaskService, SysAttaServiceImpl sysAttaService, String taskOid, CommonCreateTableDto commonCreateTableDto) {
        this.commonCreateTableDto = commonCreateTableDto;
        this.commonService = commonService;
        this.asynTaskService = asynTaskService;
        this.sysAttaService = sysAttaService;
        this.taskOid = taskOid;
        this.beginTime = System.currentTimeMillis(); ;
        Map<String, Map<String, String>> headObjectMap = Maps.newLinkedHashMap();
        //此处是模板中的基础数据
        List<ElecBaseMetadata> elecBaseMetadataList = elecBaseMetadataService.baseMetadataList();
        if(!CollectionUtils.isEmpty(elecBaseMetadataList)) {
            elecBaseMetadataList.forEach(elecBaseMetadata -> {
                Map<String, String> tempMap = Maps.newHashMap();
                tempMap.put("fieldName", elecBaseMetadata.getFieldName());
                tempMap.put("fieldCode", elecBaseMetadata.getFieldCode());
                tempMap.put("fieldLength", elecBaseMetadata.getFieldLength());
                tempMap.put("fieldType", elecBaseMetadata.getFieldType());
                //0:必填,1:可以不填
                tempMap.put("isNull", elecBaseMetadata.getIsNull());
                headObjectMap.put(elecBaseMetadata.getFieldName(), tempMap);
            });
        }
        //此处是从证照系统中传入的照面元素
        List<CommonTableFieldDto> commonTableFieldDtoList = commonCreateTableDto.getCommonTableFieldDtoList();
        if(!CollectionUtils.isEmpty(commonTableFieldDtoList)) {
            commonTableFieldDtoList.forEach(commonTableFieldDto -> {
                Map<String, String> tempMap = Maps.newHashMap();
                tempMap.put("fieldName", commonTableFieldDto.getFieldName());
                tempMap.put("fieldCode", commonTableFieldDto.getFieldCode());
                tempMap.put("fieldLength", commonTableFieldDto.getFieldLength());
                tempMap.put("fieldType", commonTableFieldDto.getFieldType());
                //0:必填,1:可以不填
                tempMap.put("isNull", (StringUtils.isEmpty(commonTableFieldDto.getIsNull()) || "1".equals(commonTableFieldDto.getIsNull())) ? "1" : "0");
                headObjectMap.put(commonTableFieldDto.getFieldName(), tempMap);
            });
        }
        this.headObjectMap = headObjectMap;
        this.inTableErrList = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void invoke(LinkedHashMap<Integer, String> data, AnalysisContext analysisContext) {
        // logger.info("解析到一条数据:{}", JSON.toJSONString(data));
        if(data.size()==0){
            analysisContext.readSheetHolder().setApproximateTotalRowNumber(analysisContext.readSheetHolder().getApproximateTotalRowNumber()-1) ;

        }else{

            LinkedHashMap<String, Object> resMap = Maps.newLinkedHashMap();
            if(null!=data&&data.size() > 0) {
                data.forEach((k, v) ->{
                    String fieldType = headObjectMap.get(headMap.get(k)).get("fieldType");
                    Object value = null;
//                    System.out.println(headObjectMap.get(headMap.get(k)));
                    if(StringUtils.isEmpty(v)) {
                        if("1".equals(headObjectMap.get(headMap.get(k)).get("isNull"))) {
                            value = null;
                        } else {
                            throw new RuntimeException(MessageFormat.format("{0}字段非空校验失败，字段值：{1}", headMap.get(k), v));
                        }
                    } else if(!StringUtils.isEmpty(fieldType)) {
                        //去除字符串解析成数字后带小数点问题
                        int position = v.lastIndexOf(".0");
                        if(v.length()>2&&v.length()-position==2){
                            v = v.substring(0,v.lastIndexOf(".0"));
                        }

                        int positionDate = v.lastIndexOf(" 00:00:00");
                        if(v.length()>9&&v.length()-positionDate==9){
                            v = v.substring(0,v.lastIndexOf(" 00:00:00"));
                        }
                        switch (fieldType.toLowerCase()) {
                            case "datetime":
                                // 需写一个通用转化方法
                                try {
                                    value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format (new SimpleDateFormat("yyyy-MM-dd").parse(v));
                                    //value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(v+" 00:00:00");
                                } catch (ParseException e) {
                                    throw new RuntimeException(MessageFormat.format("{0}字段日期格式化失败，字段值：{1}", headMap.get(k), v));
                                }
                                break;
                            case "decimal":
                                try {
                                    value = new BigDecimal(v).floatValue();
                                } catch (Exception e) {
                                    throw new RuntimeException(MessageFormat.format("{0}字段转化为decimal类型失败，字段值：{1}", headMap.get(k), v));
                                }
                                break;
                            case "varchar":
                                Integer fieldLength = Integer.valueOf(headObjectMap.get(headMap.get(k)).get("fieldLength"));
                                if(fieldLength < v.length()) {
                                    throw new RuntimeException(MessageFormat.format("{0}字段长度为{1}, 超出限制字段长度{2}插入失败！", headMap.get(k), v.length(), fieldLength));
                                }
                                value = v;
                                break;
                            default:
                                value = v;
                        }
                    }
                    resMap.put(headObjectMap.get(headMap.get(k)).get("fieldCode"), value);
                });
            }
            CommonInsertDto commonInsertDto = new CommonInsertDto();
            if(StringUtils.isEmpty(resMap.get("OID")) && StringUtils.isEmpty(resMap.get("oid"))) {
                String oid =  UUID.randomUUID().toString().replaceAll("-", "");
                resMap.put("oid", oid);
            }
            commonInsertDto.setFieldMap(resMap);
            commonInsertDto.setRowNum(String.valueOf(analysisContext.readRowHolder().getRowIndex() + 1));
            list.add(commonInsertDto);
            if (list.size() >= BATCH_COUNT) {
                saveData();
                // 存储完成清理 list
                list.clear();
            }
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        int oldCount = (int)Math.ceil(Float.valueOf(analysisContext.readRowHolder().getRowIndex()) / BATCH_COUNT);
        int realCount = (int)Math.ceil((Float.valueOf(analysisContext.readRowHolder().getRowIndex() - errList.size())) / BATCH_COUNT);
        // 防止一直等待
        if(oldCount > realCount) {
            for (int i = 0, len = oldCount - realCount; i < len; i++) {
                countDownLatch.countDown();
            }

        }

        try {
            countDownLatch.await(200, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 保存入库时异常数据
        if(!CollectionUtils.isEmpty(inTableErrList)) {
            inTableErrList.forEach(o -> {
                try {
                    List<LinkedHashMap<Integer, String>> inTableErr = (List<LinkedHashMap<Integer, String>>) o.get();
                    errList.addAll(inTableErr);
                }  catch (Exception e) {
                    logger.error("保存入库异常数据失败!", e);
                }
            });
        }
        logger.info("所有数据解析完成！用时{}", System.currentTimeMillis() - beginTime);
        String filePath = saveErrData();
        FileInputStream fileInputStream = null;
        try {
            Map<String, String> param = Maps.newHashMap();
            // 文件保存路径
            File file = new File(filePath);
            fileInputStream = new FileInputStream(file);

            String fileId = null;
            try {
                // 文件名
                String fileName = filePath.split("\\\\")[filePath.split("\\\\").length - 1];
                String fileRealPath = filePath.substring(BaseStaticParameter.ELMS_TEMP.length(),filePath.length());
                fileRealPath = fileRealPath.substring(1,fileRealPath.lastIndexOf(File.separator));
                SysAtta excelAtta = new SysAtta();
                excelAtta.setOid(null);
                excelAtta.setExtensionName("xlsx");
                excelAtta.setName(fileName);
                excelAtta.setFilePath(fileRealPath);
                excelAtta.setOriginName(fileName);
                excelAtta.setIsDelete(BaseStaticParameter.NO);
                excelAtta.setUploadDate(new Date());
                excelAtta.setUserOid(BaseStaticParameter.DEFAULT_ADMIN_OID);
//                excelAtta
                //上传的4种方式
                //按照相关的方式上传
                AbstractFileTool abstractFileTool = new AbstractFileTool();
                String fastdfsPath = abstractFileTool.upLoad(fileInputStream, excelAtta.getName());

                if(StringUtil.isEmpty(fastdfsPath)){
                    fileId = "文件服务失败";
                    System.out.println(fileId);
                }else{
                    excelAtta.setFastdfsPath(fastdfsPath);
                }
                sysAttaService.saveOrUpdateSysAtta(excelAtta);
                fileId = excelAtta.getOid();
                file.delete();
            }catch (Exception e) {
                fileId = "上传统一文件服务失败";
            }

            AsynTask asynTask = this.asynTaskService.queryByOid(taskOid);
            asynTask.setModifyDate(new Date());
            asynTask.setFailFileId(fileId);
            asynTask.setTaskEndTime(new Date());
            if(analysisContext != null) {

                asynTask.setTaskTotalNumber(analysisContext.readSheetHolder().getApproximateTotalRowNumber()-1);
                asynTask.setTaskSuccessNumber(analysisContext.readSheetHolder().getApproximateTotalRowNumber()-1 - errList.size());
            } else {
                asynTask.setTaskTotalNumber(0);
                asynTask.setTaskSuccessNumber(0);
            }

            asynTask.setTaskFailNumber(errList.size());
            asynTask.setTaskTotalTime(System.currentTimeMillis() - beginTime);
            asynTask.setTaskStatus(SysCode.TASK_STATUS.TASK_STATUS_END);
            this.asynTaskService.saveOrUpdate(asynTask);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        errList.clear();
        headMap.clear();
        headObjectMap.clear();
    }

    /**
     * 这里会一行行的返回头
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        logger.info("解析到一条头数据:{}", JSONObject.toJSONString(headMap));
        this.headMap = headMap;
        countDownLatch = new CountDownLatch((int) Math.ceil(Float.valueOf((context.getTotalCount() - 1)) / BATCH_COUNT));
    }

    /**
     * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     *
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        // logger.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        LinkedHashMap<Integer, String> errMap = (LinkedHashMap<Integer, String>) context.readRowHolder().getCurrentRowAnalysisResult();
        LinkedHashMap<Integer, String> tempErrMap = Maps.newLinkedHashMap();
        tempErrMap.put(0, String.valueOf(context.readRowHolder().getRowIndex() + 1));
        if(errMap != null && errMap.size() > 0) {
            errMap.forEach((k, v) ->{
                tempErrMap.put(k + 1, v);
            });
        }
        tempErrMap.put(tempErrMap.size(), exception.toString());
        errList.add(tempErrMap);
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        // if (exception instanceof ExcelDataConvertException) {
        //    ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
        //    logger.error("第{}行，第{}列解析异常", excelDataConvertException.getRowIndex(),
        //            excelDataConvertException.getColumnIndex(), exception);
        // }
    }

    private void saveData() {
        logger.info("{}条数据，开始存储数据库！", list.size());

        //保存数据
        Future<List<LinkedHashMap<Integer, String>>> future = commonService.saveList(list.stream().collect(Collectors.toList()),commonCreateTableDto,  countDownLatch);
        inTableErrList.add(future);
        // 现有框架实现不了在文件底部无实体写入 -- 等待后续更新
//        if(StringUtils.isEmpty(successFilePath)) {
////            successFilePath = this.getClass().getResource("/").getPath() + "noModleWrite" + System.currentTimeMillis() + ".xlsx";
//            successFilePath = "D:\\upload1\\" + "noModleWrite" + System.currentTimeMillis() + ".xlsx";
//        }
//        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
//        if(null == excelWriterSheetBuilder) {
//            excelWriterSheetBuilder = EasyExcel.write(successFilePath).head(headForSuccessFile()).sheet("sheet1");
//        }
//        excelWriterSheetBuilder.doWrite(successDataList());
        // logger.info("存储数据库成功！");
    }

    private String saveErrData()  {
        if(StringUtils.isEmpty(errorFilePath)) {
//             errorFilePath = this.getClass().getResource("/").getPath() + "noModleWrite" + System.currentTimeMillis() + ".xlsx";
            // 本地测试不要使用项目部署路径 会导致项目热更新
            errorFilePath = "Excel_ERROR_" + System.currentTimeMillis() + ".xlsx";

            errorFilePath =  BaseStaticParameter.ELMS_TEMP+File.separator+ DateUtil.date2Str(DateUtil.yyyyMMdd) +File.separator+errorFilePath;
//            errorFilePath =  BaseStaticParameter.ELMS_TEMP+File.separator+ "20200000" +File.separator+errorFilePath;
            File toF = new File(errorFilePath);
            if(toF.exists()) {
                toF.delete();
            }
            File parentFile = toF.getParentFile();
            // 当文件夹不存在时，自行创建
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
        }
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        ExcelWriterSheetBuilder excelWriterSheetBuilder = EasyExcel.write(errorFilePath).head(headForErrorFile()).sheet("sheet1");
        excelWriterSheetBuilder.doWrite(errorDataList());
        return errorFilePath;
    }

    private List<List<String>> headForSuccessFile() {
        List<List<String>> list = Lists.newArrayList();
        if(headMap != null && headMap.size() > 0) {
            List<String> head0 = Lists.newArrayList();
            headMap.forEach((k, v) -> {
                List<String> head = Lists.newArrayList();
                head.add(v);
                list.add(head);
            });
            List<String> headEnd = Lists.newArrayList();
            headEnd.add("入库id");
            list.add(headEnd);
        }
        return list;
    }

    private List<List<String>> headForErrorFile() {
        List<List<String>> list = Lists.newArrayList();
        if(headMap != null && headMap.size() > 0) {
            List<String> head0 = Lists.newArrayList();
            head0.add("错误行号");
            list.add(head0);
            headMap.forEach((k, v) -> {
                List<String> head = Lists.newArrayList();
                head.add(v);
                list.add(head);
            });
            List<String> headOid = Lists.newArrayList();
            headOid.add("主键");
            list.add(headOid);
            List<String> headEnd = Lists.newArrayList();
            headEnd.add("错误信息");
            list.add(headEnd);
        }
        return list;
    }

    private List<List<Object>> errorDataList() {
        List<List<Object>> errorDataList = Lists.newArrayList();
            if(errList != null && errList.size() > 0) {
                errList.forEach(map -> {
                    List<Object> data = Lists.newArrayList();
                    map.forEach((k, v) -> {
                        data.add(v);
                    });
                    errorDataList.add(data);
                });
            }
        return errorDataList;
    }

    private List<List<Object>> successDataList() {
        List<List<Object>> successDataList = Lists.newArrayList();
        if(list != null && list.size() > 0) {
            list.forEach(commonInsertDto -> {
                List<Object> data = Lists.newArrayList();
                LinkedHashMap<String, Object> objectMap = commonInsertDto.getFieldMap();
                objectMap.forEach((k, v) -> {
                    data.add(StringUtils.isEmpty(v) ? "" : v);
                });
                successDataList.add(data);
            });
        }
        return successDataList;
    }

}