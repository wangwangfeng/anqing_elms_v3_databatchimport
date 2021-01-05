package com.zfsoft.batchimport.utils.enm;

/**
 * @author wangsh
 * @description 照面元素值类型枚举
 * @date 2019-12-11
 * @return
 */
public enum ParamType {
    STRING, DATE, FILE;

    @Override
    public String toString() {
        return this.name();
    }

    public boolean equals(String key) {
        return this.name().equals(key);
    }

    public boolean equals(ParamType type) {
        return this.ordinal() == type.ordinal();
    }
}
