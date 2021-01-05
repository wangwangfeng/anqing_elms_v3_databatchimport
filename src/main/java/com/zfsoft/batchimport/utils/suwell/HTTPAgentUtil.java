package com.zfsoft.batchimport.utils.suwell;

import com.github.pagehelper.util.StringUtil;
import com.suwell.ofd.custom.agent.HTTPAgent;
import com.suwell.ofd.custom.wrapper.Const;
import com.suwell.ofd.custom.wrapper.Packet;
import com.suwell.ofd.custom.wrapper.model.MarkPosition;
import com.suwell.ofd.custom.wrapper.model.Template;
import com.suwell.ofd.custom.wrapper.model.TextInfo;
import com.zfsoft.batchimport.common.BaseStaticParameter;
import com.zfsoft.batchimport.domain.entity.LicenseMetadata;
import com.zfsoft.batchimport.service.impl.SysAttaServiceImpl;
import com.zfsoft.batchimport.utils.AbstractFileTool;
import com.zfsoft.batchimport.utils.Base64FileUitl;
import com.zfsoft.batchimport.common.SpringBeanUtil;
import com.zfsoft.batchimport.domain.dto.CommonTableFieldDto;
import com.zfsoft.batchimport.domain.entity.SysAtta;
import com.zfsoft.batchimport.utils.DateUtil;
import com.zfsoft.batchimport.utils.PropertiesUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;

/**
 * 数科套转接口
 * 依赖jar:packet-wrapper-1.6.18.0105.jar、http-agent-1.1.17.628.jar、
 * agent-wrapper-1.2.17.1103.jar、httpclient-4.5.3.jar、httpcore-4.4.6.jar、
 * httpmime-4.5.3.jar、commons-compress-1.12.jar
 * @author chenyq
 * @date 2020-05-20
 */
public class HTTPAgentUtil {
	private static String AGENT_URL = null;
	public static String WEB_READER_URL = null;

	/**
	 * 二维码字段配置数组
	 */
	public static String QRCodeURL = null;
	static HTTPAgent ha = null;
	static {
		PropertiesUtil env = new PropertiesUtil("prop/common.properties");
		PropertiesUtil pu = new PropertiesUtil("prop/"+env.readProperty ("profiles.env")+"/suwell.properties");

		AGENT_URL = pu.readProperty("HTTPAgent.url");
		WEB_READER_URL = pu.readProperty("WebReader.url");
		QRCodeURL = pu.readProperty("Elms.QRCodeURL");
	}


