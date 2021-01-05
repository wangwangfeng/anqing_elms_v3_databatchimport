package com.zfsoft.batchimport.base;


import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果
 */
public class PageResultBean<T> implements Serializable {

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 结果集
     */
    private List<T> rows;

    /**
     * 页数
     */
    private Integer pageNum;

    /**
     * 每页记录数
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer pages;

    public PageResultBean(List<T> rows) {
        init(rows);
    }

    /**
     * 初始化
     */
    private void init(List<T> rows) {
        if (rows instanceof Page) {
            Page<T> page = (Page<T>) rows;
            this.total = page.getTotal();
            this.rows = page.getResult();
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.pages = page.getPages();
        } else {
            this.rows = rows;
        }
    }

    public Long getTotal() {
        return total;
    }

    public PageResultBean<T> setTotal(Long total) {
        this.total = total;
        return this;
    }

    public List<T> getRows() {
        return rows;
    }

    public PageResultBean<T> setRows(List<T> rows) {
        this.rows = rows;
        return this;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public PageResultBean<T> setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public PageResultBean<T> setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Integer getPages() {
        return pages;
    }

    public PageResultBean<T> setPages(Integer pages) {
        this.pages = pages;
        return this;
    }
}
