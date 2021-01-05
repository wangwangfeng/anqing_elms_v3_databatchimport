package com.zfsoft.batchimport.thread;

import com.github.pagehelper.util.StringUtil;
import com.google.common.collect.Lists;
import com.zfsoft.batchimport.common.BaseStaticParameter;
import com.zfsoft.batchimport.domain.dto.TableParamDto;
import com.zfsoft.batchimport.domain.entity.ElecLicenseMain;
import com.zfsoft.batchimport.domain.entity.LicenseMetadata;
import com.zfsoft.batchimport.domain.entity.SysAtta;
import com.zfsoft.batchimport.domain.entity.TableStructure;
import com.zfsoft.batchimport.mapper.elms.CommonMapper;
import com.zfsoft.batchimport.mapper.elms.ElecLicenseMainMapper;
import com.zfsoft.batchimport.mapper.elms.LicenseMetadataMapper;
import com.zfsoft.batchimport.mapper.elms.TableStructureMapper;
import com.zfsoft.batchimport.mapper.middle.MiddleMapper;
import com.zfsoft.batchimport.service.ElecLicenseMainService;
import com.zfsoft.batchimport.service.impl.ElecLicenseLiceFileServiceImpl;
import com.zfsoft.batchimport.service.impl.ElecLicenseMainTableServiceImpl;
import com.zfsoft.batchimport.service.impl.SysAttaServiceImpl;
import com.zfsoft.batchimport.utils.*;
import com.zfsoft.batchimport.utils.enm.ParamType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.sql.Blob;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Future;

/**
 * @author chenyq
 * @Description: 执行表结构处理数据入库线程
 * @date 2020/5/8 13:51
 */
@Component
@EnableTransactionManagement
public class TableStructureThread{

    private static final Logger logger = LoggerFactory.getLogger(TableStructureThread.class);
    @Autowired
    private CommonMapper commonMapper;

    @Autowired
    private TableStructureMapper tableStructureMapper;
    @Autowired
    private ElecLicenseMainMapper elecLicenseMainMapper;
    @Autowired
    private ElecLicenseMainService elecLicenseMainService;
    @Autowired
    private ElecLicenseLiceFileServiceImpl elecLicenseLiceFileService ;
    @Autowired
    private ElecLicenseMainTableServiceImpl elecLicenseMainTableServiceImpl;


    @Autowired
    private SysAttaServiceImpl sysAttaService;
    @Autowired
    private LicenseMetadataMapper licenseMetadataMapper;


    @Autowired
    private MiddleMapper middleMapper;

