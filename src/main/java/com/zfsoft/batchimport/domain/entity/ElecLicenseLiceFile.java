package com.zfsoft.batchimport.domain.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @电子证照证照文档
 * @author : chenyq
 * @date : 20200423
 */
@Table(name = "T_ELEC_LICENSE_LICE_FILE")
public class ElecLicenseLiceFile  {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @COLUMN_EXPLAIN : 主键
	 * @TABLE_COLUMN_TYPE : VARCHAR (32)
	 */
	@Id
	@Column(name = "LICE_FILE_ID", length=32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String liceFileId;
	/**
	 * @COLUMN_EXPLAIN : 证照主表id
	 * @TABLE_COLUMN_TYPE : VARCHAR (32)
	 */
	@Column(name = "ELEC_LICEN_OID", length=32)
	private String elecLicenOid;
	/**
	 * @COLUMN_EXPLAIN : 模板id
	 * @TABLE_COLUMN_TYPE : VARCHAR (32)
	 */
	@Column(name = "TEMPLATE_OID", length=32)
	private String templateOid;
	/**
	 * @COLUMN_EXPLAIN : 启用状态 Y启用 N禁用
	 * @TABLE_COLUMN_TYPE : VARCHAR(1)
	 */
	@Column(name = "ENABLE_STATUS", length=1)
	private String enableStatus;
	/**
	 * @COLUMN_EXPLAIN : 删除状态 Y删除 N未删除
	 * @TABLE_COLUMN_TYPE : VARCHAR(1)
	 */
	@Column(name = "DEL_FLAG", length=1)
	private String delFlag;

	/**
	 * @COLUMN_EXPLAIN : 证照文文件路径
	 * @TABLE_COLUMN_TYPE : VARCHAR (32)
	 */
	@Column(name = "ATTA_OID", length=32)
	private String attaOid;
	/**
	 * @COLUMN_EXPLAIN : 屏蔽敏感信息证照文文件路径
	 * @TABLE_COLUMN_TYPE : VARCHAR (32)
	 */
	@Column(name = "MASK_ATTA_OID", length=32)
	private String maskAttaOid;
	
	/**
     * @COLUMN_EXPLAIN : 屏蔽敏感信息证照文件md5加密结果
     * @TABLE_COLUMN_TYPE : VARCHAR (32)
     */
    @Column(name = "MASK_MD_VERIFY", length=32)
    private String maskMdVerify;
	/**
	 * @COLUMN_EXPLAIN : 创建时间
	 * @TABLE_COLUMN_TYPE : DATETIME
	 */
	@Column(name = "CREATE_DATE")
	private Date createDate;
	/**
	 * @COLUMN_EXPLAIN : 创建人
	 * @TABLE_COLUMN_TYPE : VARCHAR (32)
	 */
	@Column(name = "CREATE_BY", length=32)
	private String createBy;
	/**
	 * 当前版本号
	 */
	@Column(name = "CUR_VERSIONS")
	private Integer versions;
	
	/**
	 * @COLUMN_EXPLAIN : 文件md5加密结果
	 * @TABLE_COLUMN_TYPE : VARCHAR (32)
	 */
	@Column(name = "MD_VERIFY", length=32)
	private String mdVerify;

	public String getLiceFileId() {
		return liceFileId;
	}

	public void setLiceFileId(String liceFileId) {
		this.liceFileId = liceFileId;
	}

	public String getElecLicenOid() {
		return elecLicenOid;
	}

	public void setElecLicenOid(String elecLicenOid) {
		this.elecLicenOid = elecLicenOid;
	}

	public String getTemplateOid() {
		return templateOid;
	}

	public void setTemplateOid(String templateOid) {
		this.templateOid = templateOid;
	}

	public String getEnableStatus() {
		return enableStatus;
	}

	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
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

	public String getMaskMdVerify() {
		return maskMdVerify;
	}

	public void setMaskMdVerify(String maskMdVerify) {
		this.maskMdVerify = maskMdVerify;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Integer getVersions() {
		return versions;
	}

	public void setVersions(Integer versions) {
		this.versions = versions;
	}

	public String getMdVerify() {
		return mdVerify;
	}

	public void setMdVerify(String mdVerify) {
		this.mdVerify = mdVerify;
	}
}
