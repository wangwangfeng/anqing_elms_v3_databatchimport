package com.zfsoft.batchimport.domain.entity;

import tk.mybatis.mapper.util.StringUtil;

import javax.persistence.*;
import java.util.Date;

/**
 * @表结构管理信息表
 * 
 * @author : chenyq
 * @date : 2020-05-07
 */
@Table(name = "T_TABLE_STRUCTURE")
public class TableStructure  {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @COLUMN_EXPLAIN : 主键
	 * @TABLE_COLUMN_TYPE : VARCHAR(32)
	 */
	@Id
	@Column(name = "OID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String oid;
	/**
	 * @COLUMN_EXPLAIN : 认领主键
	 * @TABLE_COLUMN_TYPE : VARCHAR(32)
	 */
	@Column(name = "CLAIM_OID", length=32)
	private String claimOid;
	/**
	 * @COLUMN_EXPLAIN : 目录主键
	 * @TABLE_COLUMN_TYPE : VARCHAR(32)
	 */
	@Column(name = "DIRECTORY_OID", length=32)
	private String directoryOid;
	/**
	 * @COLUMN_EXPLAIN : 下载时间
	 * @TABLE_COLUMN_TYPE : DATETIME
	 */
	@Column(name = "DOWNLOAD_DATE")
	private java.util.Date downloadDate;
	/**
	 * @COLUMN_EXPLAIN : 操作状态（1开始启动、2已完成、0未开始、3表不存在、4启动中）
	 * @TABLE_COLUMN_TYPE : CHAR(1)
	 */
	@Column(name = "OPERATER_STATUS")
	private String operaterStatus;
	/**
	 * @COLUMN_EXPLAIN : 表名称
	 * @TABLE_COLUMN_TYPE : VARCHAR(32)
	 */
	@Column(name = "TABLE_NAME", length=32)
	private String tableName;
	/**
	 * @COLUMN_EXPLAIN : 是否删除
	 * @TABLE_COLUMN_TYPE : CHAR(1)
	 */
	@Column(name = "DEL_FLAG")
	private String delFlag;
	/**
	 * @COLUMN_EXPLAIN : 机构主键
	 * @TABLE_COLUMN_TYPE : VARCHAR(32)
	 */
	@Column(name = "ORGAN_OID", length=32)
	private String organOid;
	/**
	 * @COLUMN_EXPLAIN : 启动开始时间
	 * @TABLE_COLUMN_TYPE : DATETIME
	 */
	@Column(name = "BEGIN_TIME")
	private java.util.Date beginTime;
	/**
	 * @COLUMN_EXPLAIN : 启动结束时间
	 * @TABLE_COLUMN_TYPE : DATETIME
	 */
	@Column(name = "END_TIME")
	private java.util.Date endTime;
	/**
	 * @COLUMN_EXPLAIN : 证照数据总条数
	 * @TABLE_COLUMN_TYPE : int
	 */
	@Column(name = "TOTAL_NUMBER")
	private Integer totalNumber;
	/**
	 * @COLUMN_EXPLAIN : 证照数据错误总条数
	 * @TABLE_COLUMN_TYPE : int
	 */
	@Column(name = "FAIL_NUMBER")
	private Integer failNumber;
	/**
	 * @COLUMN_EXPLAIN : 证照数据成功总条数
	 * @TABLE_COLUMN_TYPE : int
	 */
	@Column(name = "SUCCESS_NUMBER")
	private Integer successNumber;
	/**
	 * @COLUMN_EXPLAIN : 证照解析总时长(单位ms)
	 * @TABLE_COLUMN_TYPE : String
	 */
	@Column(name = "TOTAL_TIME")
	private String totalTime;

	/**
	 * @COLUMN_EXPLAIN : 修改时间
	 * @TABLE_COLUMN_TYPE : Date
	 */
	@Column(name = "MODIFY_DATE")
	private java.util.Date modifyDate;


	/**
	 * @COLUMN_EXPLAIN : 持证者类型
	 * @TABLE_COLUMN_TYPE : String
	 */
	@Column(name = "DIRECTORY_OBJ")
	private String directoryObj;
	/**
	 * @COLUMN_EXPLAIN : 证照名称
	 * @TABLE_COLUMN_TYPE : String
	 */
	@Column(name = "DIRECTORY_NAME")
	private String directoryName;
	/**
	 * @COLUMN_EXPLAIN : 证照类型代码
	 * @TABLE_COLUMN_TYPE : String
	 */
	@Column(name = "LICENSE_TYPE_CODE")
	private String licenseTypeCode;
	/**
	 * @COLUMN_EXPLAIN : 证照类型主键
	 * @TABLE_COLUMN_TYPE : String
	 */
	@Column(name = "LICENSE_TYPE_OID")
	private String licenseTypeOid;

	/**
	 * @COLUMN_EXPLAIN :  区划主键
	 * @TABLE_COLUMN_TYPE : String
	 */
	@Column(name = "DISTRICT_OID")
	private String districtOid;


	/**
	 * @主键
	 * @param oid the oid to set
	 */
	public void setOid(String oid) {
		if(StringUtil.isEmpty(oid)) {
			this.oid = null;
		} else {
			this.oid = oid;
		}
	}
	/**
	 * @主键
	 * @return the oid
	 */
	public String getOid() {
		return oid;
	}
	/**
	 * @认领主键
	 * @param claimOid the claimOid to set
	 */
	public void setClaimOid(String claimOid) {
		this.claimOid = claimOid;
	}
	/**
	 * @认领主键
	 * @return the claimOid
	 */
	public String getClaimOid() {
		return claimOid;
	}
	/**
	 * @目录主键
	 * @param directoryOid the directoryOid to set
	 */
	public void setDirectoryOid(String directoryOid) {
		this.directoryOid = directoryOid;
	}
	/**
	 * @目录主键
	 * @return the directoryOid
	 */
	public String getDirectoryOid() {
		return directoryOid;
	}
	/**
	 * @下载时间
	 * @param downloadDate the downloadDate to set
	 */
	public void setDownloadDate(java.util.Date downloadDate) {
		this.downloadDate = downloadDate;
	}
	/**
	 * @下载时间
	 * @return the downloadDate
	 */
	public java.util.Date getDownloadDate() {
		return downloadDate;
	}
	/**
	 * @操作状态（启动、已完成、未开始）
	 * @param operaterStatus the operaterStatus to set
	 */
	public void setOperaterStatus(String operaterStatus) {
		this.operaterStatus = operaterStatus;
	}
	/**
	 * @操作状态（启动、已完成、未开始）
	 * @return the operaterStatus
	 */
	public String getOperaterStatus() {
		return operaterStatus;
	}
	/**
	 * @表名称
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @表名称
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @是否删除
	 * @param delFlag the delFlag to set
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * @是否删除
	 * @return the delFlag
	 */
	public String getDelFlag() {
		return delFlag;
	}
	/**
	 * @机构主键
	 * @param organOid the organOid to set
	 */
	public void setOrganOid(String organOid) {
		this.organOid = organOid;
	}
	/**
	 * @机构主键
	 * @return the organOid
	 */
	public String getOrganOid() {
		return organOid;
	}
	/**
	 * @启动开始时间
	 * @param beginTime the beginTime to set
	 */
	public void setBeginTime(java.util.Date beginTime) {
		this.beginTime = beginTime;
	}
	/**
	 * @启动开始时间
	 * @return the beginTime
	 */
	public java.util.Date getBeginTime() {
		return beginTime;
	}
	/**
	 * @启动结束时间
	 * @param endTime the endTime to set
	 */
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * @启动结束时间
	 * @return the endTime
	 */
	public java.util.Date getEndTime() {
		return endTime;
	}
	/**
	 * @证照数据总条数
	 * @param totalNumber the totalNumber to set
	 */
	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}
	/**
	 * @证照数据总条数
	 * @return the totalNumber
	 */
	public Integer getTotalNumber() {
		return totalNumber;
	}
	/**
	 * @证照数据错误总条数
	 * @param failNumber the failNumber to set
	 */
	public void setFailNumber(Integer failNumber) {
		this.failNumber = failNumber;
	}
	/**
	 * @证照数据错误总条数
	 * @return the failNumber
	 */
	public Integer getFailNumber() {
		return failNumber;
	}
	/**
	 * @证照数据成功总条数
	 * @param successNumber the successNumber to set
	 */
	public void setSuccessNumber(Integer successNumber) {
		this.successNumber = successNumber;
	}
	/**
	 * @证照数据成功总条数
	 * @return the successNumber
	 */
	public Integer getSuccessNumber() {
		return successNumber;
	}
	/**
	 * @证照解析总时长(单位ms)
	 * @param totalTime the totalTime to set
	 */
	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
	/**
	 * @证照解析总时长(单位ms)
	 * @return the totalTime
	 */
	public String getTotalTime() {
		return totalTime;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getDirectoryObj() {
		return directoryObj;
	}

	public void setDirectoryObj(String directoryObj) {
		this.directoryObj = directoryObj;
	}

	public String getDirectoryName() {
		return directoryName;
	}

	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}

	public String getLicenseTypeCode() {
		return licenseTypeCode;
	}

	public void setLicenseTypeCode(String licenseTypeCode) {
		this.licenseTypeCode = licenseTypeCode;
	}

	public String getLicenseTypeOid() {
		return licenseTypeOid;
	}

	public void setLicenseTypeOid(String licenseTypeOid) {
		this.licenseTypeOid = licenseTypeOid;
	}

	public String getDistrictOid() {
		return districtOid;
	}

	public void setDistrictOid(String districtOid) {
		this.districtOid = districtOid;
	}
}
