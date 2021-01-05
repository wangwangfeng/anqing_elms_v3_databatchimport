package com.zfsoft.batchimport.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Auther: kkfan
 * @Date: 2019/4/5 14:58
 * @Description: 基础分页实体
 */
@ApiModel(value = "pageModel", description = "基础分页参数")
public class PageModel {

    @ApiModelProperty(value = "当前页", dataType = "Integer", required = false)
    @Min(value = 1, message = "当前页最小为1")
    @NotNull(message = "当前页不能为空")
    private Integer currentPageNo;

    @ApiModelProperty(value = "每页数量", dataType = "Integer", required = false)
    @Max(value = 100, message = "最大分页长度为100")
    @Min(value = 1, message = "分页长度最小为1")
    @NotNull(message = "每页数量不能为空")
    private Integer pageSize;

    public Integer getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(Integer currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
