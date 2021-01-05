package com.zfsoft.batchimport.domain.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_license_metadata")
public class LicenseMetadata {
    @Id
    @Column(name = "METADATA_OID")
    private String metadataOid;

    @Column(name = "DIRECTORY_OID")
    private String directoryOid;

    @Column(name = "METADATA_NAME")
    private String metadataName;

    @Column(name = "COLUMN_NAME")
    private String columnName;

    @Column(name = "COLUMN_TYPE")
    private String columnType;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "COMFIDENTIAL_FLAG")
    private String comfidentialFlag;

    @Column(name = "DEL_FLAG")
    private String delFlag;

    @Column(name = "MUST_FLAG")
    private String mustFlag;

    @Column(name = "FORMDIRECTORY_FLAG")
    private String formdirectoryFlag;

    @Column(name = "IMGE_FLAG")
    private String imgeFlag;

    @Column(name = "ATTA_OID")
    private String attaOid;

    @Column(name = "LENGTH")
    private String length;

    @Column(name = "PLUGINS")
    private String plugins;

    @Column(name = "ORDER_NUMBER")
    private Long orderNumber;

    @Column(name = "AREA_HEIGHT")
    private Long areaHeight;

    @Column(name = "AREA_WIDTH")
    private Long areaWidth;

    @Column(name = "REPLACE_FLAG")
    private String replaceFlag;

    @Column(name = "ORGAN_OID")
    private String organOid;

    @Column(name = "DISTRICT_OID")
    private String districtOid;

    @Column(name = "MAIN_METADATA_FLAG")
    private String mainMetadataFlag;

    @Column(name = "MODIFY_DATE")
    private Date modifyDate;

    @Column(name = "SYNC_FLAG")
    private Integer syncFlag;

    @Column(name = "DIR_TYPE_OID")
    private String dirTypeOid;

    @Column(name = "HTML_TEMP")
    private String htmlTemp;

    @Column(name = "COLUMN_SPLIT")
    private String columnSplit;

    @Column(name = "OPTION_SPLIT")
    private String optionSplit;

    /**
     * @return METADATA_OID
     */
    public String getMetadataOid() {
        return metadataOid;
    }

    /**
     * @param metadataOid
     */
    public void setMetadataOid(String metadataOid) {
        this.metadataOid = metadataOid;
    }

    /**
     * @return DIRECTORY_OID
     */
    public String getDirectoryOid() {
        return directoryOid;
    }

    /**
     * @param directoryOid
     */
    public void setDirectoryOid(String directoryOid) {
        this.directoryOid = directoryOid;
    }

    /**
     * @return METADATA_NAME
     */
    public String getMetadataName() {
        return metadataName;
    }

    /**
     * @param metadataName
     */
    public void setMetadataName(String metadataName) {
        this.metadataName = metadataName;
    }

    /**
     * @return COLUMN_NAME
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param columnName
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * @return COLUMN_TYPE
     */
    public String getColumnType() {
        return columnType;
    }

    /**
     * @param columnType
     */
    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    /**
     * @return CREATE_DATE
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return COMFIDENTIAL_FLAG
     */
    public String getComfidentialFlag() {
        return comfidentialFlag;
    }

    /**
     * @param comfidentialFlag
     */
    public void setComfidentialFlag(String comfidentialFlag) {
        this.comfidentialFlag = comfidentialFlag;
    }

    /**
     * @return DEL_FLAG
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * @return MUST_FLAG
     */
    public String getMustFlag() {
        return mustFlag;
    }

    /**
     * @param mustFlag
     */
    public void setMustFlag(String mustFlag) {
        this.mustFlag = mustFlag;
    }

    /**
     * @return FORMDIRECTORY_FLAG
     */
    public String getFormdirectoryFlag() {
        return formdirectoryFlag;
    }

    /**
     * @param formdirectoryFlag
     */
    public void setFormdirectoryFlag(String formdirectoryFlag) {
        this.formdirectoryFlag = formdirectoryFlag;
    }

    /**
     * @return IMGE_FLAG
     */
    public String getImgeFlag() {
        return imgeFlag;
    }

    /**
     * @param imgeFlag
     */
    public void setImgeFlag(String imgeFlag) {
        this.imgeFlag = imgeFlag;
    }

    /**
     * @return ATTA_OID
     */
    public String getAttaOid() {
        return attaOid;
    }

    /**
     * @param attaOid
     */
    public void setAttaOid(String attaOid) {
        this.attaOid = attaOid;
    }

    /**
     * @return LENGTH
     */
    public String getLength() {
        return length;
    }

    /**
     * @param length
     */
    public void setLength(String length) {
        this.length = length;
    }

    /**
     * @return PLUGINS
     */
    public String getPlugins() {
        return plugins;
    }

    /**
     * @param plugins
     */
    public void setPlugins(String plugins) {
        this.plugins = plugins;
    }

    /**
     * @return ORDER_NUMBER
     */
    public Long getOrderNumber() {
        return orderNumber;
    }

    /**
     * @param orderNumber
     */
    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * @return AREA_HEIGHT
     */
    public Long getAreaHeight() {
        return areaHeight;
    }

    /**
     * @param areaHeight
     */
    public void setAreaHeight(Long areaHeight) {
        this.areaHeight = areaHeight;
    }

    /**
     * @return AREA_WIDTH
     */
    public Long getAreaWidth() {
        return areaWidth;
    }

    /**
     * @param areaWidth
     */
    public void setAreaWidth(Long areaWidth) {
        this.areaWidth = areaWidth;
    }

    /**
     * @return REPLACE_FLAG
     */
    public String getReplaceFlag() {
        return replaceFlag;
    }

    /**
     * @param replaceFlag
     */
    public void setReplaceFlag(String replaceFlag) {
        this.replaceFlag = replaceFlag;
    }

    /**
     * @return ORGAN_OID
     */
    public String getOrganOid() {
        return organOid;
    }

    /**
     * @param organOid
     */
    public void setOrganOid(String organOid) {
        this.organOid = organOid;
    }

    /**
     * @return DISTRICT_OID
     */
    public String getDistrictOid() {
        return districtOid;
    }

    /**
     * @param districtOid
     */
    public void setDistrictOid(String districtOid) {
        this.districtOid = districtOid;
    }

    /**
     * @return MAIN_METADATA_FLAG
     */
    public String getMainMetadataFlag() {
        return mainMetadataFlag;
    }

    /**
     * @param mainMetadataFlag
     */
    public void setMainMetadataFlag(String mainMetadataFlag) {
        this.mainMetadataFlag = mainMetadataFlag;
    }

    /**
     * @return MODIFY_DATE
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * @param modifyDate
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * @return SYNC_FLAG
     */
    public Integer getSyncFlag() {
        return syncFlag;
    }

    /**
     * @param syncFlag
     */
    public void setSyncFlag(Integer syncFlag) {
        this.syncFlag = syncFlag;
    }

    /**
     * @return DIR_TYPE_OID
     */
    public String getDirTypeOid() {
        return dirTypeOid;
    }

    /**
     * @param dirTypeOid
     */
    public void setDirTypeOid(String dirTypeOid) {
        this.dirTypeOid = dirTypeOid;
    }

    /**
     * @return HTML_TEMP
     */
    public String getHtmlTemp() {
        return htmlTemp;
    }

    /**
     * @param htmlTemp
     */
    public void setHtmlTemp(String htmlTemp) {
        this.htmlTemp = htmlTemp;
    }

    /**
     * @return COLUMN_SPLIT
     */
    public String getColumnSplit() {
        return columnSplit;
    }

    /**
     * @param columnSplit
     */
    public void setColumnSplit(String columnSplit) {
        this.columnSplit = columnSplit;
    }

    /**
     * @return OPTION_SPLIT
     */
    public String getOptionSplit() {
        return optionSplit;
    }

    /**
     * @param optionSplit
     */
    public void setOptionSplit(String optionSplit) {
        this.optionSplit = optionSplit;
    }
}