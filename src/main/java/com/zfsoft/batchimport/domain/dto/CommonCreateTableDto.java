package com.zfsoft.batchimport.domain.dto;

import java.util.List;

/**
 * @author: kkfan
 * @create: 2020-03-11 00:07:32
 * @description: 通用表创建实体
 */
public class CommonCreateTableDto {

    /** 模板列表 */
    private List<Template> templateList;

    /**
     * 目录授权对象
     */
    private String directoryObj;

    /** 当前目录的证照类型编码 */
     private String licenseTypeCode;

    /**
     * 证照名称（目录名称）
     */
    private String  directoryName;

    /** 字段详情 */
    private List<CommonTableFieldDto> commonTableFieldDtoList;

    /** 有效期范围*/
    private List<ElecLicenseDirTypeTime> elecLicenseDirTypeTimeList;

    /** 认领对象*/
    private ElecDirClaim elecDirClaim;

    public List<CommonTableFieldDto> getCommonTableFieldDtoList() {
        return commonTableFieldDtoList;
    }

    public void setCommonTableFieldDtoList(List<CommonTableFieldDto> commonTableFieldDtoList) {
        this.commonTableFieldDtoList = commonTableFieldDtoList;
    }

    public List<ElecLicenseDirTypeTime> getElecLicenseDirTypeTimeList() {
        return elecLicenseDirTypeTimeList;
    }

    public void setElecLicenseDirTypeTimeList(List<ElecLicenseDirTypeTime> elecLicenseDirTypeTimeList) {
        this.elecLicenseDirTypeTimeList = elecLicenseDirTypeTimeList;
    }

    public ElecDirClaim getElecDirClaim() {
        return elecDirClaim;
    }

    public void setElecDirClaim(ElecDirClaim elecDirClaim) {
        this.elecDirClaim = elecDirClaim;
    }

    public String getDirectoryObj() {
        return directoryObj;
    }

    public void setDirectoryObj(String directoryObj) {
        this.directoryObj = directoryObj;
    }

    public String getLicenseTypeCode() {
        return licenseTypeCode;
    }

    public void setLicenseTypeCode(String licenseTypeCode) {
        this.licenseTypeCode = licenseTypeCode;
    }

    public List<Template> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<Template> templateList) {
        this.templateList = templateList;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }
}
