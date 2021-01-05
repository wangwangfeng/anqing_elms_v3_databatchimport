package com.zfsoft.batchimport.mapper.elms;


import com.zfsoft.batchimport.domain.entity.ElecBaseMetadata;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ElecBaseMetadataMapper extends Mapper<ElecBaseMetadata> {

    List<ElecBaseMetadata> baseMetadataList();
}