	/**
	 * 单页模板转换-多模板合并
	 * 加密
	 */
	public static Map<String, String> convertAse(SysAtta[] ofdPathAttas, InputStream[] xmlInputStreams, String relSavePath, String ofdName,
												 String firstUnderlayWith, String waterMark) throws Exception {
        if(ha ==null){
        	ha =  new HTTPAgent(AGENT_URL);
		}
		String fileName = StringUtil.isNotEmpty(ofdName) ? ofdName : UUID.randomUUID().toString() + ".ofd";
//		String fileRelDir = StringUtil.isNotEmpty(relSavePath) ? relSavePath : "temp/ofd/";

		String fileRelDir = "D:temp/ofd/";
		Packet packet = new Packet("common.template", Const.Target.OFD);
		AbstractFileTool tool= new AbstractFileTool();
		InputStream[] temps = new InputStream[ofdPathAttas.length];
		for (int i = 0; i < ofdPathAttas.length; i++) {
			if (ofdPathAttas[i] == null) {
				continue;
			}
			temps[i] = tool.getFileInputStreamByAtta(ofdPathAttas[i]);
			Template template = new Template("单页套转", temps[i], xmlInputStreams[i]);
			packet.data(template);
		}

		if (StringUtil.isNotEmpty(waterMark)) {
			packet.textMark(new TextInfo(waterMark, "宋体", 20,null,-45, Const.XAlign.Center, Const.YAlign.Middle),
					new MarkPosition(MarkPosition.INDEX_ALL, Const.PatternType.Tile));
		}
		try {
			//证照开始生成标识
			System.out.println("convertAse2证照开始生成========================");
			SysAtta atta = new SysAtta();
			Map<String, String> ret = new HashMap<>();
			packet.metadata(Const.Meta.CUSTOM_DATAS, "test1=" + fileRelDir+";"+ BaseStaticParameter.UPLOAD_TYPE);
			packet.metadata(Const.Meta.DOC_ID, fileName+";"+fileName);//文件名称+;+文件原始名称
			packet.fileHandler("suwell-custom-handler-suwell-company", "cpcns.convert.mc.impl.FileHandlerBridgeXZ", null);
			//中间程序生成ofd chenyq
			long start = System.currentTimeMillis();

			String tak = ha.submit(packet);
			if(StringUtil.isEmpty(tak)){
				ret.put("suwellFlag", "false");
				return ret;
			}
			long end = System.currentTimeMillis();
			System.out.println("end-start===="+(start-end));

			atta.setSkTicket(tak);
			//证照生成结束
			System.out.println("convertAse2证照生成结束========================");
			atta.setIsDelete(BaseStaticParameter.NO);
			atta.setExtensionName("ofd");
			atta.setFilePath(fileRelDir);
			atta.setName(fileName);
			atta.setOriginName(fileName);
			atta.setUploadDate(new Date());
			atta.setUserOid(null);
			SysAttaServiceImpl sysAttaService = (SysAttaServiceImpl) SpringBeanUtil.getAppContext().getBean("sysAttaServiceImpl");
			sysAttaService.saveOrUpdateSysAtta(atta);


			String path = fileRelDir + "\\" + fileName.replace(".ofd", ".pdf");
			ret.put("filePDFRelDir", DigestUtils.md5Hex(path));
			ret.put("suwellFlag", "true");
			ret.put("attaOid", atta.getOid());
			return ret;
		/*} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();*/
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			try {
//				ha.close();
				if(temps !=null && temps.length>0) {
					for(int i = 0;i<temps.length;i++) {
						if(temps[i] != null ) {
							temps[i].close();
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 单页模板转换-多模板合并
	 * 加密
	 */
	public static Map<String, String> convertAseForWeb(SysAtta[] ofdPathAttas, InputStream[] xmlBaomiPaths, String relSavePath, String ofdName,
													   String firstUnderlayWith, String waterMark) throws Exception {

		HTTPAgent ha = new HTTPAgent(AGENT_URL);
		String fileName = StringUtil.isNotEmpty(ofdName) ? ofdName : UUID.randomUUID().toString() + ".ofd";
		String fileRelDir = StringUtil.isNotEmpty(relSavePath) ? relSavePath : "temp/ofd/";
		String finalOfdFilePath = "elms/";
		Packet packet = new Packet("common.template", Const.Target.OFD);

		AbstractFileTool tool= new AbstractFileTool();
		for (int i = 0; i < ofdPathAttas.length; i++) {
			if (ofdPathAttas[i] == null) {
				continue;
			}
			InputStream temp = tool.getFileInputStreamByAtta(ofdPathAttas[i]);
			Template template = new Template("单页套转", temp, xmlBaomiPaths[i]);
			packet.data(template);
//			temp.close();
		}

		if (StringUtil.isNotEmpty(waterMark)) {
			packet.textMark(new TextInfo(waterMark, "宋体", 20),
					new MarkPosition(10, 10, 10, 10, MarkPosition.INDEX_ALL));
		}

		try {
			//保密证照生成开始
			System.out.println("convertAse保密证照生成开始========================");
			SysAtta atta = new SysAtta();
			packet.metadata(Const.Meta.CUSTOM_DATAS, "test1=" + finalOfdFilePath+";"+BaseStaticParameter.UPLOAD_TYPE);
			packet.metadata(Const.Meta.DOC_ID, fileName);
			packet.fileHandler("suwell-custom-handler-suwell-company", "cpcns.convert.mc.impl.FileHandlerBridgeXZ", null);
			String tak = ha.submit(packet);
			atta.setSkTicket(tak);
			//保密证照生成结束
			System.out.println("convertAse保密证照生成结束========================");
			atta.setIsDelete(BaseStaticParameter.NO);
			atta.setExtensionName("ofd");
			atta.setFilePath(fileRelDir);
			atta.setName(fileName);
			atta.setOriginName(fileName);
			atta.setUploadDate(new Date());
			atta.setUserOid(null);
			SysAttaServiceImpl sysAttaService = (SysAttaServiceImpl) SpringBeanUtil.getAppContext().getBean("sysAttaServiceImpl");
			sysAttaService.saveOrUpdateSysAtta(atta);
			Map<String, String> ret = new HashMap<>();
			ret.put("attaOid", atta.getOid());
			return ret;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ha.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}


	/**
	 * ofd合成
	 * 组装xml
	 * @param list
	 * @param paramMap 照面元素
	 * @return
	 * @throws Exception
	 */
	public static InputStream toOfdXmlInputStream(List<LicenseMetadata> list, Map<String, String> paramMap, String elecQRCodeSavePath) throws Exception {

		Document doc = DocumentHelper.createDocument();
		doc.addComment("ofdxml");
		Element root = doc.addElement("DataRoot");
		root.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");

		for (int i = 0; i < list.size(); i++) {
			LicenseMetadata md = list.get(i);
			if (md.getReplaceFlag() == null || md.getReplaceFlag().equals(BaseStaticParameter.REPLACE_FLAG_Y)) {
				Element dataInfo = root.addElement("DataInfo");
				dataInfo.addAttribute("Name", md.getColumnName());
				dataInfo.addAttribute("Value", md.getMetadataName());
			} else {
				String key = md.getColumnName();
				String value = paramMap.get(key);
				Element dataInfo = root.addElement("DataInfo");
				dataInfo.addAttribute("Name", md.getColumnName());
				if ("file".equals(md.getColumnType())) {
					String qrField = QRCodeURL;
					String[] qrFieldArray = qrField.split(",");
					Boolean isEWM = false;
					for(String qrf : qrFieldArray) {
						if (md.getColumnName().equals(qrf)&&StringUtil.isNotEmpty(elecQRCodeSavePath)&&md.getMetadataName().contains("二维码")) {
							dataInfo.addAttribute("Value", Base64FileUitl.encodeBase64AllFile(elecQRCodeSavePath));
							isEWM = true;
							break;
						}
					}
					if(!isEWM) {
						if (StringUtil.isEmpty(value)) {
							continue;
						}
						String attaOid = value;
						SysAttaServiceImpl sysAttaService = (SysAttaServiceImpl) SpringBeanUtil.getAppContext().getBean("sysAttaServiceImpl");
						SysAtta atta = sysAttaService.viewSysAtta(attaOid);
						dataInfo.addAttribute("Value", Base64FileUitl.encodeBase64AllFile(atta.getFastdfsPath()));
					}
				} else {
					dataInfo.addAttribute("Value", value);
				}
				// 日期类型
				if ("date".equals(md.getColumnType()) && !StringUtil.isEmpty(value)) {
					Date d = DateUtil.str2Date(value, DateUtil.date_sdf);
					// 年
					Element dataInfo1 = root.addElement("DataInfo");
					dataInfo1.addAttribute("Name", md.getColumnName() + ":Y");
					dataInfo1.addAttribute("Value", DateUtil.formatDate(d, "yyyy"));
					// 月
					Element dataInfo2 = root.addElement("DataInfo");
					dataInfo2.addAttribute("Name", md.getColumnName() + ":M");
					dataInfo2.addAttribute("Value", DateUtil.formatDate(d, "MM"));
					// 日
					Element dataInfo3 = root.addElement("DataInfo");
					dataInfo3.addAttribute("Name", md.getColumnName() + ":D");
					dataInfo3.addAttribute("Value", DateUtil.formatDate(d, "dd"));
				}
			}
		}
		InputStream inputStream = new ByteArrayInputStream(doc.asXML().getBytes("utf-8"));
		return inputStream;
	}

	/**
	 * ofd合成
	 * 组装xml
	 * @param commonTableFieldDtoList
	 * @param paramMap 照面元素
	 * @return
	 * @throws Exception
	 */
	public static InputStream toOfdXmlExcelInputStream(List<CommonTableFieldDto> commonTableFieldDtoList, Map<String, String> paramMap, String elecQRCodeSavePath) throws Exception {

		Document doc = DocumentHelper.createDocument();
		doc.addComment("ofdxml");
		Element root = doc.addElement("DataRoot");
		root.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");

		for (int i = 0; i < commonTableFieldDtoList.size(); i++) {
			CommonTableFieldDto md = commonTableFieldDtoList.get(i);
			if (md.getReplaceFlag() == null || md.getReplaceFlag().equals(BaseStaticParameter.REPLACE_FLAG_Y)) {
				Element dataInfo = root.addElement("DataInfo");
				dataInfo.addAttribute("Name", md.getFieldCode());
				dataInfo.addAttribute("Value", md.getFieldName());
			} else {
				String key = md.getFieldCode();
				String value = paramMap.get(key);
				Element dataInfo = root.addElement("DataInfo");
				dataInfo.addAttribute("Name", md.getFieldCode());
				if ("file".equals(md.getFieldType())) {
					String qrField = QRCodeURL;
					String[] qrFieldArray = qrField.split(",");
					Boolean isEWM = false;
					for(String qrf : qrFieldArray) {
						if (md.getFieldCode().equals(qrf)&&StringUtil.isNotEmpty(elecQRCodeSavePath)&&md.getFieldName().contains("二维码")) {
							dataInfo.addAttribute("Value", Base64FileUitl.encodeBase64AllFile(elecQRCodeSavePath));
							isEWM = true;
							break;
						}
					}
					if(!isEWM) {
						if (StringUtil.isEmpty(value)) {
							continue;
						}
						String attaOid = value;
						SysAttaServiceImpl sysAttaService = (SysAttaServiceImpl) SpringBeanUtil.getAppContext().getBean("sysAttaServiceImpl");
						SysAtta atta = sysAttaService.viewSysAtta(attaOid);
						dataInfo.addAttribute("Value", Base64FileUitl.encodeBase64AllFile(atta.getFastdfsPath()));
					}
				} else {
					dataInfo.addAttribute("Value", value);
				}
				// 日期类型
				if ("date".equals(md.getFieldType()) && !StringUtil.isEmpty(value)) {
					Date d = DateUtil.str2Date(value, DateUtil.date_sdf);
					// 年
					Element dataInfo1 = root.addElement("DataInfo");
					dataInfo1.addAttribute("Name", md.getFieldCode() + ":Y");
					dataInfo1.addAttribute("Value", DateUtil.formatDate(d, "yyyy"));
					// 月
					Element dataInfo2 = root.addElement("DataInfo");
					dataInfo2.addAttribute("Name", md.getFieldCode() + ":M");
					dataInfo2.addAttribute("Value", DateUtil.formatDate(d, "MM"));
					// 日
					Element dataInfo3 = root.addElement("DataInfo");
					dataInfo3.addAttribute("Name", md.getFieldCode() + ":D");
					dataInfo3.addAttribute("Value", DateUtil.formatDate(d, "dd"));
				}
			}
		}
		InputStream inputStream = new ByteArrayInputStream(doc.asXML().getBytes("utf-8"));
		return inputStream;
	}

	/**
	 * ofd合成
	 * 组装xml
	 * 用于网站浏览
	 *生成保密xml用于网站展示保密证照chenyq
	 * @param list   照面元素
	 * @param valmap 照面值
	 * @return
	 * @throws Exception
	 */
	public static InputStream toWebOfdXmlBaomiInputStream(List<LicenseMetadata> list, Map<String, String> valmap) throws Exception {
		Document doc = DocumentHelper.createDocument();
		doc.addComment("ofdxml");
		Element root = doc.addElement("DataRoot");
		root.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");

		for (int i = 0; i < list.size(); i++) {
			LicenseMetadata md = list.get(i);
			String columnName = md.getColumnName();
			String metadataName = md.getMetadataName();
			if (md.getReplaceFlag() == null || md.getReplaceFlag().equals(BaseStaticParameter.REPLACE_FLAG_Y)) {
				Element dataInfo = root.addElement("DataInfo");
				dataInfo.addAttribute("Name", columnName);
				dataInfo.addAttribute("Value", metadataName);
				// 日期类型
				if ("date".equals(md.getColumnType())) {
					//年
					Element dataInfo1 = root.addElement("DataInfo");
					dataInfo1.addAttribute("Name", columnName + ":Y");
					dataInfo1.addAttribute("Value", metadataName + ":年");
					//月
					Element dataInfo2 = root.addElement("DataInfo");
					dataInfo2.addAttribute("Name", columnName + ":M");
					dataInfo2.addAttribute("Value", metadataName + ":月");
					//日
					Element dataInfo3 = root.addElement("DataInfo");
					dataInfo3.addAttribute("Name", columnName + ":D");
					dataInfo3.addAttribute("Value", metadataName + ":日");
				}
			} else {
				String key = md.getColumnName();
				String value = valmap == null ? "" : valmap.get(key) + "";
				if (StringUtil.isNotEmpty(value)) {
					value = value.equals("null") ? "" : value;
				}

				Element dataInfo = root.addElement("DataInfo");
				dataInfo.addAttribute("Name",columnName);

				if ("file".equals(md.getColumnType())) {
					if ("null".equals(value) || StringUtil.isEmpty(value) || BaseStaticParameter.COMFIDENTIAL_FLAG_N.equals(md.getComfidentialFlag())) {
						continue;
					}
					String attaOid = value;
					SysAttaServiceImpl sysAttaService = (SysAttaServiceImpl) SpringBeanUtil.getAppContext().getBean("sysAttaServiceImpl");
					SysAtta atta = sysAttaService.viewSysAtta(attaOid);
					dataInfo.addAttribute("Value", Base64FileUitl.encodeBase64AllFile(atta.getFastdfsPath()));
				} else {
					if ( BaseStaticParameter.COMFIDENTIAL_FLAG_N.equals(md.getComfidentialFlag()) && StringUtil.isNotEmpty(value)) {
						String tempValue = value.substring(0, 1) + "**";
						dataInfo.addAttribute("Value", tempValue);
					} else {
						dataInfo.addAttribute("Value", value);
					}
				}
				// 日期类型
				if ("date".equals(md.getColumnType()) && valmap != null && StringUtil.isNotEmpty(value) && !"null".equals(value)) {
					Date d = DateUtil.str2Date(value, DateUtil.date_sdf);
					// 年
					Element dataInfo1 = root.addElement("DataInfo");
					dataInfo1.addAttribute("Name", columnName + ":Y");
					dataInfo1.addAttribute("Value", DateUtil.formatDate(d, "yyyy").substring(0, 1) + "**");
					// 月
					Element dataInfo2 = root.addElement("DataInfo");
					dataInfo2.addAttribute("Name", columnName + ":M");
					dataInfo2.addAttribute("Value", DateUtil.formatDate(d, "MM").substring(0, 1) + "**");
					// 日
					Element dataInfo3 = root.addElement("DataInfo");
					dataInfo3.addAttribute("Name", columnName + ":D");
					dataInfo3.addAttribute("Value", DateUtil.formatDate(d, "dd").substring(0, 1) + "**");
				}
			}
		}
		InputStream inputStream = new ByteArrayInputStream(doc.asXML().getBytes("utf-8"));
		return inputStream;
	}
	public static InputStream toWebOfdXmlBaomiExcelInputStream(List<CommonTableFieldDto> commonTableFieldDtoList, Map<String, String> valmap) throws Exception {
		Document doc = DocumentHelper.createDocument();
		doc.addComment("ofdxml");
		Element root = doc.addElement("DataRoot");
		root.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");

		for (int i = 0; i < commonTableFieldDtoList.size(); i++) {
			CommonTableFieldDto md = commonTableFieldDtoList.get(i);
			String columnName = md.getFieldCode();
			String metadataName = md.getFieldName();
			if (md.getReplaceFlag() == null || md.getReplaceFlag().equals(BaseStaticParameter.REPLACE_FLAG_Y)) {
				Element dataInfo = root.addElement("DataInfo");
				dataInfo.addAttribute("Name", columnName);
				dataInfo.addAttribute("Value", metadataName);
				// 日期类型
				if ("date".equals(md.getFieldType())) {
					//年
					Element dataInfo1 = root.addElement("DataInfo");
					dataInfo1.addAttribute("Name", columnName + ":Y");
					dataInfo1.addAttribute("Value", metadataName + ":年");
					//月
					Element dataInfo2 = root.addElement("DataInfo");
					dataInfo2.addAttribute("Name", columnName + ":M");
					dataInfo2.addAttribute("Value", metadataName + ":月");
					//日
					Element dataInfo3 = root.addElement("DataInfo");
					dataInfo3.addAttribute("Name", columnName + ":D");
					dataInfo3.addAttribute("Value", metadataName + ":日");
				}
			} else {
				String key = md.getFieldCode();
				String value = valmap == null ? "" : valmap.get(key) + "";
				if (StringUtil.isNotEmpty(value)) {
					value = value.equals("null") ? "" : value;
				}

				Element dataInfo = root.addElement("DataInfo");
				dataInfo.addAttribute("Name",columnName);

				if ("file".equals(md.getFieldType())) {
					if ("null".equals(value) || StringUtil.isEmpty(value) || BaseStaticParameter.COMFIDENTIAL_FLAG_N.equals(md.getComfidentialFlag())) {
						continue;
					}
					String attaOid = value;
					SysAttaServiceImpl sysAttaService = (SysAttaServiceImpl) SpringBeanUtil.getAppContext().getBean("sysAttaServiceImpl");
					SysAtta atta = sysAttaService.viewSysAtta(attaOid);
					dataInfo.addAttribute("Value", Base64FileUitl.encodeBase64AllFile(atta.getFastdfsPath()));
				} else {
					if ( BaseStaticParameter.COMFIDENTIAL_FLAG_N.equals(md.getComfidentialFlag()) && StringUtil.isNotEmpty(value)) {
						String tempValue = value.substring(0, 1) + "**";
						dataInfo.addAttribute("Value", tempValue);
					} else {
						dataInfo.addAttribute("Value", value);
					}
				}
				// 日期类型
				if ("date".equals(md.getFieldType()) && valmap != null && StringUtil.isNotEmpty(value) && !"null".equals(value)) {
					Date d = DateUtil.str2Date(value, DateUtil.date_sdf);
					// 年
					Element dataInfo1 = root.addElement("DataInfo");
					dataInfo1.addAttribute("Name", columnName + ":Y");
					dataInfo1.addAttribute("Value", DateUtil.formatDate(d, "yyyy").substring(0, 1) + "**");
					// 月
					Element dataInfo2 = root.addElement("DataInfo");
					dataInfo2.addAttribute("Name", columnName + ":M");
					dataInfo2.addAttribute("Value", DateUtil.formatDate(d, "MM").substring(0, 1) + "**");
					// 日
					Element dataInfo3 = root.addElement("DataInfo");
					dataInfo3.addAttribute("Name", columnName + ":D");
					dataInfo3.addAttribute("Value", DateUtil.formatDate(d, "dd").substring(0, 1) + "**");
				}
			}
		}
		InputStream inputStream = new ByteArrayInputStream(doc.asXML().getBytes("utf-8"));
		return inputStream;
	}
}
