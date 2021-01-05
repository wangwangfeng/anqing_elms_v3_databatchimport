package com.zfsoft.batchimport.domain.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 附件信息表
 * 
 * @author chenyq
 * @date 20200426
 */
@Table(name = "T_SYS_ATTA")
public class SysAtta {
    private static final long serialVersionUID = 4629015605862041554L;

    /*主键*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OID")
    private String oid;

    /*附件名称*/
    @Column(name = "NAME", length = 200)
    private String name;

    /*附件原始名称*/
    @Column(name = "ORIGIN_NAME", length = 400)
    private String originName;

    /*附件地址*/
    @Column(name = "FILE_PATH", length = 300)
    private String filePath;

    /*附件扩展名*/
    @Column(name = "EXTENSION_NAME", length = 50)
    private String extensionName;

    /*上传时间*/
    @Column(name = "UPLOAD_DATE", length = 200)
    private Date uploadDate;

    /*接收人编号*/
    @Column(name = "USER_OID", length = 32)
    private String userOid;

    /*删除状态*/
    @Column(name = "IS_DELETE", length = 1)
    private String isDelete;


    /*数科套转接口转pdf时传输记录*/
    @Column(name = "SK_TICKET", length = 1)
    private String skTicket;


    /*数科套转接口转pdf时传输记录*/
    @Column(name = "FASTDFS_PATH", length = 1)
    private String fastdfsPath;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUserOid() {
        return userOid;
    }

    public void setUserOid(String userOid) {
        this.userOid = userOid;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getSkTicket() {
        return skTicket;
    }

    public void setSkTicket(String skTicket) {
        this.skTicket = skTicket;
    }

    public String getFastdfsPath() {
        return fastdfsPath;
    }

    public void setFastdfsPath(String fastdfsPath) {
        this.fastdfsPath = fastdfsPath;
    }
}
