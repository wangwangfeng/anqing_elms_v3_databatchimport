package com.zfsoft.batchimport.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zfsoft.batchimport.service.ElecLicenseMainService;
import com.zfsoft.batchimport.domain.dto.ElecLicenseMainData;
import com.zfsoft.batchimport.domain.entity.ElecLicenseMain;
import com.zfsoft.batchimport.utils.HttpClient;
import com.zfsoft.batchimport.utils.Result;
import com.zfsoft.batchimport.utils.sys.FileInterfaceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenyq
 * @description
 * @date 2020-04-26
 * @Copyright
 */
@Service
public class ElecLicenseMainServiceImpl implements ElecLicenseMainService {

    //es配置路径
    @Value("${elasticsearch-api.universalSave}")
    private String universalSave;

    private static Logger log = LoggerFactory.getLogger(FileInterfaceUtil.class);
    public String saveOrUpdateElecLicenseMainWithES(ElecLicenseMain elecLicenseMain, String districtOid, String organOid,String directoryObj,String directoryName) throws Exception{
       String message = null;
       ElecLicenseMainData elecLicenseMainData = new ElecLicenseMainData();
       elecLicenseMainData.setOrganOid(organOid);
       elecLicenseMainData.setClaimOrganOid(organOid);
       elecLicenseMainData.setLongEffect(elecLicenseMain.getLongEffect());
       elecLicenseMainData.setDirAuditStatus("3");
       elecLicenseMainData.settingData(elecLicenseMain);
       elecLicenseMainData.setDirectoryObj(directoryObj);
       elecLicenseMainData.setDirectoryName(directoryName);
       elecLicenseMainData.setElecLicenOid(elecLicenseMain.getElecLicenOid());
       elecLicenseMainData.setOid( elecLicenseMain.getElecLicenOid() );
       //根据机构oid获取区划主键
       elecLicenseMainData.setDistrictOid(districtOid);
       Map<String,Object> result = this.updElecLicenseMainDataByOid(elecLicenseMainData);
       if(!(boolean)result.get("resultFlag")){
           message = "es服务异常！";
       }
       return message;
   }
    /**
     * @desc: 修改
     * @author: chenyj
     * @date: 2020/3/10
     * @param elecLicenseMainData
     * @return
     * @throws Exception
     */
    public Map<String,Object> updElecLicenseMainDataByOid(ElecLicenseMainData elecLicenseMainData) throws Exception{
        Map<String,Object> map=new HashMap(4);
        String elecLicenseMainDataJSON = JSON.toJSONString(elecLicenseMainData);
        String value= HttpClient.post(universalSave+"elasticsearch/"+elecLicenseMainData.getElecLicenOid(), elecLicenseMainDataJSON);
        log.info("调用es返回参数"+value);
        Result result = JSONObject.parseObject(value, Result.class);
        if (result.isFlag()){
            elecLicenseMainData= JSON.toJavaObject((JSON) result.getData(),ElecLicenseMainData.class);
            map.put("resultFlag",true);
            map.put("resultData",elecLicenseMainData);
            return map;
        }
        map.put("resultFlag",false);
        return map;
    }


    /**
     * @desc: 根据oid查询单条数据
     * @author: chenyq
     * @date: 2020/4/29
     * @param licenseNumber
     * @return
     * @throws Exception
     */
    public int viewMainByLicenseNumber(String licenseNumber) throws Exception{
        int licenseNumberCount = 0;
        JSONObject obj = new JSONObject();
        obj.put("licenseNumber", licenseNumber);
        String data =JSONObject.toJSONString(obj);
        String value = HttpClient.post(universalSave + "elasticsearch/elecLicenseMainData/licenseNumberCount",data);
        Result result = JSONObject.parseObject(value, Result.class);
        if (result.isFlag()){
            if(result.getData()!=null)
                licenseNumberCount = (int)result.getData();
        }
        return licenseNumberCount;
    }
    /**
     * @desc: 根据oid查询单条数据
     * @author: chenyq
     * @date: 2020/04/29
     * @param licenseNumber
     * @return
     * @throws Exception
     */
    public int viewSubedByLicenseNumber(String licenseNumber) throws Exception{
        int licenseNumberCount = 0;
        JSONObject obj = new JSONObject();
        obj.put("licenseNumber", licenseNumber);
        String data =JSONObject.toJSONString(obj);
        String value = HttpClient.post(universalSave + "elasticsearch/elecLicenseMainDataSubed/licenseNumberCount",data);
        Result result = JSONObject.parseObject(value, Result.class);
        if (result.isFlag()){
            if(result.getData()!=null)
                licenseNumberCount = (int)result.getData();
        }
        return licenseNumberCount;
    }


    /**
     * @desc: 根据oid查询单条数据
     * @author: chenyj
     * @date: 2020/3/10
     * @param oid
     * @return
     * @throws Exception
     */
    public ElecLicenseMainData getElecLicenseMainDataByOid(String oid) throws Exception{
        ElecLicenseMainData elecLicenseMainData=null;
        String value = HttpClient.get(universalSave  +"elasticsearch/"+ oid);
        Result result = JSONObject.parseObject(value, Result.class);
        if (result.isFlag()){
            elecLicenseMainData= JSON.toJavaObject((JSON) result.getData(), ElecLicenseMainData.class);
        }
        return elecLicenseMainData;
    }



    /**
     * @desc: 根据oid和证件号码查询单条数据
     * @author: chenyq
     * @date: 2020/7/31
     * @param identificateNumber
     * @return
     * @throws Exception
     */
    public  int countMainByIdentificateNumber(String identificateNumber,String elecLicenOid,String directoryOid) throws Exception{
        int licenseNumberCount = 0;

        String value = HttpClient.get(universalSave + "elasticsearch/elecLicenseMainData/identificateNumberCount/"+ identificateNumber+"/"+ elecLicenOid+"/"+directoryOid);
        Result result = JSONObject.parseObject(value, Result.class);
        if (result.isFlag()){
            if(result.getData()!=null)
                licenseNumberCount = (int)result.getData();
        }
        return licenseNumberCount;
    }



    /**
     * @desc: 根据oid和证件号码查询单条数据
     * @author: chenyq
     * @date: 2020/7/31
     * @param identificateNumber
     * @return
     * @throws Exception
     */
    public  int countMainByIdentificateNumber(String identificateNumber,String directoryOid) throws Exception{
        int licenseNumberCount = 0;
        String value = HttpClient.get(universalSave+   "elasticsearch/elecLicenseMainDataSubed/identificateNumberCount/"+ identificateNumber+"/"+directoryOid);
        Result result = JSONObject.parseObject(value, Result.class);
        if (result.isFlag()){
            if(result.getData()!=null)
                licenseNumberCount = (int)result.getData();
        }
        return licenseNumberCount;
    }
}