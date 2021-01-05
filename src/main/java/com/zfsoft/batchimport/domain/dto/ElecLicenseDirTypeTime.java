package com.zfsoft.batchimport.domain.dto;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * @电子证照类型有效期表
 * 
 * @author : chenyq
 * @date : 20200426
 */
public class ElecLicenseDirTypeTime {
    private static final long serialVersionUID = 1L;

    /**
     * @COLUMN_EXPLAIN : 有效期类型主键
     * @TABLE_COLUMN_TYPE : VARCHAR (32)
     */
    private String timeOid;
    /**
     * @COLUMN_EXPLAIN : 有效期类型
     * @TABLE_COLUMN_TYPE : VARCHAR (32)
     */
    private String timeType;
    /**
     * @COLUMN_EXPLAIN : 有效期值
     * @TABLE_COLUMN_TYPE : VARCHAR (50)
     */
    private String timeValue;
    /**
     * @COLUMN_EXPLAIN : 证照类型OID
     * @TABLE_COLUMN_TYPE : VARCHAR (32)
     */
    private String licenseTypeOid;
    /**
     * @COLUMN_EXPLAIN : 删除状态 Y已删除 N未删除
     * @TABLE_COLUMN_TYPE : VARCHAR (1)
     */
    private String delFlag;

	public String getTimeOid() {
		return timeOid;
	}

	public void setTimeOid(String timeOid) {
		this.timeOid = timeOid;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public String getTimeValue() {
		return timeValue;
	}

	public void setTimeValue(String timeValue) {
		this.timeValue = timeValue;
	}

	public String getLicenseTypeOid() {
		return licenseTypeOid;
	}

	public void setLicenseTypeOid(String licenseTypeOid) {
		this.licenseTypeOid = licenseTypeOid;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
}
