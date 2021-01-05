package com.zfsoft.batchimport.domain.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zfsoft.batchimport.domain.dto.TableParamDto;
import tk.mybatis.mapper.util.StringUtil;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyq
 * @description 电子证照_照面元素数据储存表
 * @date 2019-12-11
 * @Copyright
 */
@Table(name = "T_ELEC_LICENSE_TABLE")
public class ElecLicenseMainTable  {
    protected static final long serialVersionUID = 1L;


    @Id
    @Column(name = "OID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected String oid;


    /**
     * @COLUMN_EXPLAIN : 照面元素数据HTML
     * @TABLE_COLUMN_TYPE : VARCHAR (32)
     */
    @Column(name = "PARAM_HTML", length = 4000, columnDefinition = "CLOB")
    @Lob
    private String paramHtml;

    /**
     * @COLUMN_EXPLAIN : 照面元素数据
     * @TABLE_COLUMN_TYPE : VARCHAR (32)
     */
    @Lob
    @Column(name = "PARAM_JSON", length = 4000, columnDefinition = "CLOB")
    private String paramJson;

    /**
     * @COLUMN_EXPLAIN : 照面元素数据
     * @TABLE_COLUMN_TYPE : VARCHAR (32)
     */
    @Column(name = "PARAM_JSON_MD5", length = 64)
    private String paramJsonMD5;

    /**
     * @COLUMN_EXPLAIN : 上个版本的照面元素数据
     * @TABLE_COLUMN_TYPE : VARCHAR (32)
     */
    @Lob
    @Column(name = "LASTVERSION_PARAM", length = 4000, columnDefinition = "CLOB")
    private String lastVersionParam;

    /**
     * @COLUMN_EXPLAIN : 公共查询字段
     * @TABLE_COLUMN_TYPE : VARCHAR (32)
     */
    @Column(name = "COMMON_KEY", length = 200)
    private String commonKey;

    /**
     * @COLUMN_EXPLAIN : 公共查询字段值
     * @TABLE_COLUMN_TYPE : VARCHAR (32)
     */
    @Column(name = "COMMON_VALUE", length = 200)
    private String commonValue;
    public ElecLicenseMainTable() {

    }

    public ElecLicenseMainTable(String oid) {
        this.oid = oid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getParamHtml() {
        return paramHtml;
    }

    public ElecLicenseMainTable setParamHtml(String paramHtml) {
        this.paramHtml = paramHtml;
        return this;
    }

    public String getParamJson() {
        return paramJson;
    }

    public void setParamJson(String paramJson) {
        this.paramJson = paramJson;
    }

    public String getLastVersionParam() {
        return lastVersionParam;
    }

    public void setLastVersionParam(String lastVersionParam) {
        this.lastVersionParam = lastVersionParam;
    }

    public String getParamJsonMD5() {
        return paramJsonMD5;
    }

    public void setParamJsonMD5(String paramJsonMD5) {
        this.paramJsonMD5 = paramJsonMD5;
    }

    public String getCommonKey() {
        return commonKey;
    }

    public void setCommonKey(String commonKey) {
        this.commonKey = commonKey;
    }

    public String getCommonValue() {
        return commonValue;
    }

    public void setCommonValue(String commonValue) {
        this.commonValue = commonValue;
    }

    public List<TableParamDto> getParamVOs() {
        List<TableParamDto> jsons = new ArrayList<>();
        if (StringUtil.isNotEmpty(this.paramJson)) {
            JSONArray jsonArray = JSON.parseArray(this.paramJson);
            jsons = jsonArray.toJavaList(TableParamDto.class);
        }
        return jsons;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(this.getOid() == null) return false;
        return this.getOid().equals(((ElecLicenseMainTable)obj).getOid());
    }
}
