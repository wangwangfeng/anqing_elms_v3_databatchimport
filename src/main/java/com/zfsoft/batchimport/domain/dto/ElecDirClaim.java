package com.zfsoft.batchimport.domain.dto;


import org.hibernate.validator.constraints.Length;

import java.util.Date;


/**
 * 目录认领表
 * 
 * @description
 * @author chenyq
 * @date 20200426
 * @Copyright 版权由上海卓繁信息技术股份有限公司拥有
 */
public class ElecDirClaim{

    private static final long serialVersionUID = 457079203958725270L;

    /**
     * @COLUMN_EXPLAIN : 主键
     * @TABLE_COLUMN_TYPE : varchar
     */
    private String oid;

    /**
     * @COLUMN_EXPLAIN : 区划主键
     * @TABLE_COLUMN_TYPE : varchar
     */
    private String districtOid;

    /**
     * @COLUMN_EXPLAIN : 认领机构主键
     * @TABLE_COLUMN_TYPE : varchar
     */
    @Length(min = 0, max = 32)
    private String organOid;

    /**
     * @COLUMN_EXPLAIN : 认领目录主键
     * @TABLE_COLUMN_TYPE : varchar
     */
    private String elecDirOid;

    /**
     * @COLUMN_EXPLAIN : 创建时间
     * @TABLE_COLUMN_TYPE : datetime
     */
    private Date createDate;

    /**
     * @COLUMN_EXPLAIN : 创建人
     * @TABLE_COLUMN_TYPE : varchar
     */
    private String createUser;

    /**
     * @COLUMN_EXPLAIN : 是否删除
     * @TABLE_COLUMN_TYPE : varchar
     */
    private String isDelete;


    /**
     * 修改时间
     * 此字段用于做时间戳记录 在新增和修改时 时间会更新
     */
    protected Date modifyDate;

    /**
     * @COLUMN_EXPLAIN : 是否同步认领模板 0未同步 1已同步成功 2已同步失败
     * @TABLE_COLUMN_TYPE : varchar
     */
    private String isTemplate;


    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getDistrictOid() {
        return districtOid;
    }

    public void setDistrictOid(String districtOid) {
        this.districtOid = districtOid;
    }

    public String getOrganOid() {
        return organOid;
    }

    public void setOrganOid(String organOid) {
        this.organOid = organOid;
    }

    public String getElecDirOid() {
        return elecDirOid;
    }

    public void setElecDirOid(String elecDirOid) {
        this.elecDirOid = elecDirOid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getIsTemplate() {
        return isTemplate;
    }

    public void setIsTemplate(String isTemplate) {
        this.isTemplate = isTemplate;
    }
}
