package com.zfsoft.batchimport.mapper.elms;


import com.zfsoft.batchimport.domain.entity.ElecLicenseMainTable;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ElecLicenseMainTableMapper extends Mapper<ElecLicenseMainTable> {
    ElecLicenseMainTable viewElecLicenseMainTable(@Param("tableOid") String tableOid) throws Exception;
}