package com.zfsoft.batchimport.domain.dto;

import com.zfsoft.batchimport.common.BaseStaticParameter;
import com.zfsoft.batchimport.domain.entity.ElecLicenseMain;
import com.zfsoft.batchimport.kafka.FromStateConstans;

import java.io.Serializable;
import java.util.Date;

/**
 * 电子证照信息录入
 * @author chenjian
 */
public class ElecLicenseMainData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String oid;

    /**
     * 证照主键
     */
    private String elecLicenOid;

    /**
     * 证照编号
     */
    private String licenseNumber;

    /**
     * 认领主键
     */
    private String claimOid;

    /**
     * 证照状态
     */
    private String licenseStatus;

    /**
     * 证照类型
     */
    private String licenseType;

    /**
     * 证照名称
     */
    private String directoryName;

    /**
     * 持有者类型
     */
    private String directoryObj;

    /**
     * 持有者名称
     */
    private String holder;

    /**
     * 持有者证件号
     */
    private String identificateNumber;

    /**
     * 采集时间
     */
    private Date createDate;

    /**
     * 制证时间
     */
    private Date accreditationDate;

    private String processState;

    private Date createTime;

    private String version;

    /**
     * 认领机构
     */
    private String organOid;

    /**
     * 电子证照唯一标识码
     */
    private String elecLicenseUnique;

    /**
     * 目录主键
     */
    private String directoryOid;

    /**
     * 签发主键
     */
    private String elecLicenseIssueOid;

    /**
     * 证照类别状态 A类 B类
     */
    private String licenseClassifyStatus;

    /**
     * 证照来源
     */
    private String infoSource;

    /**
     * 最终状态
     */
    private String finalStatus;

    /**
     * 注销审核状态
     */
    private String zxAuditFlag;

    /**
     * 注销废立状态
     */
    private String zxCancelFlag;

    /**
     * 吊销审核状态
     */
    private String dxAuditFlag;

    /**
     * 吊销废立状态
     */
    private String dxCancelFlag;

    /**
     * 延续审核状态
     */
    private String yxAuditFlag;

    /**
     * 延续删除状态
     */
    private String yxDelFlag;

    /**
     * 纠错审核状态
     */
    private String jcAuditFlag;

    /**
     * 纠错删除状态
     */
    private String jcDelFlag;

    /**
     * 是否需要年检
     */
    private String autoAnnualFlag;

    /**
     * 年检审核状态
     */
    private String njAuditFlag;

    /**
     * 年检删除状态
     */
    private String njDelFlag;

    /**
     * 变更审核状态
     */
    private String bgAuditFlag;

    /**
     * 变更前一次证照主键OID
     */
    private String bgPreElecOid;

    /**
     * 上一版证照状态
     */
    private String preLicenseStatus;

    /**
     * 变更后一次证照主键OID
     */
    private String bgNextElecOid;

    /**
     * 变更后一次审核状态
     */
    private String bgNextAuditFlag;

    /**
     * 作废状态
     */
    private String cancelFlag;

    /**
     * 认领部门
     */
    private String claimOrganOid;

    /**
     * 认领删除状态
     */
    private String claimDelFlag;

    /**
     * 目录删除状态
     */
    private String dirDelFlag;

    /**
     * 目录启用状态
     */
    private String dirAbleFlag;

    /**
     * 目录状态 0:新建目录，2：待审核，3：审核通过 4：审核不通过，5：变更带审核，6：变更审核不通过,7:变更未提交审核
     */
    private String dirAuditStatus;
    /**
     * 证照类型有效期表ID
     */
    private String timeOid;

    /**
     * 是否需要自动注销
     */
    private String autoCancelFlag;

    /**
     * 当前证照是否删除
     */
    private String delFlag;

    /**
     * 超期时间
     */
    private Date overAuditTime;
    /**
     * @COLUMN_EXPLAIN : 提交审核时间
     * @TABLE_COLUMN_TYPE : DATETIME
     */
    private Date submitAuditTime;
    /**
     * 超期自动注销时间
     */
    private String autoCancelDate;
    /**
     * 创建人 系统用户表主键
     */
    private String userOid;
    /**
     * 电子证照面元素id
     */
    private String tableParamOid;
    /**
     * 根据模板照面元素生成对应表名称
     */
    private String tableName;
    /**
     * 照面元素表ID
     */
    private String metadataOid;
    /**
     * 有效期起
     */
    private Date validityDateStart;
    /**
     * 有效期止
     */
    private Date validityDateEnd;
    /**
     * 自动年检提前时间
     */
    private String autoAnnualDate;
    /**
     * 证照等级  A,B,C,D
     */
    private String elecLicenseLevel;
    /**
     * 联系电话
     */
    private String contactTel;
    /**
     * 持证主体代码类型
     */
    private String holderCode;
    /**
     * 水印内容
     */
    private String waterMark;

    /**
     * 是否长期有效0是1否
     */
    private String longEffect;

    /**
     * pdf加密后的路径 pdf的路径和证照文件的路径一样 后缀名不一样
     */
    private String attaPathMD5;

    /**
     * 超期天数
     */
    private String auditOverTime;
    /**
     * 签发oid:签发的从办机构oid，可以是多个机构，以逗号分割
     */
    private String issueOidAndCoOrganOid;


    /**
     * 机构名称
     */
    private String organName;

    /**
     * 机构编码
     */
    private String organCode;

    /**
     * ofd存储在附件表中的主键，方便下载，预览ofd时，不需要访问数据库去拿
     */
    private String attaOid;


    /**
     * 保密ofd存储在附件表中的主键，方便下载，预览ofd时，不需要访问数据库去拿
     */
    private String maskAttaOid;

    /**
     * 区划主键
     */
    private String districtOid;




    public String getOid() {
        return oid;
    }

    public String getAutoAnnualDate() {
        return autoAnnualDate;
    }

    public void setAutoAnnualDate(String autoAnnualDate) {
        this.autoAnnualDate = autoAnnualDate;
    }

    public String getElecLicenseLevel() {
        return elecLicenseLevel;
    }

    public void setElecLicenseLevel(String elecLicenseLevel) {
        this.elecLicenseLevel = elecLicenseLevel;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getHolderCode() {
        return holderCode;
    }

    public void setHolderCode(String holderCode) {
        this.holderCode = holderCode;
    }

    public String getWaterMark() {
        return waterMark;
    }

    public String getAttaPathMD5() {
        return attaPathMD5;
    }

    public void setAttaPathMD5(String attaPathMD5) {
        this.attaPathMD5 = attaPathMD5;
    }

    public String getLongEffect() {
        return longEffect;
    }

    public void setLongEffect(String longEffect) {
        this.longEffect = longEffect;
    }

    public void setWaterMark(String waterMark) {
        this.waterMark = waterMark;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Date getOverAuditTime() {
        return overAuditTime;
    }

    public String getTableName() {
        return tableName;
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

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getMetadataOid() {
        return metadataOid;
    }

    public void setMetadataOid(String metadataOid) {
        this.metadataOid = metadataOid;
    }

    public void setOverAuditTime(Date overAuditTime) {
        this.overAuditTime = overAuditTime;
    }

    public String getElecLicenOid() {
        return elecLicenOid;
    }

    public void setElecLicenOid(String elecLicenOid) {
        this.elecLicenOid = elecLicenOid;
    }

    public String getTableParamOid() {
        return tableParamOid;
    }

    public void setTableParamOid(String tableParamOid) {
        this.tableParamOid = tableParamOid;
    }

    public String getLicenseStatus() {
        return licenseStatus;
    }

    public void setLicenseStatus(String licenseStatus) {
        this.licenseStatus = licenseStatus;
    }

    public String getClaimOid() {
        return claimOid;
    }

    public String getUserOid() {
        return userOid;
    }

    public void setUserOid(String userOid) {
        this.userOid = userOid;
    }

    public void setClaimOid(String claimOid) {
        this.claimOid = claimOid;
    }

    public String getElecLicenseUnique() {
        return elecLicenseUnique;
    }

    public void setElecLicenseUnique(String elecLicenseUnique) {
        this.elecLicenseUnique = elecLicenseUnique;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getIdentificateNumber() {
        return identificateNumber;
    }

    public void setIdentificateNumber(String identificateNumber) {
        this.identificateNumber = identificateNumber;
    }

    public String getDirectoryObj() {
        return directoryObj;
    }

    public void setDirectoryObj(String directoryObj) {
        this.directoryObj = directoryObj;
    }

    public String getOrganOid() {
        return organOid;
    }

    public void setOrganOid(String organOid) {
        this.organOid = organOid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDirectoryOid() {
        return directoryOid;
    }

    public void setDirectoryOid(String directoryOid) {
        this.directoryOid = directoryOid;
    }

    public String getElecLicenseIssueOid() {
        return elecLicenseIssueOid;
    }

    public Date getAccreditationDate() {
        return accreditationDate;
    }

    public void setAccreditationDate(Date accreditationDate) {
        this.accreditationDate = accreditationDate;
    }

    public void setElecLicenseIssueOid(String elecLicenseIssueOid) {
        this.elecLicenseIssueOid = elecLicenseIssueOid;
    }

    public String getLicenseClassifyStatus() {
        return licenseClassifyStatus;
    }

    public void setLicenseClassifyStatus(String licenseClassifyStatus) {
        this.licenseClassifyStatus = licenseClassifyStatus;
    }

    public String getInfoSource() {
        return infoSource;
    }

    public void setInfoSource(String infoSource) {
        this.infoSource = infoSource;
    }

    public String getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(String finalStatus) {
        this.finalStatus = finalStatus;
    }

    public String getZxAuditFlag() {
        return zxAuditFlag;
    }

    public void setZxAuditFlag(String zxAuditFlag) {
        this.zxAuditFlag = zxAuditFlag;
    }

    public String getZxCancelFlag() {
        return zxCancelFlag;
    }

    public void setZxCancelFlag(String zxCancelFlag) {
        this.zxCancelFlag = zxCancelFlag;
    }

    public String getDxAuditFlag() {
        return dxAuditFlag;
    }

    public void setDxAuditFlag(String dxAuditFlag) {
        this.dxAuditFlag = dxAuditFlag;
    }

    public String getDxCancelFlag() {
        return dxCancelFlag;
    }

    public void setDxCancelFlag(String dxCancelFlag) {
        this.dxCancelFlag = dxCancelFlag;
    }

    public String getYxAuditFlag() {
        return yxAuditFlag;
    }

    public void setYxAuditFlag(String yxAuditFlag) {
        this.yxAuditFlag = yxAuditFlag;
    }

    public String getYxDelFlag() {
        return yxDelFlag;
    }

    public void setYxDelFlag(String yxDelFlag) {
        this.yxDelFlag = yxDelFlag;
    }

    public String getJcAuditFlag() {
        return jcAuditFlag;
    }

    public void setJcAuditFlag(String jcAuditFlag) {
        this.jcAuditFlag = jcAuditFlag;
    }

    public String getJcDelFlag() {
        return jcDelFlag;
    }

    public void setJcDelFlag(String jcDelFlag) {
        this.jcDelFlag = jcDelFlag;
    }

    public String getAutoAnnualFlag() {
        return autoAnnualFlag;
    }

    public void setAutoAnnualFlag(String autoAnnualFlag) {
        this.autoAnnualFlag = autoAnnualFlag;
    }

    public String getNjAuditFlag() {
        return njAuditFlag;
    }

    public void setNjAuditFlag(String njAuditFlag) {
        this.njAuditFlag = njAuditFlag;
    }

    public String getNjDelFlag() {
        return njDelFlag;
    }

    public void setNjDelFlag(String njDelFlag) {
        this.njDelFlag = njDelFlag;
    }

    public String getBgAuditFlag() {
        return bgAuditFlag;
    }

    public void setBgAuditFlag(String bgAuditFlag) {
        this.bgAuditFlag = bgAuditFlag;
    }

    public String getBgPreElecOid() {
        return bgPreElecOid;
    }

    public void setBgPreElecOid(String bgPreElecOid) {
        this.bgPreElecOid = bgPreElecOid;
    }

    public String getBgNextElecOid() {
        return bgNextElecOid;
    }

    public void setBgNextElecOid(String bgNextElecOid) {
        this.bgNextElecOid = bgNextElecOid;
    }

    public String getBgNextAuditFlag() {
        return bgNextAuditFlag;
    }

    public void setBgNextAuditFlag(String bgNextAuditFlag) {
        this.bgNextAuditFlag = bgNextAuditFlag;
    }

    public String getPreLicenseStatus() {
        return preLicenseStatus;
    }

    public void setPreLicenseStatus(String preLicenseStatus) {
        this.preLicenseStatus = preLicenseStatus;
    }

    public String getClaimDelFlag() {
        return claimDelFlag;
    }

    public void setClaimDelFlag(String claimDelFlag) {
        this.claimDelFlag = claimDelFlag;
    }

    public String getCancelFlag() {
        return cancelFlag;
    }

    public void setCancelFlag(String cancelFlag) {
        this.cancelFlag = cancelFlag;
    }

    public String getClaimOrganOid() {
        return claimOrganOid;
    }

    public void setClaimOrganOid(String claimOrganOid) {
        this.claimOrganOid = claimOrganOid;
    }

    public String getDirDelFlag() {
        return dirDelFlag;
    }

    public void setDirDelFlag(String dirDelFlag) {
        this.dirDelFlag = dirDelFlag;
    }

    public String getDirAbleFlag() {
        return dirAbleFlag;
    }

    public void setDirAbleFlag(String dirAbleFlag) {
        this.dirAbleFlag = dirAbleFlag;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    public String getDirAuditStatus() { return dirAuditStatus; }

    public void setDirAuditStatus(String dirAuditStatus) { this.dirAuditStatus = dirAuditStatus; }

    public String getTimeOid() {
        return timeOid;
    }

    public void setTimeOid(String timeOid) {
        this.timeOid = timeOid;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ElecLicenseMainData{");
        sb.append("oid='").append(oid).append('\'');
        sb.append(", elecLicenOid='").append(elecLicenOid).append('\'');
        sb.append(", licenseNumber='").append(licenseNumber).append('\'');
        sb.append(", licenseStatus='").append(licenseStatus).append('\'');
        sb.append(", licenseType='").append(licenseType).append('\'');
        sb.append(", claimOid='").append(claimOid).append('\'');
        sb.append(", directoryName='").append(directoryName).append('\'');
        sb.append(", elecLicenseUnique='").append(elecLicenseUnique).append('\'');
        sb.append(", holder='").append(holder).append('\'');
        sb.append(", identificateNumber='").append(identificateNumber).append('\'');
        sb.append(", directoryObj='").append(directoryObj).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", processState='").append(processState).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", version='").append(version).append('\'');
        sb.append(", organOid='").append(organOid).append('\'');
        sb.append(", zxAuditFlag=").append(zxAuditFlag);
        sb.append(", zxCancelFlag='").append(zxCancelFlag).append('\'');
        sb.append(", dxAuditFlag=").append(dxAuditFlag);
        sb.append(", dxCancelFlag='").append(dxCancelFlag).append('\'');
        sb.append(", yxAuditFlag=").append(yxAuditFlag);
        sb.append(", yxDelFlag='").append(yxDelFlag).append('\'');
        sb.append(", jcAuditFlag=").append(jcAuditFlag);
        sb.append(", jcDelFlag='").append(jcDelFlag).append('\'');
        sb.append(", autoAnnualFlag=").append(autoAnnualFlag);
        sb.append(", njAuditFlag='").append(njAuditFlag).append('\'');
        sb.append(", njDelFlag=").append(njDelFlag);
        sb.append(", bgAuditFlag='").append(bgAuditFlag).append('\'');
        sb.append(", bgPreElecOid='").append(bgPreElecOid).append('\'');
        sb.append(", directoryOid=").append(directoryOid);
        sb.append(", elecLicenseIssueOid='").append(elecLicenseIssueOid).append('\'');
        sb.append(", licenseClassifyStatus=").append(licenseClassifyStatus);
        sb.append(", infoSource='").append(infoSource).append('\'');
        sb.append(", finalStatus='").append(finalStatus).append('\'');
        sb.append(", bgNextElecOid='").append(bgNextElecOid).append('\'');
        sb.append(", bgNextAuditFlag='").append(bgNextAuditFlag).append('\'');
        sb.append(", preLicenseStatus='").append(preLicenseStatus).append('\'');
        sb.append(", claimDelFlag='").append(claimDelFlag).append('\'');
        sb.append(", dirDelFlag='").append(dirDelFlag).append('\'');
        sb.append(", dirAbleFlag='").append(dirAbleFlag).append('\'');
        sb.append(", cancelFlag='").append(cancelFlag).append('\'');
        sb.append(", claimOrganOid='").append(claimOrganOid).append('\'');
        sb.append('}');
        return sb.toString();
    }
    public ElecLicenseMainData() {}
    public ElecLicenseMainData(ElecLicenseMain elecLicenseMain,CommonCreateTableDto commonCreateTableDto) {
        this.elecLicenOid = elecLicenseMain.getElecLicenOid();

        this.organOid =commonCreateTableDto.getElecDirClaim().getOrganOid();
        this.claimOrganOid=commonCreateTableDto.getElecDirClaim().getOrganOid();
        this.longEffect=elecLicenseMain.getLongEffect();
        this.dirAuditStatus = "3";
        this.settingData(elecLicenseMain);
        this.directoryObj = commonCreateTableDto.getDirectoryObj();
        this.directoryName = commonCreateTableDto.getDirectoryName();
        this.oid = elecLicenseMain.getElecLicenOid();
        this.districtOid = commonCreateTableDto.getElecDirClaim().getDistrictOid();
    }

    public void settingData(ElecLicenseMain elecLicenseMain) {
        this.elecLicenOid = elecLicenseMain.getElecLicenOid();
        this.claimOid = elecLicenseMain.getClaimOid();
        this.licenseStatus = elecLicenseMain.getLicenseStatus();
        this.licenseType = elecLicenseMain.getLicenseType();
        this.elecLicenseUnique = elecLicenseMain.getElecLicenseUnique();
        this.holder = elecLicenseMain.getHolder();
        this.holderCode = elecLicenseMain.getHolderCode();
        this.identificateNumber = elecLicenseMain.getIdentificateNumber();
        this.licenseNumber = elecLicenseMain.getLicenseNumber();
        this.createDate = elecLicenseMain.getCreateDate();
        this.createTime = elecLicenseMain.getCreateDate();
        this.processState = FromStateConstans.TO_BE_PROCESSED;
        this.directoryOid = elecLicenseMain.getDirectoryOid();
        this.licenseClassifyStatus = elecLicenseMain.getLicenseClassifyStatus();
        this.infoSource = elecLicenseMain.getInfoSource();
        this.claimDelFlag = BaseStaticParameter.NO;
        this.dirDelFlag = BaseStaticParameter.NO;
        this.dirAbleFlag = BaseStaticParameter.YES;
        this.version = elecLicenseMain.getLicenseVersion();
        this.timeOid = elecLicenseMain.getTimeOid();
        this.validityDateStart = elecLicenseMain.getValidityDateStart();
        this.validityDateEnd = elecLicenseMain.getValidityDateEnd();
        this.elecLicenseLevel = elecLicenseMain.getElecLicenseLevel();
        this.contactTel = elecLicenseMain.getContactTel();
        this.identificateNumber = elecLicenseMain.getIdentificateNumber();
        this.tableParamOid = elecLicenseMain.getTableParamOid();
        this.waterMark = elecLicenseMain.getWaterMark();
        this.userOid = elecLicenseMain.getUserOid();
        this.autoAnnualDate = elecLicenseMain.getAutoAnnualDate();
        this.autoCancelDate = elecLicenseMain.getAutoCancelDate();
        this.autoAnnualFlag = elecLicenseMain.getAutoAnnualFlag();
        this.autoCancelFlag = elecLicenseMain.getAutoCancelFlag();
        this.delFlag = elecLicenseMain.getDelFlag();
    }

    public Date getSubmitAuditTime() {
        return submitAuditTime;
    }

    public void setSubmitAuditTime(Date submitAuditTime) {
        this.submitAuditTime = submitAuditTime;
    }

    public String getAuditOverTime() {
        return auditOverTime;
    }

    public void setAuditOverTime(String auditOverTime) {
        this.auditOverTime = auditOverTime;
    }

    public String getIssueOidAndCoOrganOid() {
        return issueOidAndCoOrganOid;
    }

    public void setIssueOidAndCoOrganOid(String issueOidAndCoOrganOid) {
        this.issueOidAndCoOrganOid = issueOidAndCoOrganOid;
    }


    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public String getAttaOid() {
        return attaOid;
    }

    public void setAttaOid(String attaOid) {
        this.attaOid = attaOid;
    }

    public String getMaskAttaOid() {
        return maskAttaOid;
    }

    public void setMaskAttaOid(String maskAttaOid) {
        this.maskAttaOid = maskAttaOid;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDistrictOid() {
        return districtOid;
    }

    public void setDistrictOid(String districtOid) {
        this.districtOid = districtOid;
    }
}
