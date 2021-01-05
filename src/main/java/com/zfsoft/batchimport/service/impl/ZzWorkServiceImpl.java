package com.zfsoft.batchimport.service.impl;

import cn.hutool.core.util.IdcardUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.fsp.shield.java.sdk.model.ApiResponse;
import com.zfsoft.batchimport.common.BaseStaticParameter;
import com.zfsoft.batchimport.domain.dto.ElecLicenseMainDataSubed;
import com.zfsoft.batchimport.domain.entity.ElecLicenseSbSubed;
import com.zfsoft.batchimport.mapper.middle.ElecLicenseSbSubedMapper;
import com.zfsoft.batchimport.service.IZzWorkService;
import com.zfsoft.batchimport.utils.HttpClient;
import com.zfsoft.batchimport.utils.IflytekUtils;
import com.zfsoft.batchimport.utils.Result;
import com.zfsoft.batchimport.utils.StringUtil;
import com.zfsoft.batchimport.utils.sys.FileInterfaceUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "zzWorkService")
public class ZzWorkServiceImpl implements IZzWorkService {

    /**
     * es配置路径
     */
    @Value("${elasticsearch-api.universalSave}")
    private String universalSave;

    @Autowired
    private ElecLicenseSbSubedMapper elecLicenseSbSubedMapper;


    private static Logger log = LoggerFactory.getLogger(FileInterfaceUtil.class);

    @Override
    public String suoYinShangBao() throws Exception {
        IflytekUtils iflytekUtils = new IflytekUtils();
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        String result = new String();
        List<Integer> listNum = elecLicenseSbSubedMapper.getSubNumTimes();
        for (Integer i : listNum) {
            //逻辑懵 (当于上面查询未上报的排序号，然后同一个排序号下数据全部再上报一次(含已上报))
            List<ElecLicenseSbSubed> list = elecLicenseSbSubedMapper.getElecLicenseSbSubedList(i);
            for (ElecLicenseSbSubed elecLicenseSbSubed : list) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                jsonMap.put("ownerId", elecLicenseSbSubed.getOwnerId());
                jsonMap.put("contentName", elecLicenseSbSubed.getContentName());
                jsonMap.put("infoCode", elecLicenseSbSubed.getInfoCode());
                jsonMap.put("code", elecLicenseSbSubed.getLshCode());
                jsonMap.put("ownerName", elecLicenseSbSubed.getOwnerName());
                jsonMap.put("areaCode", "340800");
                jsonMap.put("version", elecLicenseSbSubed.getVersion());
                jsonMap.put("contentCode", elecLicenseSbSubed.getContentCode());
                jsonMap.put("operateType", elecLicenseSbSubed.getOpType());
                jsonMap.put("systemCode", "aqzzk");
                jsonMap.put("bizId", elecLicenseSbSubed.getBizId());
                jsonMap.put("validBegin", elecLicenseSbSubed.getValidBegin() == null ? null : sdf.format(elecLicenseSbSubed.getValidBegin()));
                jsonMap.put("validEnd", elecLicenseSbSubed.getValidEnd() == null ? null : sdf.format(elecLicenseSbSubed.getValidEnd()));
                jsonMap.put("dataSource", "2");
                jsonMap.put("makeTime", elecLicenseSbSubed.getCreateTime() == null ? null : sdf.format(elecLicenseSbSubed.getCreateTime()));
                jsonMap.put("createTime", sdf.format(new Date()));
                jsonMap.put("manyToOne", "");
                jsonMap.put("ownerIds", "");
                jsonMap.put("ownerNames", "");
                jsonMap.put("contentMd5", elecLicenseSbSubed.getContentMD5());
                // 2020-11-28新增 证件类型(证件类型infoTypeCode：个人（满足18位）111或999，法人（满足18位）001或099；signCount：印章数量)
                jsonMap.put("infoTypeCode", judgeCertificateType(elecLicenseSbSubed.getDirectoryObj(),elecLicenseSbSubed.getOwnerId()));
                // 印章数量	非必填，默认0
                jsonMap.put("signCount", "0");
                String jsonStr = JSON.toJSONString(jsonMap);
                System.out.println("第" + i + "个序列：" + jsonStr);
                //索引上报
                ApiResponse apu = iflytekUtils.key_WEIHU(jsonStr.getBytes("UTF-8"));
                result = new String(apu.getBody(), "UTF-8");
                elecLicenseSbSubedMapper.updateElecLicenseSbSubed(elecLicenseSbSubed.getOid());
            }
        }

