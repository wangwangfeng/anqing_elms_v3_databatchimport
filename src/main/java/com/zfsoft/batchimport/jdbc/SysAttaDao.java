package com.zfsoft.batchimport.jdbc;

import com.zfsoft.batchimport.domain.entity.TTSysAtta;

import java.util.List;

public interface SysAttaDao {

    int batchUpdate(final List<TTSysAtta> attaOids,int type);

    int batchSuccessUpdate(final List<TTSysAtta> attaOids);

    int batchFailUpdate(final List<TTSysAtta> attaOids);
}
