package com.zfsoft.batchimport.domain.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_qrcode_config")
public class QRcodeConfig {
    /**
     * 主键
     */
    @Id
    @Column(name = "QRcode_oid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String qrcodeOid;

    /**
     * 所属类型（基本信息，照面元素信息）
     */
    @Column(name = "config_Type")
    private String configType;

    /**
     * 名称
     */
    @Column(name = "config_name")
    private String configName;

    /**
     * 名称代码
     */
    @Column(name = "config_code")
    private String configCode;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 删除状态
     */
    @Column(name = "del_flag")
    private String delFlag;



    /**
     * 修改时间
     */
    @Column(name = "MODIFY_DATE")
    private Date modifyDate;
    /**
     * 修改时间
     */
    @Column(name = "paixu")
    private Integer paixu;



    /**
     * 证照类型主键
     */
    @Column(name = "LICENSE_TYPE_OID")
    private String licenseTypeOid;


    /**
     * 获取主键
     *
     * @return QRcode_oid - 主键
     */
    public String getQrcodeOid() {
        return qrcodeOid;
    }

    /**
     * 设置主键
     *
     * @param qrcodeOid 主键
     */
    public void setQrcodeOid(String qrcodeOid) {
        this.qrcodeOid = qrcodeOid;
    }

    /**
     * 获取所属类型（基本信息，照面元素信息）
     *
     * @return config_Type - 所属类型（基本信息，照面元素信息）
     */
    public String getConfigType() {
        return configType;
    }

    /**
     * 设置所属类型（基本信息，照面元素信息）
     *
     * @param configType 所属类型（基本信息，照面元素信息）
     */
    public void setConfigType(String configType) {
        this.configType = configType;
    }

    /**
     * 获取名称
     *
     * @return config_name - 名称
     */
    public String getConfigName() {
        return configName;
    }

    /**
     * 设置名称
     *
     * @param configName 名称
     */
    public void setConfigName(String configName) {
        this.configName = configName;
    }

    /**
     * 获取名称代码
     *
     * @return config_code - 名称代码
     */
    public String getConfigCode() {
        return configCode;
    }

    /**
     * 设置名称代码
     *
     * @param configCode 名称代码
     */
    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取删除状态
     *
     * @return del_flag - 删除状态
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * 设置删除状态
     *
     * @param delFlag 删除状态
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }


    /**
     * 获取修改时间
     *
     * @return MODIFY_DATE - 修改时间
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * 设置修改时间
     *
     * @param modifyDate 修改时间
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * @return paixu
     */
    public Integer getPaixu() {
        return paixu;
    }

    /**
     * @param paixu
     */
    public void setPaixu(Integer paixu) {
        this.paixu = paixu;
    }


    public String getLicenseTypeOid() {
        return licenseTypeOid;
    }

    public void setLicenseTypeOid(String licenseTypeOid) {
        this.licenseTypeOid = licenseTypeOid;
    }
}