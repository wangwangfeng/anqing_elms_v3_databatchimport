package com.zfsoft.batchimport.domain.dto;

import java.util.Date;

/**
 * 模板Dto
 * @author chenyq
 * @date 2020-4-26
 */
public class Template {
	private static final long serialVersionUID = 5168683733082445520L;

	/**
	 * 主键
	 */
	private String templateOid;

	/**
	 * 模板名称
	 */
	private String templateName;

	/**
	 * 模板类型 0证照 1批文
	 */
	private String templateType;

	/**
	 * 启用状态 Y启用 N禁用
	 */
	private String enableStatus;

	/**
	 * 删除状态 Y删除 N未删除
	 */
	private String delFlag;

	/**
	 * 模板当前状态 0-新建目录2-待审核3-审核通过4-审核不通过5-变更待审核6-变更审核不通过
	 */
	private String templateStatus;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 创建人
	 */
	private String createBy;

	/**
	 * 模板文件路径
	 */
	private String attaOid;

	/**
	 * 证照模板内容
	 */
	private String designedHtml;
	/**
	 * 证照模板宽度
	 */
	private String htmlWidth;
	/**
	 * 证照模板高度
	 */
	private String htmlHeight;
	/**
	 * 对应底图id
	 */
	private String underlayOid;
	/**
	 * 所属目录主键
	 */
	private String directoryOid;


	/**
	 * 机构主键
	 */
	private String organOid;

	public String getTemplateOid() {
		return templateOid;
	}

	public void setTemplateOid(String templateOid) {
		this.templateOid = templateOid;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
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

	public String getTemplateStatus() {
		return templateStatus;
	}

	public void setTemplateStatus(String templateStatus) {
		this.templateStatus = templateStatus;
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

	public String getAttaOid() {
		return attaOid;
	}

	public void setAttaOid(String attaOid) {
		this.attaOid = attaOid;
	}

	public String getDesignedHtml() {
		return designedHtml;
	}

	public void setDesignedHtml(String designedHtml) {
		this.designedHtml = designedHtml;
	}

	public String getHtmlWidth() {
		return htmlWidth;
	}

	public void setHtmlWidth(String htmlWidth) {
		this.htmlWidth = htmlWidth;
	}

	public String getHtmlHeight() {
		return htmlHeight;
	}

	public void setHtmlHeight(String htmlHeight) {
		this.htmlHeight = htmlHeight;
	}

	public String getUnderlayOid() {
		return underlayOid;
	}

	public void setUnderlayOid(String underlayOid) {
		this.underlayOid = underlayOid;
	}

	public String getDirectoryOid() {
		return directoryOid;
	}

	public void setDirectoryOid(String directoryOid) {
		this.directoryOid = directoryOid;
	}

	public String getOrganOid() {
		return organOid;
	}

	public void setOrganOid(String organOid) {
		this.organOid = organOid;
	}
}

