package com.zfsoft.batchimport.mapper.original;


import com.zfsoft.batchimport.domain.entity.TTSysAtta;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TTSysAttaMapper extends Mapper<TTSysAtta> {
    /**
     * 根据长度查询数据
     * @param start
     * @param len
     * @return
     */
    List<TTSysAtta> selectByNum(@Param("start") Integer start, @Param("len") Integer len);

    /**
     * 查询所有未处理附件数据
     * @return
     */
    List<TTSysAtta> selectALLAttaOid(@Param("start") Integer start, @Param("len") Integer len);
}