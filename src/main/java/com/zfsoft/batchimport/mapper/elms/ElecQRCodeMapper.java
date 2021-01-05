package com.zfsoft.batchimport.mapper.elms;


import com.zfsoft.batchimport.domain.entity.ElecQRCode;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ElecQRCodeMapper extends Mapper<ElecQRCode> {


    /**
     * 查询数据库中当前模板的底图宽度用来生成证照
     * @author chenyq
     * @date 20200426
     * @param underlayOid 底图主键
     */
    String getUnderlayWidthMm(@Param("underlayOid")  String underlayOid)throws Exception;
}