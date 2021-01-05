package com.zfsoft.batchimport.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Auther: kkfan
 * @Date: 2019/1/13 10:27
 * @Description: 基础实体类
 */
public class BaseModel {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == "" || id == null ? null : id.trim();
    }
}
