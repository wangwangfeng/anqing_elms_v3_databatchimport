package com.zfsoft.batchimport.mapper.elms;

import com.zfsoft.batchimport.domain.entity.LicenseMetadata;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface LicenseMetadataMapper extends Mapper<LicenseMetadata> {

    List<LicenseMetadata> getLicenseMetadataList(@Param("organOid") String organOid,@Param("directoryOid")  String directoryOid);


}