        return result;
    }

    /**
     * 将数据上报到省里删除
     *
     * @param elecLicenseMainDataSubed
     * @return
     * @throws Exception
     * @author chenyq
     * @date 20200901
     */
    public String shanchuShangBao(ElecLicenseMainDataSubed elecLicenseMainDataSubed) throws Exception {

        IflytekUtils iflytekUtils = new IflytekUtils();
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jsonMap.put("ownerId", elecLicenseMainDataSubed.getIdentificateNumber());
        jsonMap.put("contentName", elecLicenseMainDataSubed.getDirectoryName());
        jsonMap.put("infoCode", elecLicenseMainDataSubed.getLicenseNumber());
        jsonMap.put("code", elecLicenseMainDataSubed.getElecLicenseUnique());
        jsonMap.put("ownerName", elecLicenseMainDataSubed.getHolder());
        jsonMap.put("areaCode", "340800");
        jsonMap.put("version", elecLicenseMainDataSubed.getVersion());
        jsonMap.put("contentCode", elecLicenseMainDataSubed.getContentCode());
        jsonMap.put("operateType", "D");
        jsonMap.put("systemCode", "aqzzk");
        jsonMap.put("bizId", elecLicenseMainDataSubed.getElecLicenOid());
        jsonMap.put("validBegin", elecLicenseMainDataSubed.getValidityDateStart() == null ? null : sdf.format(elecLicenseMainDataSubed.getValidityDateStart()));
        jsonMap.put("validEnd", elecLicenseMainDataSubed.getValidityDateEnd() == null ? null : sdf.format(elecLicenseMainDataSubed.getValidityDateEnd()));
        jsonMap.put("dataSource", "2");
        jsonMap.put("makeTime", elecLicenseMainDataSubed.getCreateDate() == null ? null : sdf.format(elecLicenseMainDataSubed.getCreateDate()));
        jsonMap.put("createTime", sdf.format(new Date()));
        jsonMap.put("manyToOne", "");
        jsonMap.put("ownerIds", "");
        jsonMap.put("ownerNames", "");
        jsonMap.put("contentMd5", DigestUtils.md5Hex(elecLicenseMainDataSubed.getTableParamJson()));
        // 2020-11-28新增 证件类型(证件类型infoTypeCode：个人（满足18位）111或999，法人（满足18位）001或099；signCount：印章数量)
        jsonMap.put("infoTypeCode", judgeCertificateType(elecLicenseMainDataSubed.getDirectoryObj(), elecLicenseMainDataSubed.getIdentificateNumber()));
        // 印章数量	非必填，默认0
        jsonMap.put("signCount", "0");
        String jsonStr = JSON.toJSONString(jsonMap);
        System.out.println(jsonStr);
        //索引上报
        ApiResponse apu = iflytekUtils.key_WEIHU(jsonStr.getBytes("UTF-8"));
        String result = new String(apu.getBody(), "UTF-8");
        System.out.println(result);
        return result;

    }

    @Override
    @Async("asyncServiceExecutor")
    public void doHistoryElms(List<ElecLicenseMainDataSubed> list) throws Exception {
        for (ElecLicenseMainDataSubed elecLicenseMainDataSubed : list) {
            String result = this.shanchuShangBao(elecLicenseMainDataSubed);
            JSONObject respJson = JSONObject.parseObject(result);
            if ("200".equals(respJson.getString("flag")) || respJson.getString("result").contains("索引不存在")) {
                elecLicenseMainDataSubed.setOperation("7");
                this.saveOrUpdElecLicenseMainDataSubed(elecLicenseMainDataSubed);
            }
        }
    }

    /**
     * 查询历史数据是否需要删除
     *
     * @return
     * @author chenyq
     * @date 2020/8/29
     */

    @Override
    public List queryHistoryMainDataSubed() throws Exception {
        String value = HttpClient.post(universalSave + "elasticsearch/queryHistoryMainDataSubed", "");
        return JSONObject.parseArray(value, ElecLicenseMainDataSubed.class);
    }


    /**
     * @param elecLicenseMainDataSubed
     * @return
     * @throws Exception
     * @desc: 新增或者修改
     * @author: 郑家祥
     * @date: 2020/3/27
     */
    public Map<String, Object> saveOrUpdElecLicenseMainDataSubed(ElecLicenseMainDataSubed elecLicenseMainDataSubed) throws Exception {
        Map<String, Object> map = new HashMap(4);

        String apiUrl = "elasticsearch/elecLicenseMainDataSubed/";
        String elecLicenseMainDataSubedJSON = JSON.toJSONString(elecLicenseMainDataSubed);
        String value = HttpClient.post(universalSave + apiUrl + elecLicenseMainDataSubed.getOid(), elecLicenseMainDataSubedJSON);
        Result result = JSONObject.parseObject(value, Result.class);
        if (result.isFlag()) {
            elecLicenseMainDataSubed = JSON.toJavaObject((JSON) result.getData(), ElecLicenseMainDataSubed.class);
            map.put("resultFlag", true);
            map.put("resultData", elecLicenseMainDataSubed);
            return map;
        }
        map.put("resultFlag", false);
        return map;
    }

    /**
     * 根据当前项目的证件类型和证件号判断索引上报入参的infoTypeCode值
     * 证件类型infoTypeCode：个人（满足18位）111或999，法人（满足18位）011或099
     *
     * @param directoryObj
     * @param ownerId
     * @return
     */
    public String judgeCertificateType(String directoryObj, String ownerId) {

        if (StringUtil.isNotEmpty(directoryObj)) {
            if (BaseStaticParameter.HOLDER_TYPE_PERSON.equals(directoryObj)) {
                // 自然人
                if (ownerId.length() == 18) {
                    return BaseStaticParameter.HOLDER_TYPE_ZIRANREN_SFZ;
                } else {
                    return BaseStaticParameter.HOLDER_TYPE_ZIRANREN_SFZ_OTHER;
                }
            } else if (BaseStaticParameter.HOLDER_TYPE_LEGAR_PERSONAL.equals(directoryObj)) {
                // 法人
                if (ownerId.length() == 18) {
                    return BaseStaticParameter.HOLDER_TYPE_FAREN_XYDM;
                } else {
                    return BaseStaticParameter.HOLDER_TYPE_FAREN_XYDM_OTHER;
                }
            } else {
                // 混合或者其它类型
                // 判断ownerId是否是身份证号
                if (StringUtil.isNotEmpty(ownerId)) {
                    if (IdcardUtil.isValidCard(ownerId)) {
                        // 自然人
                        if (ownerId.length() == 18) {
                            return BaseStaticParameter.HOLDER_TYPE_ZIRANREN_SFZ;
                        } else {
                            return BaseStaticParameter.HOLDER_TYPE_ZIRANREN_SFZ_OTHER;
                        }
                    } else {
                        // 法人
                        if (ownerId.length() == 18) {
                            return BaseStaticParameter.HOLDER_TYPE_FAREN_XYDM;
                        } else {
                            return BaseStaticParameter.HOLDER_TYPE_FAREN_XYDM_OTHER;
                        }
                    }
                } else {
                    // 默认返回个人其它类型
                    return BaseStaticParameter.HOLDER_TYPE_ZIRANREN_SFZ_OTHER;
                }
            }
        } else {
            // 默认返回个人其它类型
            return BaseStaticParameter.HOLDER_TYPE_ZIRANREN_SFZ_OTHER;
        }

    }

    /**
     * 根据当前项目的证件号判断索引上报入参的infoTypeCode值
     * 证件类型infoTypeCode：个人（满足18位）111或999，法人（满足18位）011或099
     *
     * @param ownerId
     * @return
     */
    public String judgeCertificateTypeByOwnerId(String ownerId) {

        if (StringUtil.isNotEmpty(ownerId)) {
            if (IdcardUtil.isValidCard(ownerId)) {
                // 自然人
                if (ownerId.length() == 18) {
                    return BaseStaticParameter.HOLDER_TYPE_ZIRANREN_SFZ;
                } else {
                    return BaseStaticParameter.HOLDER_TYPE_ZIRANREN_SFZ_OTHER;
                }
            } else {
                // 法人
                if (ownerId.length() == 18) {
                    return BaseStaticParameter.HOLDER_TYPE_FAREN_XYDM;
                } else {
                    return BaseStaticParameter.HOLDER_TYPE_FAREN_XYDM_OTHER;
                }
            }
        } else {
            // 默认返回个人其它类型
            return BaseStaticParameter.HOLDER_TYPE_ZIRANREN_SFZ_OTHER;
        }

    }

}
