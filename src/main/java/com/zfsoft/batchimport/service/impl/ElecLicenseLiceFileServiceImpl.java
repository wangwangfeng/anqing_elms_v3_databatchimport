package com.zfsoft.batchimport.service.impl;


import com.alibaba.excel.util.CollectionUtils;
import com.google.gson.Gson;
import com.zfsoft.batchimport.mapper.elms.*;
import com.zfsoft.batchimport.utils.AbstractFileTool;
import com.zfsoft.batchimport.utils.MatrixToImageWriter;
import com.zfsoft.batchimport.common.BaseStaticParameter;
import com.zfsoft.batchimport.domain.dto.*;
import com.zfsoft.batchimport.domain.entity.*;
import com.zfsoft.batchimport.service.ElecLicenseLiceFileService;
import com.zfsoft.batchimport.service.ElecLicenseMainService;
import com.zfsoft.batchimport.utils.DateUtil;
import com.zfsoft.batchimport.utils.suwell.HTTPAgentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ElecLicenseLiceFileServiceImpl implements ElecLicenseLiceFileService {

    @Autowired
    private ElecLicenseLiceFileMapper elecLicenseLiceFileMapper;
    @Autowired
    private ElecLicenseMainMapper elecLicenseMainMapper;
    @Autowired
    private SysAttaMapper sysAttaMapper;
    @Autowired
    private ElecQRCodeMapper elecQRCodeMapper;
    @Autowired
    private ElecLicenseMainTableServiceImpl elecLicenseMainTableService;

    @Autowired
    private ElecLicenseMainService elecLicenseMainService;
    /**
     * 二维码配置信息表 mapper
     */
    @Autowired
    private QRcodeConfigServiceImpl qRcodeConfigService;


    @Autowired
    private CommonMapper commonMapper;

    public String createElecLicenseMainFileLice(ElecLicenseMain elecLicenseMain, CommonCreateTableDto commonCreateTableDto, String tableOid)throws Exception {

        InputStream[] xmlPaths = null;
        InputStream[] xmlBaomiPaths = null;

        if (elecLicenseMain == null)
            return "证照信息为空";

        List<Template> templateDtoList = commonCreateTableDto.getTemplateList();
        List<CommonTableFieldDto> commonTableFieldDtoList = commonCreateTableDto.getCommonTableFieldDtoList();

        Boolean QRCodeFlag = false;
        Boolean comfidentialFlag = false;
        if (!CollectionUtils.isEmpty(commonTableFieldDtoList)){
            for (CommonTableFieldDto commonTableFieldDto : commonTableFieldDtoList) {
                String qrField = BaseStaticParameter.QRCode_Field;
                String[] qrFieldArray = qrField.split(",");
                for(String qrf : qrFieldArray){
                    if (StringUtil.isNotEmpty(commonTableFieldDto.getFieldCode()) &&commonTableFieldDto.getFieldCode().equals(qrf)&&commonTableFieldDto.getFieldName().contains("二维码")) {
                        QRCodeFlag = true;
                        break;
                    }
                }

                if (StringUtil.isNotEmpty(commonTableFieldDto.getComfidentialFlag()) &&commonTableFieldDto.getComfidentialFlag().equals(BaseStaticParameter.COMFIDENTIAL_FLAG_N)) {//保密
                    comfidentialFlag = true;
                }
            }
        }


        // 照面元素list
        Map<String, String> nameValMap = elecLicenseMainTableService.getMetadataMap(tableOid);

        String elecQRCodeSavePath = "";
        if(QRCodeFlag){
            //二维码start
            Map<String, String> qrCode = splicingQRCode(elecLicenseMain, nameValMap);
            elecQRCodeSavePath = qrCode.get("elecQRCodeSavePath");
            //二维码end
        }

        SysAtta[] ofdPathAttas = new SysAtta[templateDtoList.size()];
        xmlPaths = new InputStream[templateDtoList.size()];
        xmlBaomiPaths = new InputStream[templateDtoList.size()];
        int version = 0;
        for (int i = 0; i < templateDtoList.size(); i++) {

            Template template =  templateDtoList.get(i);
            String attaOid = template.getAttaOid();
            ofdPathAttas[i] = sysAttaMapper.selectByPrimaryKey(attaOid);
            xmlPaths[i] =  HTTPAgentUtil.toOfdXmlExcelInputStream(commonTableFieldDtoList, nameValMap , elecQRCodeSavePath);
            if (null != comfidentialFlag) {
                xmlBaomiPaths[i] =  HTTPAgentUtil.toWebOfdXmlBaomiExcelInputStream(commonTableFieldDtoList, nameValMap );
            }
        }


        if (templateDtoList == null || templateDtoList.size() == 0) {
            return "证照文件正在生成，请稍后再试！";
        }

        String ofdName = System.currentTimeMillis()+ new Random().nextInt(10000) + ".ofd";

        String relSavePath = BaseStaticParameter.ELEC_LICENSE_LICE_PATH
                + DateUtil.getNowDate("yyyy/MM/dd") + "/"
                + elecLicenseMain.getDirectoryOid() + "/"
                + elecLicenseMain.getElecLicenOid() + "/";
        Map<String, String> ret = HTTPAgentUtil.convertAse(ofdPathAttas, xmlPaths,  relSavePath,
                ofdName, null, elecLicenseMain.getWaterMark());//此处生成加密ofd
        Map<String, String> retBaomi = new HashMap<>();
        if (comfidentialFlag) {
            // _0_0 未盖章 未加密

            String ofdNameBaomi = System.currentTimeMillis()+ new Random().nextInt(10000) + "_mask.ofd";
            retBaomi = HTTPAgentUtil.convertAseForWeb(ofdPathAttas, xmlBaomiPaths,  relSavePath,
                    ofdNameBaomi, null, elecLicenseMain.getWaterMark());//此处生成加密ofd
        }
        if(ret != null){
            String suwellFlag = ret.get("suwellFlag");
            if(suwellFlag.equals("false")){
                return "数科服务异常！";
            }
            //初始化数据
            ElecLicenseMainData elecLicenseMainData = new ElecLicenseMainData(elecLicenseMain,commonCreateTableDto);
            String attaOid = ret.get("attaOid");String maskAttaOid ="";
            if(retBaomi != null) {
                maskAttaOid = retBaomi.get("attaOid");
            }
            elecLicenseMainData.setAttaOid(attaOid);
            elecLicenseMainData.setMaskAttaOid(maskAttaOid);
            Map message =  elecLicenseMainService.updElecLicenseMainDataByOid(elecLicenseMainData);
            if(message.get("resultFlag").equals(false)){
                return "es服务异常！";
            }

            ElecLicenseLiceFile lf = new ElecLicenseLiceFile();
            lf.setCreateDate(new Date());
            lf.setDelFlag(BaseStaticParameter.NO);
            lf.setElecLicenOid(elecLicenseMain.getElecLicenOid());
            lf.setEnableStatus(BaseStaticParameter.YES);
            lf.setTemplateOid(templateDtoList.get(0).getTemplateOid());
            lf.setVersions(0);
            lf.setAttaOid(attaOid);
            lf.setMaskAttaOid(maskAttaOid);
            lf.setVersions(0);
            this.elecLicenseLiceFileMapper.insertSelective(lf);
        }
        return null;
    }

    public String createElecLicenseMainFileLice(ElecLicenseMain elecLicenseMain, List<LicenseMetadata> licenseMetadataList, List<HashMap> templateDtoList, String tableOid)throws Exception {


        InputStream[] xmlPaths = null;
        InputStream[] xmlBaomiPaths = null;
        try {
            if (elecLicenseMain == null)
                return "未查询到证照信息";
        } catch (Exception e) {
            return "证照查询错误";
        }


        Boolean QRCodeFlag = false;
        Boolean comfidentialFlag = false;
        if (!CollectionUtils.isEmpty(licenseMetadataList)){
            for (LicenseMetadata licenseMetadata : licenseMetadataList) {
                String qrField = BaseStaticParameter.QRCode_Field;
                String[] qrFieldArray = qrField.split(",");
                for(String qrf : qrFieldArray){
                    if (StringUtil.isNotEmpty(licenseMetadata.getColumnName()) &&licenseMetadata.getColumnName().equals(qrf)&&licenseMetadata.getMetadataName().contains("二维码")) {
                        QRCodeFlag = true;
                        break;
                    }
                }
                if (licenseMetadata.getComfidentialFlag()!=null&& licenseMetadata.getComfidentialFlag().equals(BaseStaticParameter.COMFIDENTIAL_FLAG_N)) {//保密
                    comfidentialFlag = true;
                }
            }
        }

        // 照面元素list
        Map<String, String> nameValMap = elecLicenseMainTableService.getMetadataMap(tableOid);

        String elecQRCodeSavePath = "";
        String elecQRCodeFileName = "";
        if(QRCodeFlag){
            //二维码start
                Map<String, String> qrCode = splicingQRCode(elecLicenseMain, nameValMap);
                elecQRCodeSavePath = qrCode.get("elecQRCodeSavePath");
            //二维码end
        }


        if (templateDtoList == null || templateDtoList.size() == 0) {
            return "证照文件正在生成，请稍后再试！";
        }

        SysAtta[] ofdPathAttas = new SysAtta[templateDtoList.size()];
        xmlPaths = new InputStream[templateDtoList.size()];
        xmlBaomiPaths = new InputStream[templateDtoList.size()];
        int version = 0;
        for (int i = 0; i < templateDtoList.size(); i++) {

            InputStream ofdXmlInputStream =  HTTPAgentUtil.toOfdXmlInputStream(licenseMetadataList, nameValMap , elecQRCodeSavePath);

            InputStream  ofdXmlForMimiInputStream = null;
            HashMap templateMap =  templateDtoList.get(i);
            String attaOid = templateMap.get("ATTA_OID").toString();
            ofdPathAttas[i] = sysAttaMapper.selectByPrimaryKey(attaOid);
            xmlPaths[i] = ofdXmlInputStream;
            if(comfidentialFlag){//此处生成保密odfxml 判断是否存在保密字段
                ofdXmlForMimiInputStream = HTTPAgentUtil.toWebOfdXmlBaomiInputStream(licenseMetadataList, nameValMap);
            }
            if (null != ofdXmlForMimiInputStream) {
                xmlBaomiPaths[i] = ofdXmlForMimiInputStream;
            }
        }
        // _0_0 未盖章 未加密
       /* String ofdName = elecLicenseMain.getElecLicenOid() + "_0_1_" + version + "_"
                + System.currentTimeMillis() + ".ofd";*/
        String ofdName = System.currentTimeMillis()+ new Random().nextInt(10000) + ".ofd";

        String relSavePath = BaseStaticParameter.ELEC_LICENSE_LICE_PATH
                + DateUtil.getNowDate("yyyy/MM/dd") + "/"
                + elecLicenseMain.getDirectoryOid() + "/"
                + elecLicenseMain.getElecLicenOid() + "/";
        Map<String, String> ret = HTTPAgentUtil.convertAse(ofdPathAttas, xmlPaths,  relSavePath,
                ofdName, null, elecLicenseMain.getWaterMark());//此处生成加密ofd
        Map<String, String> retBaomi = new HashMap<>();
        if (comfidentialFlag) {
            // _0_0 未盖章 未加密
            /*String ofdNameBaomi = elecLicenseMain.getElecLicenOid() + "_0_1_" + version + "_"
                    + System.currentTimeMillis() + "_mask.ofd";*/
            String ofdNameBaomi = System.currentTimeMillis()+ new Random().nextInt(10000) + "_mask.ofd";
            retBaomi = HTTPAgentUtil.convertAseForWeb(ofdPathAttas, xmlBaomiPaths,  relSavePath,
                    ofdNameBaomi, null, elecLicenseMain.getWaterMark());//此处生成加密ofd
        }
        if(ret != null){
            ElecLicenseMainData elecLicenseMainData = elecLicenseMainService.getElecLicenseMainDataByOid(elecLicenseMain.getElecLicenOid());

            String attaOid = ret.get("attaOid");String maskAttaOid ="";
            if(retBaomi != null) {
                maskAttaOid = retBaomi.get("attaOid");
            }
            if(elecLicenseMainData!=null){
                elecLicenseMainData.setAttaOid(attaOid);
                elecLicenseMainData.setMaskAttaOid(maskAttaOid);
                elecLicenseMainService.updElecLicenseMainDataByOid(elecLicenseMainData);
            }


            ElecLicenseLiceFile lf = new ElecLicenseLiceFile();
            lf.setCreateDate(new Date());
            lf.setDelFlag(BaseStaticParameter.NO);
            lf.setElecLicenOid(elecLicenseMain.getElecLicenOid());
            lf.setEnableStatus(BaseStaticParameter.YES);
            lf.setTemplateOid(templateDtoList.get(0).get("TEMPLATE_OID").toString());
            lf.setVersions(0);
            lf.setAttaOid(attaOid);
            lf.setMaskAttaOid(maskAttaOid);
            lf.setVersions(0);
            this.elecLicenseLiceFileMapper.insertSelective(lf);
        }
        //保存证照主表信息
        elecLicenseMainMapper.insert(elecLicenseMain);
        return null;
    }

    /**
     * 二维码内容拼接
     * @author chenyq
     * @date 20201105
     * @param elecLicenseMain 证照信息
     * @return
     * @throws Exception
     */
    public Map<String,String> splicingQRCode( ElecLicenseMain elecLicenseMain,Map paramMap) throws Exception {
try{

        String elecLicenOid = elecLicenseMain.getElecLicenOid();
        String directoryOid = elecLicenseMain.getDirectoryOid();
        HashMap hashMapLicenseTypeOid = commonMapper.getLicenseTypeOidByDirMain(directoryOid);
        String licenseTypeOid = hashMapLicenseTypeOid.get("LICENCE_TYPE").toString();

        HashMap hashMap = commonMapper.getOrgan(elecLicenseMain.getClaimOid());

        String organOid = hashMap.get("OID").toString();
        //获取organName,organCode
        String organCode = hashMap.get("CODE").toString();
        String organName  = hashMap.get("NAME").toString();

        HashMap<Integer,Object> map = new HashMap<Integer,Object>();
        //获取照面元素信息
        //获取二维码配置照面元素信息
        List<QRcodeConfig> listZmys =  qRcodeConfigService.getQRcodeConfigByOrganAndDirectoryOid(licenseTypeOid,BaseStaticParameter.CONFIG_TYPE_ZMYS);
        if(listZmys.size()>0) {
            for (Object key : paramMap.keySet()) {
                for (QRcodeConfig qRcodeConfig : listZmys) {
                    if (StringUtil.isNotEmpty(qRcodeConfig.getConfigCode())) {
                        if (qRcodeConfig.getConfigCode().equals(key.toString())&& paramMap.get(key)!=null) {
                            qRcodeConfig.setConfigCode(qRcodeConfig.getConfigCode().replace(key.toString(), paramMap.get(key).toString()));
                        } else if (qRcodeConfig.getConfigCode().contains("[") &&
                                qRcodeConfig.getConfigCode().contains("]") &&
                                qRcodeConfig.getConfigCode().contains(key.toString())&& paramMap.get(key)!=null) {
                            //组合字段最外层必须使用 [year年month月day日] 括起来
                            // 后续在拼接内容时候去掉加上的 []
                            qRcodeConfig.setConfigCode(qRcodeConfig.getConfigCode().replace(key.toString(), paramMap.get(key).toString()));
                        }
                    }
                }
            }
            for (QRcodeConfig qRcodeConfig : listZmys) {
                map.put(qRcodeConfig.getPaixu(), qRcodeConfig.getConfigCode());
            }
        }

        //获取二维码配置基本信息
        List<QRcodeConfig> listJbxx =  qRcodeConfigService.getQRcodeConfigByOrganAndDirectoryOid(licenseTypeOid,BaseStaticParameter.CONFIG_TYPE_JBXX);


        if(listJbxx.size()>0){
            for(QRcodeConfig qRcodeConfig : listJbxx){
                if(qRcodeConfig.getConfigCode().equals("organName")){
                    map.put(qRcodeConfig.getPaixu(),organName);
                }
                if(qRcodeConfig.getConfigCode().equals("organCode")){
                    map.put(qRcodeConfig.getPaixu(),organCode);
                }
                if(qRcodeConfig.getConfigCode().equals("elecLicenseUnique")){
                    map.put(qRcodeConfig.getPaixu(),elecLicenseMain.getElecLicenseUnique());
                }
                if(qRcodeConfig.getConfigCode().equals("licenseNumber")){
                    map.put(qRcodeConfig.getPaixu(),elecLicenseMain.getLicenseNumber());
                }
                if(qRcodeConfig.getConfigCode().equals("holder")){
                    map.put(qRcodeConfig.getPaixu(),elecLicenseMain.getHolder());
                }
                if(qRcodeConfig.getConfigCode().equals("identificateNumber")){
                    map.put(qRcodeConfig.getPaixu(),elecLicenseMain.getIdentificateNumber());
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                if(qRcodeConfig.getConfigCode().equals("validityDateStart")){
                    map.put(qRcodeConfig.getPaixu(),sdf.format(elecLicenseMain.getValidityDateStart()));
                }
                if(qRcodeConfig.getConfigCode().equals("validityDateEnd")){
                    if(elecLicenseMain.getValidityDateEnd()==null){
                        map.put(qRcodeConfig.getPaixu(),"2099-12-31");
                    }else{
                        map.put(qRcodeConfig.getPaixu(),sdf.format(elecLicenseMain.getValidityDateEnd()));
                    }
                }
            }
        }

        //获取二维码配置定制化
        List<QRcodeConfig> listDzh =  qRcodeConfigService.getQRcodeConfigByOrganAndDirectoryOid(licenseTypeOid,BaseStaticParameter.CONFIG_TYPE_DZH);

        if(listDzh.size()>0 && StringUtil.isNotEmpty(elecLicenseMain.getRemarks())){
            Gson gson = new Gson();
            Map<String, Object> mapDzh = new HashMap<String, Object>();
            mapDzh = gson.fromJson(elecLicenseMain.getRemarks(), mapDzh.getClass());
            for(Object key :mapDzh.keySet()) {
                for(QRcodeConfig qRcodeConfig : listDzh){
                    if(qRcodeConfig.getConfigCode().equals(key)){
                        map.put(qRcodeConfig.getPaixu(),mapDzh.get(key));
                    }
                }
            }
        }



        String content = QRcodeContent(map);
        if(StringUtil.isEmpty(content)){
            Map<String,String> mapPath = new HashMap(1);
            mapPath.put("elecQRCodeSavePath",null);
            return mapPath;
        }
        String  codeUploadPath="upload" + "/"
                + DateUtil.getNowDate("yyyy/MM/dd") + "/" + elecLicenOid + "/";
        String elecQRCodeName=elecLicenOid+"_QRCode";
        SysAtta elecQRCodeAtta = new SysAtta();
        elecQRCodeAtta.setIsDelete(BaseStaticParameter.NO);
        elecQRCodeAtta.setExtensionName("jpg");
        elecQRCodeAtta.setFilePath(codeUploadPath);
        elecQRCodeAtta.setName(elecQRCodeName+".jpg");
        elecQRCodeAtta.setOriginName(elecQRCodeName+".jpg");
        elecQRCodeAtta.setUploadDate(new Date());
        elecQRCodeAtta.setUserOid(null);
        sysAttaMapper.insert(elecQRCodeAtta);

        ElecQRCode elecQRCode=new ElecQRCode();
        elecQRCode.setElecLicenOid(elecLicenseMain.getElecLicenOid());
        elecQRCode.setAttaOid(elecQRCodeAtta.getOid());
        elecQRCode.setImgPath(codeUploadPath+ "/" +elecQRCodeName+".jpg");
        elecQRCodeMapper.insert(elecQRCode);
        String fastdfsPath = MatrixToImageWriter.createQRcodeImage(content, codeUploadPath,elecQRCodeAtta.getOid(), elecQRCodeName, 100, 100);
        Map<String,String> mapPath = new HashMap(1);
        mapPath.put("elecQRCodeSavePath",fastdfsPath);
        return mapPath;
        }catch (Exception e){
            e.printStackTrace();
        }return null;
    }

    public String QRcodeContent(HashMap<Integer,Object> hashMap) {
        StringBuffer sbu = new StringBuffer();
        StringBuffer sbuAll = new StringBuffer();

        //这里将map.entrySet()转换成list
        List<Map.Entry<Integer,Object>> list = new ArrayList<Map.Entry<Integer,Object>>(hashMap.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list,new Comparator<Map.Entry<Integer,Object>>() {
            //升序排序
            public int compare(Map.Entry<Integer,Object> o1,
                               Map.Entry<Integer,Object> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }

        });

        for(Map.Entry<Integer,Object> mapping:list){
            sbu.append(mapping.getValue()).append("^");
        }
        if(sbu.toString().lastIndexOf("^")>0){
            sbuAll.append(sbu.substring(0,sbu.length()-1));
        }
        return sbuAll.toString();
    }

}