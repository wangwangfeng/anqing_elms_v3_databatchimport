package com.zfsoft.batchimport.mapper.elms;


import com.zfsoft.batchimport.domain.entity.ElecLicenseLiceFile;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ElecLicenseLiceFileMapper extends Mapper<ElecLicenseLiceFile> {
    /**
     * 根据证照主键查询证照的文件
     * @author chenyq
     * @date 20200426
     * @param elecLicenOid 证照主键
     * @return
     * @throws Exception
     */
    List<ElecLicenseLiceFile> queryElecLicenseLiceFileList(@Param("elecLicenOid") String elecLicenOid) throws Exception;

    /**
     * 保存证照文件
     * @author chenyq
     * @date 20200426
     * @param elecLicenseLiceFile 证照文件
     * @return
     * @throws Exception
     */
//    void saveOrUpdateElecLicenseLiceFile(ElecLicenseLiceFile elecLicenseLiceFile) throws Exception;
}