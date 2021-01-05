package com.zfsoft.batchimport.base;

import com.zfsoft.batchimport.common.BaseStaticParameter;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

/**
 * @author: kkfan
 * @create: 2020-01-15 22:50:08
 * @description:    排序实体
 */
public class SortFieldDto {

    /** 排序字段 */
    private String fieldName;

    /** 排序类型 asc 正序 desc倒序 */
    private String sortType;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        if(StringUtils.equalsAnyIgnoreCase(sortType, BaseStaticParameter.SORT_TYPE.ASC, BaseStaticParameter.SORT_TYPE.DESC)) {
            this.sortType = sortType;
        } else {
            throw new IllegalArgumentException(MessageFormat.format("sortType类型错误, asc -- 正序 desc -- 倒叙，输入：{0}", sortType));
        }
    }
}
