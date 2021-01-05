package com.zfsoft.batchimport.mapper.middle;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;

import com.zfsoft.batchimport.domain.entity.ElecLicenseLiceFile;
import com.zfsoft.batchimport.domain.entity.ElecLicenseSbSubed;

import tk.mybatis.mapper.common.Mapper;

public interface ElecLicenseSbSubedMapper  extends Mapper<ElecLicenseSbSubed>{
	
	
	/**
	 * 
	 * @description:对于未交换的序列的上报数据按照分组每一个未交换完成的序列值
	 * @author yangcb
	 * @date: 2020年6月24日
	 * @return
	 */
	  List<Integer> getSubNumTimes();
	  
	  /**
	   * 
	   * @description:获取电子证照签发上报数据列表信息
	   * @author yangcb
	   * @date: 2020年6月24日
	   * @param subNumTime
	   * @return
	   */
	  List<ElecLicenseSbSubed> getElecLicenseSbSubedList(@Param("subNumTime") Integer subNumTime);
	  
	  /**
	   * 
	   * @description:更新上报数据信息推送状态
	   * @author yangcb
	   * @date: 2020年6月24日
	   * @param oid
	   */
	  void updateElecLicenseSbSubed(@Param("subOid") String oid);


}
