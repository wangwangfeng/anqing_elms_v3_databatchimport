package com.zfsoft.batchimport.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zfsoft.batchimport.common.BaseStaticParameter;
import com.zfsoft.batchimport.domain.dto.*;
import com.zfsoft.batchimport.domain.entity.ElecLicenseMain;
import com.zfsoft.batchimport.mapper.elms.CommonMapper;
import com.zfsoft.batchimport.mapper.elms.ElecLicenseMainMapper;
import com.zfsoft.batchimport.service.CommonService;
import com.zfsoft.batchimport.service.ElecLicenseMainService;
import com.zfsoft.batchimport.service.ElecLicenseMainTableService;
import com.zfsoft.batchimport.utils.BusinessUtil;
import com.zfsoft.batchimport.utils.CountNumberOfDaysAYear;
import com.zfsoft.batchimport.utils.DateUtil;
import com.zfsoft.batchimport.utils.enm.ParamType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * @author: kkfan
 * @create: 2020-03-10 13:27:23
 * @description: 数据导入服务层
 */
@Service
@RestControllerAdvice
public class CommonServiceImpl implements CommonService {

    private static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Autowired
    private CommonMapper commonMapper;

    @Autowired
    private ElecLicenseMainMapper elecLicenseMainMapper;
    @Autowired
    private ElecLicenseMainService elecLicenseMainService;
    @Autowired
    private ElecLicenseLiceFileServiceImpl elecLicenseLiceFileService ;
    @Autowired
    private ElecLicenseMainTableService elecLicenseMainTableService;


