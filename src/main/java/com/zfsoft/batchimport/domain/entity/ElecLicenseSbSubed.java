package com.zfsoft.batchimport.domain.entity;

import com.zfsoft.batchimport.utils.StringUtil;

import javax.persistence.*;

/**
 * @author : zhangh
 * @证照签发数据上报表
 * @date : 2020-06-24
 */
@Table(name = "T_ELEC_LICENSE_SB_SUBED")
public class ElecLicenseSbSubed {

    /**
     * @COLUMN_EXPLAIN : 主键
     * @TABLE_COLUMN_TYPE : VARCHAR (32)
     */
    @Id
    @Column(name = "OID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private java.lang.String oid;
    /**
     * @COLUMN_EXPLAIN : 证照主键
     * @TABLE_COLUMN_TYPE : VARCHAR (32)
     */
    @Column(name = "BIZ_ID", length = 32)
    private java.lang.String bizId;
    /**
     * @COLUMN_EXPLAIN : 证照持证人ID
     * @TABLE_COLUMN_TYPE : VARCHAR (18)
     */
    @Column(name = "OWNER_ID", length = 18)
    private java.lang.String ownerId;
    /**
     * @COLUMN_EXPLAIN : 证照目录名称
     * @TABLE_COLUMN_TYPE : VARCHAR (200)
     */
    @Column(name = "CONTENT_NAME", length = 200)
    private java.lang.String contentName;
    /**
     * @COLUMN_EXPLAIN : 证件照面编码
     * @TABLE_COLUMN_TYPE : VARCHAR (100)
     */
    @Column(name = "INFO_CODE", length = 100)
    private java.lang.String infoCode;
    /**
     * @COLUMN_EXPLAIN : 电子证照流水号，是电子证照识别的唯一标识
     * @TABLE_COLUMN_TYPE : VARCHAR (100)
     */
    @Column(name = "LSH_CODE", length = 100)
    private java.lang.String lshCode;
    /**
     * @COLUMN_EXPLAIN : 持有者名称
     * @TABLE_COLUMN_TYPE : VARCHAR (100)
     */
    @Column(name = "OWNER_NAME", length = 100)
    private java.lang.String ownerName;
    /**
     * @COLUMN_EXPLAIN : 证照版本
     * @TABLE_COLUMN_TYPE : VARCHAR (100)
     */
    @Column(name = "VERSION", length = 100)
    private java.lang.String version;
    /**
     * @COLUMN_EXPLAIN : 证照目录编码
     * @TABLE_COLUMN_TYPE : VARCHAR (100)
     */
    @Column(name = "CONTENT_CODE", length = 100)
    private java.lang.String contentCode;
    /**
     * @COLUMN_EXPLAIN : 证照有效期限开始时间
     * @TABLE_COLUMN_TYPE : datetime
     */
    @Column(name = "VALID_BEGIN")
    private java.util.Date validBegin;
    /**
     * @COLUMN_EXPLAIN : 证照有效期限结束时间
     * @TABLE_COLUMN_TYPE : datetime
     */
    @Column(name = "VALID_END")
    private java.util.Date validEnd;
    /**
     * @COLUMN_EXPLAIN : 创建时间
     * @TABLE_COLUMN_TYPE : datetime
     */
    @Column(name = "CREATE_TIME")
    private java.util.Date createTime;
    /**
     * @COLUMN_EXPLAIN : 制证人姓名
     * @TABLE_COLUMN_TYPE : VARCHAR (100)
     */
    @Column(name = "USER_NAME", length = 100)
    private java.lang.String userName;
    /**
     * @COLUMN_EXPLAIN : 制证人编码
     * @TABLE_COLUMN_TYPE : VARCHAR (50)
     */
    @Column(name = "USER_CODE", length = 50)
    private java.lang.String userCode;
    /**
     * @COLUMN_EXPLAIN : 制证人身份标识
     * @TABLE_COLUMN_TYPE : VARCHAR (20)
     */
    @Column(name = "IDCARD", length = 20)
    private java.lang.String idcard;
    /**
     * @COLUMN_EXPLAIN : 组织机构代码
     * @TABLE_COLUMN_TYPE : VARCHAR (50)
     */
    @Column(name = "ORG_CODE", length = 50)
    private java.lang.String orgCode;
    /**
     * @COLUMN_EXPLAIN : 组织机构名称
     * @TABLE_COLUMN_TYPE : VARCHAR (200)
     */
    @Column(name = "ORG_NAME", length = 200)
    private java.lang.String orgName;
    /**
     * @COLUMN_EXPLAIN : 操作类型
     * @TABLE_COLUMN_TYPE : VARCHAR (200)
     */
    @Column(name = "OP_TYPE", length = 200)
    private java.lang.String opType;
    /**
     * @COLUMN_EXPLAIN : 修改时间
     * @TABLE_COLUMN_TYPE : datetime
     */
    @Column(name = "MODIFY_DATE")
    private java.util.Date modifyDate;
    /**
     * @COLUMN_EXPLAIN : 同步时间
     * @TABLE_COLUMN_TYPE : datetime
     */
    @Column(name = "SUB_SYNC_DATE")
    private java.util.Date subSyncDate;
    /**
     * @COLUMN_EXPLAIN : 上报标识 1:已经上报 0: 未上报
     * @TABLE_COLUMN_TYPE : VARCHAR (1)
     */
    @Column(name = "SUB_PUSH_FLAG", length = 1)
    private java.lang.String subPushFlag;
    /**
     * @COLUMN_EXPLAIN : 推送排序号
     * @TABLE_COLUMN_TYPE : int (11)
     */
    @Column(name = "SUB_NUM_TIME")
    private java.lang.Integer subNumTime;

    /**
     * @COLUMN_EXPLAIN : 持有者类型
     * @TABLE_COLUMN_TYPE : VARCHAR (10)
     */
    @Column(name = "DIRECTORY_OBJ", length = 10)
    private java.lang.String directoryObj;

    /**
     * @COLUMN_EXPLAIN : 证照内容MD5值
     * @TABLE_COLUMN_TYPE : VARCHAR (200)
     */
    @Column(name = "CONTENT_MD5", length = 200)
    private java.lang.String contentMD5;

    /**
     * @param oid the oid to set
     * @主键
     */
    public void setOid(java.lang.String oid) {
        if (StringUtil.isEmpty(oid)) {
            this.oid = null;
        } else {
            this.oid = oid;
        }
    }

    /**
     * @return the oid
     * @主键
     */
    public java.lang.String getOid() {
        return oid;
    }

    /**
     * @param bizId the bizId to set
     * @证照主键
     */
    public void setBizId(java.lang.String bizId) {
        this.bizId = bizId;
    }

    /**
     * @return the bizId
     * @证照主键
     */
    public java.lang.String getBizId() {
        return bizId;
    }

    /**
     * @param ownerId the ownerId to set
     * @证照持证人ID
     */
    public void setOwnerId(java.lang.String ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * @return the ownerId
     * @证照持证人ID
     */
    public java.lang.String getOwnerId() {
        return ownerId;
    }

    /**
     * @param contentName the contentName to set
     * @证照目录名称
     */
    public void setContentName(java.lang.String contentName) {
        this.contentName = contentName;
    }

    /**
     * @return the contentName
     * @证照目录名称
     */
    public java.lang.String getContentName() {
        return contentName;
    }

    /**
     * @param infoCode the infoCode to set
     * @证件照面编码
     */
    public void setInfoCode(java.lang.String infoCode) {
        this.infoCode = infoCode;
    }

    /**
     * @return the infoCode
     * @证件照面编码
     */
    public java.lang.String getInfoCode() {
        return infoCode;
    }

    /**
     * @param ownerName the ownerName to set
     * @持有者名称
     */
    public void setOwnerName(java.lang.String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * @return the ownerName
     * @持有者名称
     */
    public java.lang.String getOwnerName() {
        return ownerName;
    }

    /**
     * @param version the version to set
     * @证照版本
     */
    public void setVersion(java.lang.String version) {
        this.version = version;
    }

    /**
     * @return the version
     * @证照版本
     */
    public java.lang.String getVersion() {
        return version;
    }

    /**
     * @param contentCode the contentCode to set
     * @证照目录编码
     */
    public void setContentCode(java.lang.String contentCode) {
        this.contentCode = contentCode;
    }

    /**
     * @return the contentCode
     * @证照目录编码
     */
    public java.lang.String getContentCode() {
        return contentCode;
    }

    /**
     * @param validBegin the validBegin to set
     * @证照有效期限开始时间
     */
    public void setValidBegin(java.util.Date validBegin) {
        this.validBegin = validBegin;
    }

    /**
     * @return the validBegin
     * @证照有效期限开始时间
     */
    public java.util.Date getValidBegin() {
        return validBegin;
    }

    /**
     * @param validEnd the validEnd to set
     * @证照有效期限结束时间
     */
    public void setValidEnd(java.util.Date validEnd) {
        this.validEnd = validEnd;
    }

    /**
     * @return the validEnd
     * @证照有效期限结束时间
     */
    public java.util.Date getValidEnd() {
        return validEnd;
    }

    /**
     * @param createTime the createTime to set
     * @创建时间
     */
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the createTime
     * @创建时间
     */
    public java.util.Date getCreateTime() {
        return createTime;
    }

    /**
     * @param userName the userName to set
     * @制证人姓名
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }

    /**
     * @return the userName
     * @制证人姓名
     */
    public java.lang.String getUserName() {
        return userName;
    }

    /**
     * @param userCode the userCode to set
     * @制证人编码
     */
    public void setUserCode(java.lang.String userCode) {
        this.userCode = userCode;
    }

    /**
     * @return the userCode
     * @制证人编码
     */
    public java.lang.String getUserCode() {
        return userCode;
    }

    /**
     * @param idcard the idcard to set
     * @制证人身份标识
     */
    public void setIdcard(java.lang.String idcard) {
        this.idcard = idcard;
    }

    /**
     * @return the idcard
     * @制证人身份标识
     */
    public java.lang.String getIdcard() {
        return idcard;
    }

    /**
     * @param orgCode the orgCode to set
     * @组织机构代码
     */
    public void setOrgCode(java.lang.String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * @return the orgCode
     * @组织机构代码
     */
    public java.lang.String getOrgCode() {
        return orgCode;
    }

    /**
     * @param orgName the orgName to set
     * @组织机构名称
     */
    public void setOrgName(java.lang.String orgName) {
        this.orgName = orgName;
    }

    /**
     * @return the orgName
     * @组织机构名称
     */
    public java.lang.String getOrgName() {
        return orgName;
    }

    /**
     * @param opType the opType to set
     * @操作类型
     */
    public void setOpType(java.lang.String opType) {
        this.opType = opType;
    }

    /**
     * @return the opType
     * @操作类型
     */
    public java.lang.String getOpType() {
        return opType;
    }

    /**
     * @param modifyDate the modifyDate to set
     * @修改时间
     */
    public void setModifyDate(java.util.Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * @return the modifyDate
     * @修改时间
     */
    public java.util.Date getModifyDate() {
        return modifyDate;
    }

    /**
     * @param subSyncDate the subSyncDate to set
     * @同步时间
     */
    public void setSubSyncDate(java.util.Date subSyncDate) {
        this.subSyncDate = subSyncDate;
    }

    /**
     * @return the subSyncDate
     * @同步时间
     */
    public java.util.Date getSubSyncDate() {
        return subSyncDate;
    }

    /**
     * @param subPushFlag the subPushFlag to set
     * @上报标识 1:已经上报 0: 未上报
     */
    public void setSubPushFlag(java.lang.String subPushFlag) {
        this.subPushFlag = subPushFlag;
    }

    /**
     * @return the subPushFlag
     * @上报标识 1:已经上报 0: 未上报
     */
    public java.lang.String getSubPushFlag() {
        return subPushFlag;
    }

    public java.lang.Integer getSubNumTime() {
        return subNumTime;
    }

    public void setSubNumTime(java.lang.Integer subNumTime) {
        this.subNumTime = subNumTime;
    }

    public java.lang.String getLshCode() {
        return lshCode;
    }

    public void setLshCode(java.lang.String lshCode) {
        this.lshCode = lshCode;
    }

    public String getDirectoryObj() {
        return directoryObj;
    }

    public void setDirectoryObj(String directoryObj) {
        this.directoryObj = directoryObj;
    }

    public String getContentMD5() {
        return contentMD5;
    }

    public void setContentMD5(String contentMD5) {
        this.contentMD5 = contentMD5;
    }
}
