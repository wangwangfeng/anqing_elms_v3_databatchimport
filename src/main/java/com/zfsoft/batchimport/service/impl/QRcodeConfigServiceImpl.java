package com.zfsoft.batchimport.service.impl;

import com.zfsoft.batchimport.common.BaseStaticParameter;
import com.zfsoft.batchimport.domain.entity.QRcodeConfig;
import com.zfsoft.batchimport.mapper.elms.QRcodeConfigMapper;
import com.zfsoft.batchimport.service.IQRcodeConfigService;
import com.zfsoft.batchimport.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 二维码配置信息表 service类
 * 
 * @author : chenyq
 * @date : 2020-11-03
 */

@Service
@RestControllerAdvice
public class QRcodeConfigServiceImpl   implements IQRcodeConfigService {
	private static final long serialVersionUID = 1L;

	/**
	 * 二维码配置信息表 mapper
	 */
	@Autowired
	private QRcodeConfigMapper qRcodeConfigMapper;

	@Override
	public List getQRcodeConfigByOrganAndDirectoryOid(String licenseTypeOid,String configType) throws Exception{
		return qRcodeConfigMapper.getQRcodeConfigByOrganAndDirectoryOid(licenseTypeOid,configType);
	}
	@Override
	public String saveQRcodeConfig(String deleteQrcodeOids ,String qrcodeOids,String licenseTypeOid, HttpServletRequest request)throws Exception{
		if (StringUtil.isEmpty(qrcodeOids)) {
			return "二维码配置信息不能为空！";
		}

		String[] qos = qrcodeOids.split(",");
		List<QRcodeConfig> QRcodeConfigList = new ArrayList<QRcodeConfig>();
		for (String qo : qos) {
			String qrcodeOid = null;
			if (StringUtil.isEmpty(qo)) {
				continue;
			}
			// 当主键编号的长度为32位时，说明为修改字段，否则为新增字段
			if (qo.length() == 32) {
				qrcodeOid = qo;
			}
			QRcodeConfig qRcodeConfig = new QRcodeConfig();
			qRcodeConfig.setQrcodeOid(qrcodeOid);
			qRcodeConfig.setLicenseTypeOid(licenseTypeOid);

			String configName = request.getParameter("configName_" + qo);
			qRcodeConfig.setConfigName(configName.trim());
			String configType = request.getParameter("configType_" + qo);
			qRcodeConfig.setConfigType(configType);

			String configCode = request.getParameter("configCode_" + qo);
			qRcodeConfig.setConfigCode(configCode);

			int paixu = Integer.valueOf(request.getParameter("paixu_" + qo));
			qRcodeConfig.setPaixu(paixu);
			qRcodeConfig.setCreateDate(new Date());
			qRcodeConfig.setModifyDate(new Date());
			qRcodeConfig.setDelFlag("N");
			qRcodeConfigMapper.insert (qRcodeConfig);
		}

		// 处理删除的二维码配置
		if (StringUtil.isNotEmpty(deleteQrcodeOids)) {
			String[] dqos = deleteQrcodeOids.split(",");
			for (String qo : dqos) {
				QRcodeConfig oldqm = null;
				if (StringUtil.isNotEmpty(qo) && qo.length() == 32) {
					oldqm = qRcodeConfigMapper.selectByPrimaryKey(qo);
				}
				if (oldqm != null) {
					oldqm.setDelFlag(BaseStaticParameter.YES);
					qRcodeConfigMapper.updateByPrimaryKey (oldqm);
				}
			}
		}
		return null;
	}
}
