package com.zfsoft.batchimport.domain.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_structure_batch")
public class StructureBatch {
    /**
     * 主键
     */
    @Id
    @Column(name = "batch_oid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String batchOid;

    /**
     * 批次号，按照时间来生成202007040001
     */
    @Column(name = "batch_no")
    private String batchNo;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 批次状态，当前批次所处状态，0是未处理，1是已处理
     */
    @Column(name = "batch_status")
    private String batchStatus;
    /**
     * 处理信息记录
     */
    @Column(name = "batch_message")
    private String batchMessage;
    /**
     * 获取主键
     *
     * @return batch_oid - 主键
     */
    public String getBatchOid() {
        return batchOid;
    }

    /**
     * 设置主键
     *
     * @param batchOid 主键
     */
    public void setBatchOid(String batchOid) {
        this.batchOid = batchOid;
    }

    /**
     * 获取批次号，按照时间来生成202007040001
     *
     * @return batch_no - 批次号，按照时间来生成202007040001
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * 设置批次号，按照时间来生成202007040001
     *
     * @param batchNo 批次号，按照时间来生成202007040001
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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
     * 获取批次状态，当前批次所处状态，0是未处理，1是已处理
     *
     * @return batch_status - 批次状态，当前批次所处状态，0是未处理，1是已处理
     */
    public String getBatchStatus() {
        return batchStatus;
    }

    /**
     * 设置批次状态，当前批次所处状态，0是未处理，1是已处理
     *
     * @param batchStatus 批次状态，当前批次所处状态，0是未处理，1是已处理
     */
    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }

    public String getBatchMessage() {
        return batchMessage;
    }

    public void setBatchMessage(String batchMessage) {
        this.batchMessage = batchMessage;
    }
}