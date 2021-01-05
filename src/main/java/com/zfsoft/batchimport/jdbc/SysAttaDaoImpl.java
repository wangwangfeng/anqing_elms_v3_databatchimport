package com.zfsoft.batchimport.jdbc;

import com.zfsoft.batchimport.domain.entity.TTSysAtta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class SysAttaDaoImpl implements SysAttaDao{


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public int batchUpdate(List<TTSysAtta> attaOids, int type) {
        String sql;
        if(type == 1){
            sql = "update t_sys_atta set SYN_STATUS = 'Y' where oid = ?";
        }else {
            sql = "update t_sys_atta set SYN_STATUS = 'F' where oid = ?";
        }
        int[][] updateCounts = jdbcTemplate.batchUpdate(sql,attaOids,attaOids.size(),
                (ParameterizedPreparedStatementSetter) (preparedStatement, o) -> {
            TTSysAtta sysAtta = (TTSysAtta) o;
            System.out.println("更新处理oid = " +  sysAtta.getOid());
            preparedStatement.setString(1, sysAtta.getOid());
        });
        return  updateCounts.length;
    }

    @Override
    public int batchSuccessUpdate(List<TTSysAtta> attaOids) {
        return  batchUpdate(attaOids,1);
    }

    @Override
    public int batchFailUpdate(List<TTSysAtta> attaOids) {
        return  batchUpdate(attaOids,0);
    }
}
