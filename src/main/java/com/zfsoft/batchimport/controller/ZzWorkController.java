package com.zfsoft.batchimport.controller;

import com.zfsoft.batchimport.domain.dto.ElecLicenseMainDataSubed;
import com.zfsoft.batchimport.service.AsynAttaFileService;
import com.zfsoft.batchimport.service.IZzWorkService;
import com.zfsoft.batchimport.utils.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
* @description: 电子证照接口
* @author: yangcb
* @date:2020年6月18日
* @version: Version 2.0 
* @Copyright: 版权由上海卓繁股份有限公司所有
 */
@Controller
@RequestMapping(value = "ws/zzWork")
public class ZzWorkController {

	/**
	 * 将日志打印到控制台
	 */
	public Logger log = LoggerFactory.getLogger(this.getClass());

	/**
     * 电子证照主表 DAO
     */
    @Autowired
    private IZzWorkService zzWorkService;

    @Autowired
	private AsynAttaFileService asynAttaFileService;


	/**
	 * 定时清理过期历史数据，将数据进行删除操作
     * 1、注销数据
     * 2、去重 （持证者id、证照编号、有效期起、有效期止）
     * 3、证照号码低于或者高于18位全部删除
	 * @author: chenyq
	 * @date:2020年8月29日
	 */
	@RequestMapping(value = "/QuartzJobHistoryElms.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public void QuartzJobHistoryElms() {
		try {
			// 开始时间
			long beginTime =  System.currentTimeMillis();
			//去es中取出需要清理的数据
			List list = zzWorkService.queryHistoryMainDataSubed();
			//循环数据并将数据进行上报
			if (list.size()>0){
				//业务逻辑start
				int m = list.size()/10+1;
				int n = list.size()/m;
				try {
					for(int i=0;i< n ;i++ ){
						List<ElecLicenseMainDataSubed> elec = list.subList(i*m,(i+1)*m);
						zzWorkService.doHistoryElms(elec);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 结束时间
			Long endTime = new Date().getTime();
			// 耗时
			System.out.println("数据处理完成，共用时 : " + (endTime - beginTime) / 1000 + " s");
		} catch (Exception e) {
			log.error("定时器定时清理过期历史数据，将数据进行删除操作出错", e);
		}
	}



	/**
	 *
	 * @description:
	 * @author yangcb
	 * @date: 2020年6月28日
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/suoYinShangBao.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object zzUpload(HttpServletResponse response) {
		String result = new String();
		try {
			result = zzWorkService.suoYinShangBao();
			System.out.println("调用省接口返回值：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@ResponseBody
    @RequestMapping("/startBatchImport.do")
    public String startBatchImport() throws Exception{
		asynAttaFileService.uploadAttaChment();
    	return "success";
	}
    
    public static void main(String[] args) {
		Map<String,String> mapParam = new HashMap<String,String>();
		String value=HttpClient.post("http://172.168.252.64:10091/batchimport/ws/zzWork/suoYinShangBao.do", mapParam);
		System.out.println(value);
	}
	
}
