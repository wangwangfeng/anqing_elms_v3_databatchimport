package com.zfsoft.batchimport.domain.entity;

import javax.persistence.*;


/**
 * 证照二维码表
 * 
 * @description
 * @author chenbw
 * @date 2018年6月21日
 * @Copyright 版权由上海卓繁信息技术股份有限公司拥有
 */
@Table(name = "T_ELEC_QR_CODE")
public class ElecQRCode{

    private static final long serialVersionUID = 784923370203624936L;

    /**
     * @COLUMN_EXPLAIN : 主键
     * @TABLE_COLUMN_TYPE : varchar
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OID")
    private String oid;

    /**
     * @COLUMN_EXPLAIN : 电子证照主键
     * @TABLE_COLUMN_TYPE : varchar
     */
    @Column(name = "ELEC_LICEN_OID")
    private String elecLicenOid;

    /**
     * @COLUMN_EXPLAIN : 二维码图片附件主键
     * @TABLE_COLUMN_TYPE : varchar
     */
    @Column(name = "ATTA_OID")
    private String attaOid;

    /**
     * @COLUMN_EXPLAIN : 二维码图片路径
     * @TABLE_COLUMN_TYPE : varchar
     */
    @Column(name = "IMG_PATH")
    private String imgPath;


    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getElecLicenOid() {
        return elecLicenOid;
    }

    public void setElecLicenOid(String elecLicenOid) {
        this.elecLicenOid = elecLicenOid;
    }

    public String getAttaOid() {
        return attaOid;
    }

    public void setAttaOid(String attaOid) {
        this.attaOid = attaOid;
    }


    public String getImgPath() {
        return imgPath;
    }


    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

}
