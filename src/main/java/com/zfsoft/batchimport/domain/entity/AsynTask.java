package com.zfsoft.batchimport.domain.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_asyn_task")
public class AsynTask {
    /**
     * 主键
     */
    @Id
    @Column(name = "OID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String oid;

    /**
     * 任务名
     */
    @Column(name = "task_name")
    private String taskName;

    /**
     * 任务导入时间
     */
    @Column(name = "task_begin_time")
    private Date taskBeginTime;

    /**
     * 任务状态
     */
    @Column(name = "task_status")
    private String taskStatus;

    /**
     * 任务导入总条数
     */
    @Column(name = "task_total_number")
    private Integer taskTotalNumber;

    /**
     * 任务导入错误条数
     */
    @Column(name = "task_fail_number")
    private Integer taskFailNumber;

    /**
     * 上传文件id
     */
    @Column(name = "file_id")
    private String fileId;

    /**
     * 任务导入错误表下载文件id
     */
    @Column(name = "fail_file_id")
    private String failFileId;

    /**
     * 任务导入成功条数
     */
    @Column(name = "task_success_number")
    private Integer taskSuccessNumber;

    /**
     * 任务导入结束时间
     */
    @Column(name = "task_end_time")
    private Date taskEndTime;

    /**
     * 任务导入总用时(单位ms)
     */
    @Column(name = "task_total_time")
    private Long taskTotalTime;

    /**
     * 修改时间
     */
    @Column(name = "MODIFY_DATE")
    private Date modifyDate;

    /**
     * 获取主键
     *
     * @return OID - 主键
     */
    public String getOid() {
        return oid;
    }

    /**
     * 设置主键
     *
     * @param oid 主键
     */
    public void setOid(String oid) {
        this.oid = oid;
    }

    /**
     * 获取任务名
     *
     * @return task_name - 任务名
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 设置任务名
     *
     * @param taskName 任务名
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 获取任务导入时间
     *
     * @return task_begin_time - 任务导入时间
     */
    public Date getTaskBeginTime() {
        return taskBeginTime;
    }

    /**
     * 设置任务导入时间
     *
     * @param taskBeginTime 任务导入时间
     */
    public void setTaskBeginTime(Date taskBeginTime) {
        this.taskBeginTime = taskBeginTime;
    }

    /**
     * 获取任务状态
     *
     * @return task_status - 任务状态
     */
    public String getTaskStatus() {
        return taskStatus;
    }

    /**
     * 设置任务状态
     *
     * @param taskStatus 任务状态
     */
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    /**
     * 获取任务导入总条数
     *
     * @return task_total_number - 任务导入总条数
     */
    public Integer getTaskTotalNumber() {
        return taskTotalNumber;
    }

    /**
     * 设置任务导入总条数
     *
     * @param taskTotalNumber 任务导入总条数
     */
    public void setTaskTotalNumber(Integer taskTotalNumber) {
        this.taskTotalNumber = taskTotalNumber;
    }

    /**
     * 获取任务导入错误条数
     *
     * @return task_fail_number - 任务导入错误条数
     */
    public Integer getTaskFailNumber() {
        return taskFailNumber;
    }

    /**
     * 设置任务导入错误条数
     *
     * @param taskFailNumber 任务导入错误条数
     */
    public void setTaskFailNumber(Integer taskFailNumber) {
        this.taskFailNumber = taskFailNumber;
    }

    /**
     * 获取上传文件id
     *
     * @return file_id - 上传文件id
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * 设置上传文件id
     *
     * @param fileId 上传文件id
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * 获取任务导入错误表下载文件id
     *
     * @return fail_file_id - 任务导入错误表下载文件id
     */
    public String getFailFileId() {
        return failFileId;
    }

    /**
     * 设置任务导入错误表下载文件id
     *
     * @param failFileId 任务导入错误表下载文件id
     */
    public void setFailFileId(String failFileId) {
        this.failFileId = failFileId;
    }

    /**
     * 获取任务导入成功条数
     *
     * @return task_success_number - 任务导入成功条数
     */
    public Integer getTaskSuccessNumber() {
        return taskSuccessNumber;
    }

    /**
     * 设置任务导入成功条数
     *
     * @param taskSuccessNumber 任务导入成功条数
     */
    public void setTaskSuccessNumber(Integer taskSuccessNumber) {
        this.taskSuccessNumber = taskSuccessNumber;
    }

    /**
     * 获取任务导入结束时间
     *
     * @return task_end_time - 任务导入结束时间
     */
    public Date getTaskEndTime() {
        return taskEndTime;
    }

    /**
     * 设置任务导入结束时间
     *
     * @param taskEndTime 任务导入结束时间
     */
    public void setTaskEndTime(Date taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    /**
     * 获取任务导入总用时(单位ms)
     *
     * @return task_total_time - 任务导入总用时(单位ms)
     */
    public Long getTaskTotalTime() {
        return taskTotalTime;
    }

    /**
     * 设置任务导入总用时(单位ms)
     *
     * @param taskTotalTime 任务导入总用时(单位ms)
     */
    public void setTaskTotalTime(Long taskTotalTime) {
        this.taskTotalTime = taskTotalTime;
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
}