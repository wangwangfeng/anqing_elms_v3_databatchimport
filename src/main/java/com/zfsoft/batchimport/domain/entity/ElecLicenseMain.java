package com.zfsoft.batchimport.domain.entity;

import com.zfsoft.batchimport.common.BaseStaticParameter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author chenyq
 * @description
 * @date 2020-4-23
 * @Copyright
 */
@Table(name = "T_ELEC_LICENSE_MAIN")
public class ElecLicenseMain {
    /**
     * 主键
     */
    @Id
    @Column(name = "ELEC_LICEN_OID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String elecLicenOid; 
    /**
     * @COLUMN_EXPLAIN : 证照类型 0证照 1批文
     * @TABLE_COLUMN_TYPE : VARCHAR (32)
     */
    @Column(name = "LICENSE_TYPE", length = 32)
    private String licenseType;
    /**
     * @COLUMN_EXPLAIN : 证照版本
     * @TABLE_COLUMN_TYPE : VARCHAR (100)
     */
    @Column(name = "LICENSE_VERSION", length = 100)
    private String licenseVersion;
    /**
     * @COLUMN_EXPLAIN : 证照编号
     * @TABLE_COLUMN_TYPE : VARCHAR (100)
     */
    @Column(name = "LICENSE_NUMBER", length = 100)
    private String licenseNumber;
    /**
     * @COLUMN_EXPLAIN : 所属电子证照目录主键 对应T_ELEC _LICENSE_DIR_MAIN表主键
     * @TABLE_COLUMN_TYPE : VARCHAR (32)
     */
    @Column(name = "DIRECTORY_OID", length = 32)
    private String directoryOid;
    /**
     * @COLUMN_EXPLAIN : 目录认领对象
     * @TABLE_COLUMN_TYPE : VARCHAR (32)
     */
    @Column(name = "CLAIM_OID", length = 32)
    private String claimOid;
    /**
     * @COLUMN_EXPLAIN : 证照状态
     * @TABLE_COLUMN_TYPE : VARCHAR(2)
     */
    @Column(name = "LICENSE_STATUS", length = 2)
    private String licenseStatus;
    /**
     * @COLUMN_EXPLAIN : 有效期起
     * @TABLE_COLUMN_TYPE : DATETIME
     */
    @Column(name = "VALIDITY_DATE_START")
    private Date validityDateStart;
    /**
     * @COLUMN_EXPLAIN : 有效期止
     * @TABLE_COLUMN_TYPE : DATETIME
     */
    @Column(name = "VALIDITY_DATE_END")
    private Date validityDateEnd;

    /**
     * 是否需要自动年检 0需要 1不需要 2自定义
     */
    @Column(name = "AUTO_ANNUAL_FLAG")
    private String autoAnnualFlag;
    /**
     * 提前多少时间年检 默认单位 天
     */
    @Column(name = "AUTO_ANNUAL_DATE")
    private String autoAnnualDate;
    /**
     * 是否需要自动注销 0需要 1不需要 2自定义
     */
    @Column(name = "AUTO_CANCEL_FLAG")
    private String autoCancelFlag;
    /**
     * 超期多少时间自动注销 默认单位 天
     */
    @Column(name = "AUTO_CANCEL_DATE")
    private String autoCancelDate;
    /**
     * @COLUMN_EXPLAIN : 持证人名称（法人名称）
     * @TABLE_COLUMN_TYPE : VARCHAR (100)
     */
    @Column(name = "HOLDER", length = 100)
    private String holder;

    /**
     * 持证主体代码类型
     */
    @Column(name = "HOLDER_CODE", length = 10)
    private String holderCode;

    /**
     * @COLUMN_EXPLAIN : 持证人类型  0自然人  1企业 2混合 3其他
     * @TABLE_COLUMN_TYPE : VARCHAR(1)
     */
    @Column(name = "HOLDER_TYPE", length = 1)
    private String holderType;



    /**
     * @COLUMN_EXPLAIN : 制证时间
     * @TABLE_COLUMN_TYPE : DATETIME
     */
    @Column(name = "ACCREDITATION_DATE")
    private Date accreditationDate;

    /**
     * @COLUMN_EXPLAIN : 创建人 系统用户表主键
     * @TABLE_COLUMN_TYPE : VARCHAR(32)
     */
    @Column(name = "USER_OID", length = 32)
    private String userOid;
    /**
     * @COLUMN_EXPLAIN : 联系电话
     * @TABLE_COLUMN_TYPE : VARCHAR(20)
     */
    @Column(name = "CONTACT_TEL", length = 20)
    private String contactTel;


    /**
     * @COLUMN_EXPLAIN : 证照来源 0数据对接 1excel导入 2手工录入 3-扫描
     * @TABLE_COLUMN_TYPE : VARCHAR(1)
     */
    @Column(name = "INFO_SOURCE", length = 1)
    private String infoSource;
    /**
     * @COLUMN_EXPLAIN : 创建时间
     * @TABLE_COLUMN_TYPE : DATETIME
     */
    @Column(name = "CREATE_DATE")
    private Date createDate;
    /**
     * @COLUMN_EXPLAIN : 是否删除 DEL_FLAG
     * @TABLE_COLUMN_TYPE : VARCHAR(2)
     */
    @Column(name = "DEL_FLAG", length = 2)
    private String delFlag;

    /**
     * @COLUMN_EXPLAIN : 推送状态 PUSH_STATUS
     * @TABLE_COLUMN_TYPE : VARCHAR(2)
     */
    @Column(name = "PUSH_STATUS", length = 2)
    private String push_status;

    /**
     * 证照类别状态 A类 B类
     */
    @Column(name = "LICENSE_CLASSIFY_STATUS", length = 2)
    private String licenseClassifyStatus;
    /**
     * 水印内容
     */
    @Column(name = "WATER_MARK", length = 500)
    private String waterMark;
    /**
     * @COLUMN_EXPLAIN : 是否长期有效0是1否
     * @TABLE_COLUMN_TYPE : VARCHAR(1)
     */
    @Column(name = "LONG_EFFECT", length = 1)
    private String longEffect;

    /**
     * 电子证照唯一标识码
     */
    @Column(name = "ELEC_LICENSE_UNIQUE", length = 100)
    private String elecLicenseUnique;


    /**
     * 证件号(自然人 证件为持证人名称、身份证号， 企业证件为企业名称或法人名称、身份证号或企业信用代码或组织机构代码证）
     */
    @Column(name = "IDENTIFICATE_NUMBER", length = 50)
    private String identificateNumber;

    /**
     * 证照等级  A,B,C,D
     */
    @Column(name = "ELEC_LICENSE_LEVEL", length = 50)
    private String elecLicenseLevel;


    /**
     * 电子证照面元素id
     */
    @Column(name = "TABLE_PARAM_OID", length = 32)
    private String tableParamOid;

    @Column(name = "SYNC_ERR_TAG", length = 1)
    private Integer syncErrTag;
    /**
     * @COLUMN_EXPLAIN : 有效期oID
     * @TABLE_COLUMN_TYPE : VARCHAR (32)
     */
    @Column(name = "TIME_OID", length = 32)
    private String timeOid;

    /**
     * 修改时间
     * 此字段用于做时间戳记录 在新增和修改时 时间会更新
     */
    @Column(name = "MODIFY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDate;


    /**
     * 备注字段 新增字段 用于对接过来的二维码json信息
     */
    @Column(name = "REMARKS", length = 32)
    private String remarks;



    public ElecLicenseMain(){
        this.userOid="00000000000000000000000000000000";
        this.infoSource="1";//导入
        this.licenseClassifyStatus="A";
        this.createDate=new Date();
        this.modifyDate=new Date();
        this.licenseType= BaseStaticParameter.ZZ;
        this.delFlag=BaseStaticParameter.NO;
    }

    public String getElecLicenOid() {
        return elecLicenOid;
    }

    public void setElecLicenOid(String elecLicenOid) {
        this.elecLicenOid = elecLicenOid;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getLicenseVersion() {
        return licenseVersion;
    }

    public void setLicenseVersion(String licenseVersion) {
        this.licenseVersion = licenseVersion;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getDirectoryOid() {
        return directoryOid;
    }

    public void setDirectoryOid(String directoryOid) {
        this.directoryOid = directoryOid;
    }

    public String getClaimOid() {
        return claimOid;
    }

    public void setClaimOid(String claimOid) {
        this.claimOid = claimOid;
    }

    public String getLicenseStatus() {
        return licenseStatus;
    }

    public void setLicenseStatus(String licenseStatus) {
        this.licenseStatus = licenseStatus;
    }

    public Date getValidityDateStart() {
        return validityDateStart;
    }

    public void setValidityDateStart(Date validityDateStart) {
        this.validityDateStart = validityDateStart;
    }

    public Date getValidityDateEnd() {
        return validityDateEnd;
    }

    public void setValidityDateEnd(Date validityDateEnd) {
        this.validityDateEnd = validityDateEnd;
    }

    public String getAutoAnnualFlag() {
        return autoAnnualFlag;
    }

    public void setAutoAnnualFlag(String autoAnnualFlag) {
        this.autoAnnualFlag = autoAnnualFlag;
    }

    public String getAutoAnnualDate() {
        return autoAnnualDate;
    }

    public void setAutoAnnualDate(String autoAnnualDate) {
        this.autoAnnualDate = autoAnnualDate;
    }

    public String getAutoCancelFlag() {
        return autoCancelFlag;
    }

    public void setAutoCancelFlag(String autoCancelFlag) {
        this.autoCancelFlag = autoCancelFlag;
    }

    public String getAutoCancelDate() {
        return autoCancelDate;
    }

    public void setAutoCancelDate(String autoCancelDate) {
        this.autoCancelDate = autoCancelDate;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getHolderCode() {
        return holderCode;
    }

    public void setHolderCode(String holderCode) {
        this.holderCode = holderCode;
    }

    public String getHolderType() {
        return holderType;
    }

    public void setHolderType(String holderType) {
        this.holderType = holderType;
    }


    public Date getAccreditationDate() {
        return accreditationDate;
    }

    public void setAccreditationDate(Date accreditationDate) {
        this.accreditationDate = accreditationDate;
    }

    public String getUserOid() {
        return userOid;
    }

    public void setUserOid(String userOid) {
        this.userOid = userOid;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }


    public String getInfoSource() {
        return infoSource;
    }

    public void setInfoSource(String infoSource) {
        this.infoSource = infoSource;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getPush_status() {
        return push_status;
    }

    public void setPush_status(String push_status) {
        this.push_status = push_status;
    }

    public String getLicenseClassifyStatus() {
        return licenseClassifyStatus;
    }

    public void setLicenseClassifyStatus(String licenseClassifyStatus) {
        this.licenseClassifyStatus = licenseClassifyStatus;
    }

    public String getWaterMark() {
        return waterMark;
    }

    public void setWaterMark(String waterMark) {
        this.waterMark = waterMark;
    }

    public String getLongEffect() {
        return longEffect;
    }

    public void setLongEffect(String longEffect) {
        this.longEffect = longEffect;
    }

    public String getElecLicenseUnique() {
        return elecLicenseUnique;
    }

    public void setElecLicenseUnique(String elecLicenseUnique) {
        this.elecLicenseUnique = elecLicenseUnique;
    }

    public String getIdentificateNumber() {
        return identificateNumber;
    }

    public void setIdentificateNumber(String identificateNumber) {
        this.identificateNumber = identificateNumber;
    }

    public String getElecLicenseLevel() {
        return elecLicenseLevel;
    }

    public void setElecLicenseLevel(String elecLicenseLevel) {
        this.elecLicenseLevel = elecLicenseLevel;
    }

    public String getTableParamOid() {
        return tableParamOid;
    }

    public void setTableParamOid(String tableParamOid) {
        this.tableParamOid = tableParamOid;
    }

    public Integer getSyncErrTag() {
        return syncErrTag;
    }

    public void setSyncErrTag(Integer syncErrTag) {
        this.syncErrTag = syncErrTag;
    }

    public String getTimeOid() {
        return timeOid;
    }

    public void setTimeOid(String timeOid) {
        this.timeOid = timeOid;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }


    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