    @Override
    @Async("asyncServiceExecutor")
    public Future<List<LinkedHashMap<Integer, String>>> saveList(List<CommonInsertDto> list, CommonCreateTableDto commonCreateTableDto, CountDownLatch countDownLatch) {
        Thread thread = Thread.currentThread();
        logger.info(MessageFormat.format("线程{0}开始执行！", thread.getName()));
        // 记录线程中入库失败数据
        List<LinkedHashMap<Integer, String>> errList = Lists.newArrayList();
        CopyOnWriteArrayList list1 = Lists.newCopyOnWriteArrayList(list);
        if(!CollectionUtils.isEmpty(list)) {
            list1.forEach(commonInsertDto ->{
                CommonInsertDto insertDto = (CommonInsertDto) commonInsertDto;
                ElecLicenseMain elecLicenseMain = new ElecLicenseMain();
                try {
                    //数据存放在主表中一一对应，其中包括有效期范围
                   //证照编号不能重复
                    if(StringUtils.isNotEmpty((CharSequence) insertDto.getFieldMap().get("license_code"))) {
                        //查询es中证照主表中的数据
                        if(this.elecLicenseMainService.viewMainByLicenseNumber( String.valueOf(insertDto.getFieldMap().get("license_code"))) > 0) {
                            throw new RuntimeException(MessageFormat.format("证照编号已存在于办理列表中，证照编号为：{0}",insertDto.getFieldMap().get("license_code")));
                        }
                        //查询es中证照签发表数据
                        if(this.elecLicenseMainService.viewSubedByLicenseNumber( String.valueOf(insertDto.getFieldMap().get("license_code"))) > 0) {
                            throw new RuntimeException(MessageFormat.format("证照编号存在于已签发列表中，证照编号为：{0}",insertDto.getFieldMap().get("license_code")));
                        }
                    }
                    elecLicenseMain.setLicenseNumber(insertDto.getFieldMap().get("license_code").toString());
                    elecLicenseMain.setHolder(insertDto.getFieldMap().get("license_name").toString());

                    //判断证照等级是不是A、B、C、D
                    elecLicenseMain.setElecLicenseLevel(insertDto.getFieldMap().get("license_grade")!=null?insertDto.getFieldMap().get("license_grade").toString():"");//证照等级
                   if(insertDto.getFieldMap().get("contact_number")!=null){
                       if((com.zfsoft.batchimport.utils.StringUtil.isMobile(insertDto.getFieldMap().get("contact_number").toString())||com.zfsoft.batchimport.utils.StringUtil.isTelPhone(insertDto.getFieldMap().get("contact_number").toString()))){
                           elecLicenseMain.setContactTel(insertDto.getFieldMap().get("contact_number").toString());//联系电话
                       }else{
                           throw new RuntimeException(MessageFormat.format("联系电话格式错误，证照编号为：{0}",insertDto.getFieldMap().get("license_code")));
                       }
                   }

                    if(StringUtils.isNotEmpty((CharSequence) insertDto.getFieldMap().get("id_card_number"))) {
                         //查询es中证照主表中的数据
                        if (elecLicenseMainService.countMainByIdentificateNumber(insertDto.getFieldMap().get("id_card_number").toString(), "null", commonCreateTableDto.getElecDirClaim().getElecDirOid()) > 0) {
                            throw new RuntimeException(MessageFormat.format("持证人证件号码存在于办理列表中，证照编号为：{0}",insertDto.getFieldMap().get("license_code")));
                        }
                        //查询es中证照签发表数据
                        else if (elecLicenseMainService.countMainByIdentificateNumber(insertDto.getFieldMap().get("id_card_number").toString(), commonCreateTableDto.getElecDirClaim().getElecDirOid()) > 0) {
                            throw new RuntimeException(MessageFormat.format("持证人证件号码证照编号存在于已签发列表中，证照编号为：{0}",insertDto.getFieldMap().get("license_code")));
                        }
                    }
                    if(insertDto.getFieldMap().get("certificate_type").toString().equals("公民身份号码")){
                       if(!com.zfsoft.batchimport.utils.StringUtil.isIdCard(insertDto.getFieldMap().get("id_card_number").toString())){
                           throw new RuntimeException(MessageFormat.format("公民身份号码格式错误，证照编号为：{0}",insertDto.getFieldMap().get("license_code")));
                       }else{
                            elecLicenseMain.setIdentificateNumber(insertDto.getFieldMap().get("id_card_number").toString());// 持证人证件号码
                        }
                    }else{
                        elecLicenseMain.setIdentificateNumber(insertDto.getFieldMap().get("id_card_number")!=null?insertDto.getFieldMap().get("id_card_number").toString():"");// 持证人证件号码
                    }
                    try {//有效期起始时间
                        elecLicenseMain.setValidityDateStart(DateUtil.date_sdf.parse(insertDto.getFieldMap().get("validity_begin").toString()));

                    } catch (ParseException e) {
                        throw new RuntimeException(MessageFormat.format("有效期起始时间不能为空并且日期格式为yyyy-MM-dd，证照编号为：{0}",insertDto.getFieldMap().get("license_code")));
                    }
                    //得到有效期
                    String timeOid="";
                    String  longEffect = BaseStaticParameter.LONG_EFFECT_YES; //是否长期 0是 1否
                    String longEffectStr = insertDto.getFieldMap().get("validity_range").toString();

                    if(longEffectStr.equals("自定义")){
                        if (insertDto.getFieldMap().get("validity_end")==null){
                            throw new RuntimeException(MessageFormat.format("有效期范围为自定义时，有效期止不能为空，证照编号为：{0}",insertDto.getFieldMap().get("license_code")));
                        }
                    }

                    List<ElecLicenseDirTypeTime> elecLicenseDirTypeTimeDtoList = commonCreateTableDto.getElecLicenseDirTypeTimeList();
                    ElecLicenseDirTypeTime elecLicenseDirTypeTime = new ElecLicenseDirTypeTime();
                    String type = "";
                    if(!CollectionUtils.isEmpty(elecLicenseDirTypeTimeDtoList)) {
                        for(ElecLicenseDirTypeTime elecLicenseDirTypeTimeDto :elecLicenseDirTypeTimeDtoList){
                            if(BaseStaticParameter.ELECLICENCE_TYPE_TIME_3.equals(elecLicenseDirTypeTimeDto.getTimeType())){
                                type=elecLicenseDirTypeTimeDto.getTimeValue()+"日";
                            }else if(BaseStaticParameter.ELECLICENCE_TYPE_TIME_2.equals(elecLicenseDirTypeTimeDto.getTimeType())){
                                type = (elecLicenseDirTypeTimeDto.getTimeValue()+"月");
                            }else if(BaseStaticParameter.ELECLICENCE_TYPE_TIME_1.equals(elecLicenseDirTypeTimeDto.getTimeType())){
                                type = (elecLicenseDirTypeTimeDto.getTimeValue()+"年");
                            }else if(BaseStaticParameter.ELECLICENCE_TYPE_TIME_4.equals(elecLicenseDirTypeTimeDto.getTimeType())){
                                type = ("长期");
                            } else if(BaseStaticParameter.ELECLICENCE_TYPE_TIME_5.equals(elecLicenseDirTypeTimeDto.getTimeType())){
                                type = ("自定义");
                            }
                            if(type.equals(longEffectStr)){
                                if(longEffectStr.equals("长期")){
                                    longEffect= ("0");
                                }else{
                                    longEffect= ("1");
                                }
                                timeOid= elecLicenseDirTypeTimeDto.getTimeOid();
                                elecLicenseDirTypeTime = elecLicenseDirTypeTimeDto;
                                break;
                            }
                        }
                    }
                    if(timeOid.length() >0) {
                        elecLicenseMain.setLongEffect(longEffect);//有效期标识
                        if (elecLicenseDirTypeTime != null) {
                            if (elecLicenseDirTypeTime.getTimeType().equals(BaseStaticParameter.ELECLICENCE_TYPE_TIME_4)) {
                                elecLicenseMain.setAutoCancelFlag(BaseStaticParameter.AUTO_CANCEL_NO);
                                elecLicenseMain.setAutoAnnualFlag(BaseStaticParameter.AUTO_ANNUAL_NO);
                            } else if (elecLicenseDirTypeTime.getTimeType().equals(BaseStaticParameter.ELECLICENCE_TYPE_TIME_5)) {
                                if (insertDto.getFieldMap().get("validity_end")!=null) {
                                    try {
                                        SimpleDateFormat sfm = new SimpleDateFormat("yyyy-MM-dd");
                                        Date date = sfm.parse(insertDto.getFieldMap().get("validity_end").toString());
                                        elecLicenseMain.setValidityDateEnd(date);
                                    } catch (ParseException e) {
                                        throw new RuntimeException(MessageFormat.format("有效期为自定义状态下，有效期止不能为空并且日期格式为yyyy-MM-dd，证照编号为：{0}", insertDto.getFieldMap().get("license_code")));
                                    }
                                } else {
                                    CountNumberOfDaysAYear CNODAY = new CountNumberOfDaysAYear();
                                    try {
                                        Date endDate = CNODAY.addDate(elecLicenseMain.getValidityDateStart(),
                                                elecLicenseDirTypeTime.getTimeValue(),
                                                elecLicenseDirTypeTime.getTimeType());
                                        if (null != endDate) {
                                            elecLicenseMain.setValidityDateEnd(endDate);
                                        }
                                    } catch (Exception e) {
                                        throw new RuntimeException(MessageFormat.format("有效期为自定义状态下，有效期止不能为空并且日期格式为yyyy-MM-dd，证照编号为：{0}", insertDto.getFieldMap().get("license_code")));
                                    }
                                }
                            }
                        } else {
                            throw new RuntimeException(MessageFormat.format("有效期范围出错，证照编号为：{0}", insertDto.getFieldMap().get("validity_range")));
                        }

                        if (longEffect.equals(BaseStaticParameter.LONG_EFFECT_NO)) {//非长期有效，判断年检和注销
                            // 验证是否需要年检
                            String autoAnnual = "";
                            String autoAnnualFlag = "";
                            if (insertDto.getFieldMap().get("need_annual_inspection")!=null) {
                                autoAnnual = insertDto.getFieldMap().get("need_annual_inspection").toString();//是否需要年检
                                if ("需要".equals(autoAnnual)) {
                                    autoAnnualFlag = BaseStaticParameter.AUTO_ANNUAL_YES;
                                    elecLicenseMain.setAutoAnnualFlag(autoAnnualFlag);

                                    if (insertDto.getFieldMap().get("annual_inspection_begin_days")==null) {
                                        throw new RuntimeException(MessageFormat.format("提前年检天数不能为空，证照编号为：{0}", insertDto.getFieldMap().get("validity_range")));
                                    }
                                    String autoAnnualDays = insertDto.getFieldMap().get("annual_inspection_begin_days").toString();//提前年检天数
                                    if (DateUtil.isNumeric(autoAnnualDays)) {
                                        elecLicenseMain.setAutoAnnualDate(autoAnnualDays);
                                    } else {
                                        throw new RuntimeException(MessageFormat.format("提前年检天数出错,只能填写数字，证照编号为：{0}", insertDto.getFieldMap().get("validity_range")));
                                    }
                                } else if ("不需要".equals(autoAnnual)) {
                                    autoAnnualFlag = BaseStaticParameter.AUTO_ANNUAL_NO;
                                    elecLicenseMain.setAutoAnnualFlag(autoAnnualFlag);
                                } else {
                                    throw new RuntimeException(MessageFormat.format("是否需要年检不符合规范,只能填写需要和不需要，证照编号为：{0}", insertDto.getFieldMap().get("validity_range")));
                                }
                            } else {
                                throw new RuntimeException(MessageFormat.format("是否需要年检不能为空,证照编号为：{0}", insertDto.getFieldMap().get("license_code")));
                            }
                            // 验证超期是否需要注销
                            String autoCancel = insertDto.getFieldMap().get("expire_auto").toString();//超期是否需要注销
                            String autoCancelFlag = "";
                            if (!StringUtil.isEmpty(autoCancel)) {
                                if ("需要".equals(autoCancel)) {
                                    autoCancelFlag = BaseStaticParameter.AUTO_CANCEL_YES;
                                    elecLicenseMain.setAutoCancelFlag(autoCancelFlag);

                                    if (insertDto.getFieldMap().get("expire_automatically_days")==null) {
                                        throw new RuntimeException(MessageFormat.format("提前年检天数不能为空，证照编号为：{0}", insertDto.getFieldMap().get("validity_range")));
                                    }
                                    String autoCancelDays = insertDto.getFieldMap().get("expire_automatically_days").toString();//超期自动注销天数
                                    if (!StringUtil.isEmpty(autoCancelDays) && DateUtil.isNumeric(autoCancelDays)) {
                                        elecLicenseMain.setAutoCancelDate(autoCancelDays);
                                    } else {
                                        throw new RuntimeException(MessageFormat.format("超期自动注销天数出错,不能为空且只能填写数字，证照编号为：{0}", insertDto.getFieldMap().get("expire_automatically_days")));
                                    }
                                } else if ("不需要".equals(autoCancel)) {
                                    autoCancelFlag = BaseStaticParameter.AUTO_CANCEL_NO;
                                    elecLicenseMain.setAutoCancelFlag(autoCancelFlag);
                                } else {
                                    throw new RuntimeException(MessageFormat.format("超期自动注销不符合规范,只能填写需要和不需要，证照编号为：{0}", insertDto.getFieldMap().get("expire_automatically_days")));
                                }
                            } else {
                                throw new RuntimeException(MessageFormat.format("超期自动注销不能为空,证照编号为：{0}", insertDto.getFieldMap().get("license_code")));
                            }
                        }
                            elecLicenseMain.setWaterMark(insertDto.getFieldMap().get("watermark_content")!=null?insertDto.getFieldMap().get("watermark_content").toString():"");// 水印内容

                            //保存照面元素信息
                            List<CommonTableFieldDto> commonTableFieldDtoList = commonCreateTableDto.getCommonTableFieldDtoList();
                            List<TableParamDto> tableParamDtoList = new ArrayList<>();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            for (CommonTableFieldDto commonTableFieldDto : commonTableFieldDtoList) {
//                                String fieldCode = commonTableFieldDto.getFieldCode();// 字段名
                                String excelValue = insertDto.getFieldMap().get(commonTableFieldDto.getFieldCode()) == null ? null : insertDto.getFieldMap().get(commonTableFieldDto.getFieldCode()).toString();// excel中的值
                                if (commonTableFieldDto.getFieldType().toLowerCase().contains("date")) {
                                    if (!StringUtil.isEmpty(excelValue)) {

                                        SimpleDateFormat sfm = new SimpleDateFormat("yyyy-MM-dd");
                                        Date date = sfm.parse(excelValue);
                                        excelValue = sfm.format(date);

                                       /* Object[] obj1 = DateUtil.checkInstanceofDate(commonTableFieldDto.getFieldName(), excelValue, "n", "yyyy-MM-dd");
                                        if (StringUtil.isNotEmpty((String) obj1[1])) {// 是时间格式
                                            Date metadataVal1 = (Date) obj1[1];
                                            String DateStart3 = DateUtil.date_sdf.format(metadataVal1);
                                            metadataVal1 = DateUtil.str2Date(DateStart3);
                                            excelValue = sdf.format(metadataVal1);
                                        } else {
                                            throw new RuntimeException(MessageFormat.format(commonTableFieldDto.getFieldName() + "出错,日期格式不正确，证照编号为：{0}", fieldCode));
                                        }*/
                                    }
                                }
                                ParamType paramType = commonTableFieldDto.getFieldType().equals("file") ? ParamType.FILE : ( commonTableFieldDto.getFieldType().equals("date") ? ParamType.DATE : ParamType.STRING);
                                TableParamDto TableParamDto = new TableParamDto(commonTableFieldDto.getFieldCode(),commonTableFieldDto.getFieldName(),paramType,excelValue);
                                tableParamDtoList.add(TableParamDto);
                            }
                            String tableParamOid = UUID.randomUUID().toString().replaceAll("-", "");
                            //此处像T_ELEC_LICENSE_TABLE中存储证照元素信息
                            String tableMessage = elecLicenseMainTableService.saveOrUpdateMetadataTable(tableParamOid,tableParamDtoList);
                            if (!StringUtil.isEmpty(tableMessage)) {
                                throw new RuntimeException(MessageFormat.format(tableMessage + "证照编号为：{0}", elecLicenseMain.getLicenseNumber()));
                            }
                            elecLicenseMain.setTableParamOid( tableParamOid );//在证照主表中保存照面元素表主键信息
                            //标识的组成部分从左至右依次为：电子证照OID、证照名称代码、流水号、版本号和校验位，各项之间用短横线“-”分隔。
                            //电子证照OID固定为：1.2.156.3003.2。
                            if (StringUtil.isEmpty(elecLicenseMain.getLicenseVersion())) {
                                elecLicenseMain.setLicenseVersion(BusinessUtil.createVersionNumber(null));
                            }
                            if (StringUtil.isEmpty(elecLicenseMain.getElecLicenseUnique())) {
                                //电子证照代码
                                String licenseTypeCode = commonCreateTableDto.getLicenseTypeCode();
                                //流水号
                                String flowNumber = BusinessUtil.getFlowNumber();
                                //版本号
                                String licenseVersion = elecLicenseMain.getLicenseVersion();
                                licenseVersion=licenseVersion.substring(licenseVersion.length()-2, licenseVersion.length());
                                String elecLicenseCheck= BusinessUtil.ELECLICENSE_OID+"."+licenseTypeCode+"."+flowNumber+"."+licenseVersion;
                                String checkCode= BusinessUtil.getCertificateIdentifier(elecLicenseCheck.replace(".", ""), BusinessUtil.Designation.ISO_7064_MOD_37_HYBRID_36);
                                String elecLicenseUnique=elecLicenseCheck+"."+checkCode.substring(checkCode.length()-1, checkCode.length());
                                elecLicenseMain.setElecLicenseUnique(elecLicenseUnique);
                            }
                            elecLicenseMain.setDirectoryOid(commonCreateTableDto.getElecDirClaim().getElecDirOid());
                            elecLicenseMain.setClaimOid(commonCreateTableDto.getElecDirClaim().getOid());
                            elecLicenseMain.setLicenseStatus("0");
                            elecLicenseMain.setHolderType(commonCreateTableDto.getDirectoryObj());//持证者
                            elecLicenseMain.setHolderCode(BaseStaticParameter.HOLDER_CODE_MAP.get(insertDto.getFieldMap().get("certificate_type").toString()));
                            if(StringUtil.isEmpty(elecLicenseMain.getHolderCode())){
                                throw new RuntimeException(MessageFormat.format("持有者代码类型异常，证照编号为：{0}", elecLicenseMain.getLicenseNumber()));
                            }

                            elecLicenseMain.setTableParamOid(tableParamOid);
                            elecLicenseMain.setTimeOid(timeOid);

                            String elecLicenOid = UUID.randomUUID().toString().replaceAll("-", "");
                            elecLicenseMain.setElecLicenOid(elecLicenOid);
                            //发送es保存主表信息
                            String message= "";
                            //生成文件信息ofd，包含文件表入库
                            message = elecLicenseLiceFileService.createElecLicenseMainFileLice(elecLicenseMain
                                    ,commonCreateTableDto,tableParamOid) ;
                            if(StringUtil.isNotEmpty(message)){
                                throw new RuntimeException(MessageFormat.format(message+"，证照编号为：{0}", elecLicenseMain.getLicenseNumber()));
                            }

                            //此处保存证照入库
                            elecLicenseMainMapper.insert(elecLicenseMain);

                        }

                } catch (Exception e) {
                    LinkedHashMap<Integer, String> map = Maps.newLinkedHashMap();
                    map.put(0, insertDto.getRowNum());
                    LinkedHashMap<String, Object> fieldMap = insertDto.getFieldMap();
                    if(fieldMap != null && fieldMap.size() > 0) {
                        fieldMap.forEach((k, v) -> {
                            map.put(map.size(), String.valueOf(v));
                        });
                    };
                    map.put(map.size(), e.toString());
                    errList.add(map);
                }

            });

        }
        countDownLatch.countDown();
        logger.info(MessageFormat.format("线程{0}执行结束！", thread.getName()));
        return new AsyncResult<>(errList);
    }


    public List<HashMap>  getSysConfigList(){
        return commonMapper.getSysConfigList();
    }
}
