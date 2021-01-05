package com.zfsoft.batchimport.mapper.elms;


import com.zfsoft.batchimport.domain.entity.ElecLicenseMain;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
public interface ElecLicenseMainMapper extends Mapper<ElecLicenseMain> {
    /**
     * 通过证照编号查询证照信息
     * @author chenyq
     * @date 20200426
     * @param licenseCode
     * @return
     */
    int existDataLicenseCode( @Param("licenseCode") String licenseCode);

    /**
     * 根据证照主键获取证照
     * @author chenyq
     * @date 20200426
     * @param elecLicenOid
     * @return
     */
    ElecLicenseMain viewElecLicenseMain( @Param("elecLicenOid") String elecLicenOid);

}