    @Async("asyncServiceExecutor")
    public Future<Integer> doSaveElms(List<HashMap<String,Object>>  mapList, TableStructure tableStructure) {
        Thread thread = Thread.currentThread();
        logger.info(MessageFormat.format("线程{0}开始执行！", thread.getName()));
        // 记录线程中入库失败数据
        Integer errCount = 0;
        //有效期
        List<HashMap> elecLicenseDirTypeTimeDtoList = commonMapper.getElecLicenseDirTypeTimeList(tableStructure.getLicenseTypeOid());
//                ElecLicenseDirTypeTime
        //照面元素信息
        List<LicenseMetadata> licenseMetadataList = licenseMetadataMapper.getLicenseMetadataList(tableStructure.getOrganOid(),tableStructure.getDirectoryOid());

        List<HashMap> templateList = commonMapper.getTemplateList(tableStructure.getDirectoryOid(),tableStructure.getOrganOid());
//                Template
        //3、固定字段匹配hashMap中字段值，获取数据写入对象
        for(HashMap<String,Object> fieldMap :mapList){
            ElecLicenseMain elecLicenseMain = new ElecLicenseMain();
            try {
                //数据存放在主表中一一对应，其中包括有效期范围
                //证照编号不能重复
                if(StringUtils.isNotEmpty((CharSequence) fieldMap.get("LICENSE_NUMBER").toString())) {
                    //查询es中证照主表中的数据
                    if(this.elecLicenseMainService.viewMainByLicenseNumber( String.valueOf(fieldMap.get("LICENSE_NUMBER"))) > 0) {
                        this.errorTableData(MessageFormat.format("证照编号已存在于办理列表中，证照编号：{0}",String.valueOf(fieldMap.get("LICENSE_NUMBER"))),
                                tableStructure.getTableName(),fieldMap.get("LICENSE_NUMBER").toString());
                        errCount ++;
                        continue;
                    }
                    //查询es中证照签发表数据
                    if(this.elecLicenseMainService.viewSubedByLicenseNumber( String.valueOf(fieldMap.get("LICENSE_NUMBER"))) > 0) {
                        this.errorTableData(MessageFormat.format("证照编号已存在于已签发列表中，证照编号：{0}",String.valueOf(fieldMap.get("LICENSE_NUMBER"))),
                                tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                        errCount ++;
                        continue;
                    }
                }

                if(fieldMap.get("HOLDER_TYPE_NAME")==null){
                    this.errorTableData(MessageFormat.format("持证者代码类型不能为空，证照编号：{0}",String.valueOf(fieldMap.get("LICENSE_NUMBER"))),
                            tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                    errCount ++;
                    continue;
                }
                elecLicenseMain.setLicenseNumber(fieldMap.get("LICENSE_NUMBER").toString());
                if(fieldMap.get("HOLDER")==null){
                    this.errorTableData(MessageFormat.format("持证者名称不能为空，证照编号：{0}",String.valueOf(fieldMap.get("LICENSE_NUMBER"))),
                            tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                    errCount ++;
                    continue;
                }
                elecLicenseMain.setHolder(fieldMap.get("HOLDER").toString());
                //判断证照等级是不是A、B、C、D
                elecLicenseMain.setElecLicenseLevel(fieldMap.get("ELEC_LICENSE_LEVEL")!=null&&fieldMap.get("ELEC_LICENSE_LEVEL").toString().length()==1?fieldMap.get("ELEC_LICENSE_LEVEL").toString():null);//证照等级
                if(fieldMap.get("CONTACT_TEL")!=null){
                    if((com.zfsoft.batchimport.utils.StringUtil.isMobile(fieldMap.get("CONTACT_TEL").toString())||com.zfsoft.batchimport.utils.StringUtil.isTelPhone(fieldMap.get("CONTACT_TEL").toString()))){
                        elecLicenseMain.setContactTel(fieldMap.get("CONTACT_TEL").toString());//联系电话
                    }else{
                        this.errorTableData(MessageFormat.format("联系电话格式错误，证照编号：{0}",String.valueOf(fieldMap.get("LICENSE_NUMBER"))),
                                tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                        errCount ++;
                        continue;
                    }
                }

                if(fieldMap.get("IDENTIFICATE_NUMBER")==null){
                    this.errorTableData(MessageFormat.format("证件号码不能为空，证照编号：{0}",String.valueOf(fieldMap.get("LICENSE_NUMBER"))),
                            tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                    errCount ++;
                    continue;
                }

                //查询es中证照主表中的数据
                if (elecLicenseMainService.countMainByIdentificateNumber(fieldMap.get("IDENTIFICATE_NUMBER").toString(), "null", tableStructure.getDirectoryOid()) > 0) {
                    this.errorTableData(MessageFormat.format("持证人证件号码存在于办理列表中，证照编号：{0}",String.valueOf(fieldMap.get("LICENSE_NUMBER"))),
                            tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                    errCount ++;
                    continue;
                }
                //查询es中证照签发表数据
                if (elecLicenseMainService.countMainByIdentificateNumber(fieldMap.get("IDENTIFICATE_NUMBER").toString(), tableStructure.getDirectoryOid()) > 0) {
                    this.errorTableData(MessageFormat.format("持证人证件号码证照编号存在于已签发列表中，证照编号：{0}",String.valueOf(fieldMap.get("LICENSE_NUMBER"))),
                            tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                    errCount ++;
                    continue;
                }
               if(fieldMap.get("HOLDER_TYPE_NAME").toString().equals("公民身份号码")){
                    if(!com.zfsoft.batchimport.utils.StringUtil.isIdCard(fieldMap.get("IDENTIFICATE_NUMBER").toString())){
                        this.errorTableData(MessageFormat.format("公民身份号码格式错误，证照编号：{0}",String.valueOf(fieldMap.get("LICENSE_NUMBER"))),
                                tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                        errCount ++;
                        continue;
                    }else{
                        elecLicenseMain.setIdentificateNumber(fieldMap.get("IDENTIFICATE_NUMBER").toString());// 持证人证件号码
                    }
                }else{
                   elecLicenseMain.setIdentificateNumber(fieldMap.get("IDENTIFICATE_NUMBER") == null ? null :fieldMap.get("IDENTIFICATE_NUMBER").toString());// 持证人证件号码
               }
                try {//有效期起始时间
                    //chenyq hashMap中date类型取不出来
                    if(fieldMap.get("VALIDITY_DATE_START")==null){
                        this.errorTableData(MessageFormat.format("有效期起始时间不能为空，证照编号：{0}",String.valueOf(fieldMap.get("LICENSE_NUMBER"))),
                                tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                        errCount ++;
                        continue;
                    }
                    Date date1=  (Date)fieldMap.get("VALIDITY_DATE_START");
                    elecLicenseMain.setValidityDateStart(date1);
                } catch (Exception e) {
                    this.errorTableData(MessageFormat.format("有效期起始时间不能为空并且日期格式为yyyy-MM-dd，证照编号：{0}",String.valueOf(fieldMap.get("LICENSE_NUMBER"))),
                            tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                     errCount ++;
                    continue;
                }
                //得到有效期
                String timeOid="";
                String  longEffect = BaseStaticParameter.LONG_EFFECT_NO; //是否长期 0是 1否
                if(fieldMap.get("VALIDITY_RANGE")==null){
                    this.errorTableData(MessageFormat.format("有效期范围不能为空，证照编号：{0}",String.valueOf(fieldMap.get("LICENSE_NUMBER"))),
                            tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                    errCount ++;
                    continue;
                }
                String longEffectStr = fieldMap.get("VALIDITY_RANGE").toString();
                String type = "";

                HashMap elecLicenseDirTypeTime = new HashMap(16);
                if(!CollectionUtils.isEmpty(elecLicenseDirTypeTimeDtoList)) {
                    for(HashMap hashMap :elecLicenseDirTypeTimeDtoList){
                        if(BaseStaticParameter.ELECLICENCE_TYPE_TIME_3.equals(hashMap.get("TIME_TYPE"))){
                            type=hashMap.get("TIME_VALUE")+"日";
                        }else if(BaseStaticParameter.ELECLICENCE_TYPE_TIME_2.equals(hashMap.get("TIME_TYPE"))){
                            type = (hashMap.get("TIME_VALUE")+"月");
                        }else if(BaseStaticParameter.ELECLICENCE_TYPE_TIME_1.equals(hashMap.get("TIME_TYPE"))){
                            type = (hashMap.get("TIME_VALUE")+"年");
                        }else if(BaseStaticParameter.ELECLICENCE_TYPE_TIME_4.equals(hashMap.get("TIME_TYPE"))){
                            type = ("长期");
                        } else if(BaseStaticParameter.ELECLICENCE_TYPE_TIME_5.equals(hashMap.get("TIME_TYPE"))){
                            type = ("自定义");
                        }
                        if(type.equals(longEffectStr)){
                            if(longEffectStr.equals("长期")){
                                longEffect= ("0");
                            }
                            elecLicenseDirTypeTime = hashMap;
                            timeOid= hashMap.get("TIME_OID").toString();
                            break;
                        }
                    }
                }
                if(timeOid.length() >0) {
                    elecLicenseMain.setLongEffect(longEffect);//有效期标识
                    if (elecLicenseDirTypeTime != null) {
                        if (elecLicenseDirTypeTime.get("TIME_TYPE").toString().equals(BaseStaticParameter.ELECLICENCE_TYPE_TIME_4)) {
                            elecLicenseMain.setAutoCancelFlag(BaseStaticParameter.AUTO_CANCEL_NO);
                            elecLicenseMain.setAutoAnnualFlag(BaseStaticParameter.AUTO_ANNUAL_NO);
                        } else if (elecLicenseDirTypeTime.get("TIME_TYPE").toString().equals(BaseStaticParameter.ELECLICENCE_TYPE_TIME_5)) {
                            if(fieldMap.get("VALIDITY_DATE_END")==null){
                                this.errorTableData(MessageFormat.format("有效期范围为自定义时，有效期止不能为空，证照编号：{0}",String.valueOf(fieldMap.get("LICENSE_NUMBER"))),
                                        tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                                errCount ++;
                                continue;
                            }
                            if (StringUtil.isNotEmpty(fieldMap.get("VALIDITY_DATE_END").toString())) {
                                try {
                                    /*SimpleDateFormat sfm = new SimpleDateFormat("yyyy-MM-dd");
                                    Date date = sfm.parse(fieldMap.get("VALIDITY_DATE_END").toString().substring(0,19));*/
                                    elecLicenseMain.setValidityDateEnd((Date)fieldMap.get("VALIDITY_DATE_END"));
                                } catch (Exception e) {
                                    this.errorTableData(MessageFormat.format("有效期为自定义状态下，有效期止不能为空并且日期格式为yyyy-MM-dd，证照编号：{0}", fieldMap.get("LICENSE_NUMBER")),
                                            tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                                     errCount ++;
                                     continue;
                                }
                            } else {
                                CountNumberOfDaysAYear CNODAY = new CountNumberOfDaysAYear();
                                try {
                                    Date endDate = CNODAY.addDate(elecLicenseMain.getValidityDateStart(),
                                            elecLicenseDirTypeTime.get("TIME_VALUE").toString(),
                                            elecLicenseDirTypeTime.get("TIME_TYPE").toString());
                                    if (null != endDate) {
                                        elecLicenseMain.setValidityDateEnd(endDate);
                                    }
                                } catch (Exception e) {
                                    this.errorTableData(MessageFormat.format("有效期为自定义状态下，有效期止不能为空并且日期格式为yyyy-MM-dd，证照编号：{0}", fieldMap.get("LICENSE_NUMBER")),
                                            tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                                    errCount ++;
                                    continue;
                                }
                            }
                        }
                    } else {
                        this.errorTableData(MessageFormat.format("有效期范围出错，证照编号：{0}", fieldMap.get("VALIDITY_RANGE")),
                                tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                        errCount ++;
                        continue;
                    }

                    if (longEffect.equals(BaseStaticParameter.LONG_EFFECT_NO)) {//非长期有效，判断年检和注销
                        // 验证是否需要年检
                        if(org.springframework.util.StringUtils.isEmpty(fieldMap.get("AUTO_ANNUAL_FLAG"))){
                            this.errorTableData(MessageFormat.format("是否需要年检不能为空，证照编号：{0}", fieldMap.get("LICENSE_NUMBER")),
                                    tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                            errCount ++;
                            continue;
                        }
                        String autoAnnual = fieldMap.get("AUTO_ANNUAL_FLAG").toString();//是否需要年检
                        String autoAnnualFlag = "";
                        if (!StringUtil.isEmpty(autoAnnual)) {
                            if ("需要".equals(autoAnnual)) {
                                autoAnnualFlag = BaseStaticParameter.AUTO_ANNUAL_YES;
                                elecLicenseMain.setAutoAnnualFlag(autoAnnualFlag);
                                if(org.springframework.util.StringUtils.isEmpty(fieldMap.get("AUTO_ANNUAL_DATE"))){
                                    this.errorTableData(MessageFormat.format("需要年检时，提前年检天数不能为空，证照编号：{0}", fieldMap.get("LICENSE_NUMBER")),
                                            tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                                    errCount ++;
                                    continue;
                                }
                                String autoAnnualDays = fieldMap.get("AUTO_ANNUAL_DATE").toString();//提前年检天数
                                if ( DateUtil.isNumeric(autoAnnualDays)) {
                                    elecLicenseMain.setAutoAnnualDate(autoAnnualDays);
                                } else {
                                    this.errorTableData(MessageFormat.format("提前年检天数出错，只能填写数字，证照编号：{0}", fieldMap.get("LICENSE_NUMBER")),
                                            tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                                    errCount ++;
                                    continue;
                                }
                            } else if ("不需要".equals(autoAnnual)) {
                                autoAnnualFlag = BaseStaticParameter.AUTO_ANNUAL_NO;
                                elecLicenseMain.setAutoAnnualFlag(autoAnnualFlag);
                            } else {
                                this.errorTableData(MessageFormat.format("是否需要年检不符合规范,只能填写需要和不需要，证照编号：{0}", fieldMap.get("AUTO_ANNUAL_DATE")),
                                        tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                                errCount ++;
                                continue;
                            }
                        } else {
                            this.errorTableData(MessageFormat.format("是否需要年检不能为空,证照编号：{0}", String.valueOf(fieldMap.get("LICENSE_NUMBER"))),
                                    tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                                errCount ++;
                                continue;
                        }
                        // 验证超期是否需要注销
                        if(org.springframework.util.StringUtils.isEmpty(fieldMap.get("AUTO_CANCEL_FLAG"))){
                            this.errorTableData(MessageFormat.format("超期是否需要注销不能为空,证照编号：{0}", String.valueOf(fieldMap.get("LICENSE_NUMBER"))),
                                    tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                            errCount ++;
                            continue;
                        }
                        String autoCancel = fieldMap.get("AUTO_CANCEL_FLAG").toString();//超期是否需要注销
                        String autoCancelFlag = "";
                        if (!StringUtil.isEmpty(autoCancel)) {
                            if ("需要".equals(autoCancel)) {
                                autoCancelFlag = BaseStaticParameter.AUTO_CANCEL_YES;
                                elecLicenseMain.setAutoCancelFlag(autoCancelFlag);
                                if(org.springframework.util.StringUtils.isEmpty(fieldMap.get("AUTO_CANCEL_DATE"))){//
                                   this.errorTableData(MessageFormat.format("超期自动注销天数不能为空，证照编号：{0}", fieldMap.get("AUTO_CANCEL_DATE")),
                                           tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                                   errCount ++;
                                   continue;
                               }
                                String autoCancelDays = fieldMap.get("AUTO_CANCEL_DATE").toString();//超期自动注销天数
                                if (DateUtil.isNumeric(autoCancelDays)) {
                                    elecLicenseMain.setAutoCancelDate(autoCancelDays);
                                } else {
                                    this.errorTableData(MessageFormat.format("超期自动注销天数出错,只能填写数字，证照编号：{0}", fieldMap.get("AUTO_CANCEL_DATE")),
                                            tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                                    errCount ++;
                                    continue;
                                }
                            } else if ("不需要".equals(autoCancel)) {
                                autoCancelFlag = BaseStaticParameter.AUTO_CANCEL_NO;
                                elecLicenseMain.setAutoCancelFlag(autoCancelFlag);
                            } else {
                                this.errorTableData(MessageFormat.format("超期自动注销不符合规范,只能填写需要和不需要，证照编号：{0}", fieldMap.get("AUTO_CANCEL_DATE")),
                                        tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                                errCount ++;
                                continue;
                            }
                        } else {
                            this.errorTableData(MessageFormat.format("超期自动注销不能为空,证照编号：{0}", String.valueOf(fieldMap.get("LICENSE_NUMBER"))),
                                    tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                            errCount ++;
                            continue;
                        }
                    }
                    if(fieldMap.get("WATER_MARK")!=null){
                        elecLicenseMain.setWaterMark(fieldMap.get("WATER_MARK").toString());// 水印内容
                    }
                    if(fieldMap.get("REMARKS")!=null){
                        elecLicenseMain.setRemarks(fieldMap.get("REMARKS").toString());// 定制化内容
                    }
                    List<TableParamDto> tableParamDtoList = new ArrayList<>();
                    Boolean metaFlagError = false;
                    for (LicenseMetadata licenseMetadata : licenseMetadataList) {
                        String mustFlag= licenseMetadata.getMustFlag();// 字段名
                        if(mustFlag.equals("0")&&fieldMap.get(licenseMetadata.getColumnName()) == null ){
                            metaFlagError = true;
                            this.errorTableData(MessageFormat.format(licenseMetadata.getColumnName()+"为必填项，不能为空,证照编号：{0}", String.valueOf(fieldMap.get("LICENSE_NUMBER"))),
                                    tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                            break;
                        }
                        String tableValue = fieldMap.get(licenseMetadata.getColumnName()) == null ? null :fieldMap.get(licenseMetadata.getColumnName()).toString();// 数据库中字段值

                        ParamType paramType = licenseMetadata.getColumnType().equals("file") ? ParamType.FILE : ( licenseMetadata.getColumnType().equals("date") ? ParamType.DATE : ParamType.STRING);
                       //当是图片时，对图片进行解析上传到文件服务器上
                        if(paramType.equals( ParamType.FILE)&& com.zfsoft.batchimport.utils.StringUtil.isNotEmpty(tableValue)){
                            byte[] bytes = (byte[])fieldMap.get(licenseMetadata.getColumnName());// 数据库中字段值

                            String value = this.metadataInput(bytes)   ;
                            if(value!=null && value.equals("错误")){
                                this.errorTableData(MessageFormat.format("照面元素中文件解析错误，照面元素字段为："+ licenseMetadata.getColumnType() + "证照编号：{0}", elecLicenseMain.getLicenseNumber()),
                                        tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                                errCount ++;
                                continue;
                            }else{
                                tableValue = value;
                            }
                        }

                        TableParamDto TableParamDto = new TableParamDto(licenseMetadata.getColumnName(),licenseMetadata.getMetadataName(),paramType,tableValue);
                        tableParamDtoList.add(TableParamDto);
                    }
                    if(metaFlagError){
                        errCount ++;
                        continue;
                    }
                    String tableParamOid = UUID.randomUUID().toString().replaceAll("-", "");
                    //此处像T_ELEC_LICENSE_TABLE中存储证照元素信息
                    String tableMessage = elecLicenseMainTableServiceImpl.saveOrUpdateMetadataTable(tableParamOid,tableParamDtoList);
                    if (!StringUtil.isEmpty(tableMessage)) {
                        this.errorTableData(MessageFormat.format(tableMessage + "证照编号：{0}", elecLicenseMain.getLicenseNumber()),
                                tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                        errCount ++;
                        continue;
                    }
                    elecLicenseMain.setTableParamOid( tableParamOid );//在证照主表中保存照面元素表主键信息
                    //标识的组成部分从左至右依次为：电子证照OID、证照名称代码、流水号、版本号和校验位，各项之间用短横线“-”分隔。
                    //电子证照OID固定为：1.2.156.3003.2。
                    if (StringUtil.isEmpty(elecLicenseMain.getLicenseVersion())) {
                        elecLicenseMain.setLicenseVersion(BusinessUtil.createVersionNumber(null));
                    }
                    if (StringUtil.isEmpty(elecLicenseMain.getElecLicenseUnique())) {
                        //电子证照代码
                        String licenseTypeCode = tableStructure.getLicenseTypeCode();
                        //流水号
                        String flowNumber = BusinessUtil.getFlowNumber();
                        //版本号
                        String licenseVersion = elecLicenseMain.getLicenseVersion();
                        licenseVersion=licenseVersion.substring(licenseVersion.length()-2, licenseVersion.length());
                        String elecLicenseCheck=BusinessUtil.ELECLICENSE_OID+"."+licenseTypeCode+"."+flowNumber+"."+licenseVersion;
                        String checkCode=BusinessUtil.getCertificateIdentifier(elecLicenseCheck.replace(".", ""), BusinessUtil.Designation.ISO_7064_MOD_37_HYBRID_36);
                        String elecLicenseUnique=elecLicenseCheck+"."+checkCode.substring(checkCode.length()-1, checkCode.length());
                        elecLicenseMain.setElecLicenseUnique(elecLicenseUnique);
                    }
                    elecLicenseMain.setClaimOid(tableStructure.getClaimOid());
                    elecLicenseMain.setDirectoryOid(tableStructure.getDirectoryOid());
                    elecLicenseMain.setLicenseStatus("0");
                    elecLicenseMain.setHolderType(tableStructure.getDirectoryObj());//持证者类型
                    elecLicenseMain.setHolderCode(BaseStaticParameter.HOLDER_CODE_MAP.get(fieldMap.get("HOLDER_TYPE_NAME").toString()));
                    if(StringUtil.isEmpty(elecLicenseMain.getHolderCode())){
                        this.errorTableData(MessageFormat.format("持有者代码类型异常，证照编号：{0}", elecLicenseMain.getLicenseNumber()),
                                tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                        errCount ++;
                        continue;
                    }
                    elecLicenseMain.setElecLicenOid( UUID.randomUUID().toString().replaceAll("-", ""));
                    elecLicenseMain.setTableParamOid(tableParamOid);
                    elecLicenseMain.setTimeOid(timeOid);
                    //此处保存证照入库
                    //发送es保存主表信息
                    String message= "";
                    message  = elecLicenseMainService.saveOrUpdateElecLicenseMainWithES(elecLicenseMain,tableStructure.getDistrictOid(),tableStructure.getOrganOid(),tableStructure.getDirectoryObj(),tableStructure.getDirectoryName());
                    if(StringUtil.isNotEmpty(message)){
                        this.errorTableData(MessageFormat.format(message+"，证照编号：{0}", elecLicenseMain.getLicenseNumber()),
                                tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                        errCount ++;
                        continue;
                    }

                    //4、操作对象值生成ofd并入库操作
                    //生成文件信息ofd，包含文件表入库
                    message = elecLicenseLiceFileService.createElecLicenseMainFileLice(elecLicenseMain
                            , licenseMetadataList, templateList,tableParamOid) ;
                    if(StringUtil.isNotEmpty(message)){
                        this.errorTableData(MessageFormat.format(message+"，证照编号：{0}", elecLicenseMain.getLicenseNumber()),
                                tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                        errCount ++;
                        continue;
                    }else{
                        this.successTableData("成功",
                                tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                    }
                }
            } catch (Exception e) {
                this.errorTableData(MessageFormat.format("处理异常"+(e.toString().length()>1000?e.toString().substring(0,1000):e.toString()),""),
                        tableStructure.getTableName(),String.valueOf(fieldMap.get("LICENSE_NUMBER")));
                errCount ++;
                continue;
            }
        }
        logger.info(MessageFormat.format("线程{0}执行结束！", thread.getName()));
        return new AsyncResult<>(errCount);
    }
    private String metadataInput( byte[] imgValue) throws Exception {
        AbstractFileTool abstractFileTool = new AbstractFileTool();
        //解析base64为图片
        String ofdPath = BaseStaticParameter.ELMS_TEMP+ File.separator+DateUtil.date2Str(DateUtil.yyyyMMdd) +File.separator;
        String ofdName =  + System.currentTimeMillis() + ".jpg";
        SysAtta ofdAtta = new SysAtta();
        ofdAtta.setOid(null);
        ofdAtta.setExtensionName("jpg");
        ofdAtta.setName(ofdName);
        ofdAtta.setFilePath(ofdPath);
        ofdAtta.setOriginName(ofdName);
        ofdAtta.setIsDelete("N");
        ofdAtta.setUploadDate(new Date());
        InputStream stream = BytesToInputStream(imgValue);
        String fastdfsPath =  abstractFileTool.upLoad(stream, ofdAtta.getName());

        if(com.zfsoft.batchimport.utils.StringUtil.isEmpty(fastdfsPath)){
            return  "文件服务失败";
        }else{
            ofdAtta.setFastdfsPath(fastdfsPath);
            sysAttaService.saveOrUpdateSysAtta(ofdAtta);
            return ofdAtta.getOid() ;
        }

    }
    /**
     * String转byte[]
     * @param str
     * @return
     */
    public static byte[] StringToBytes(String str){
        byte[] bytes = str.getBytes();
        return bytes;
    }

    /**
     * byte[]转InputStream
     * @param bytes
     * @return
     * @throws Exception
     */
    public static InputStream BytesToInputStream(byte[] bytes) throws Exception{
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        return is;
    }

    /**
     * 出现异常时，将数据入库处理，并将详细信息记录
     * @author chenyq
     * @date 20200509
     * @param operaterMessage
     * @param tableName
     * @param licenseNumber
     */
    private  void errorTableData(String operaterMessage,String tableName,String licenseNumber){
        middleMapper.updateTableByLicenseNumber(operaterMessage,tableName,licenseNumber,BaseStaticParameter.TABLEOPERATERSTATUS_2);
    }

    /**
     * 入库成功
     * @author chenyq
     * @date 20200511
     * @param operaterMessage
     * @param tableName
     * @param licenseNumber
     */
    private  void successTableData(String operaterMessage,String tableName,String licenseNumber){
        middleMapper.updateTableByLicenseNumber(operaterMessage,tableName,licenseNumber,BaseStaticParameter.TABLEOPERATERSTATUS_1);
    }
}
