package com.zfsoft.batchimport.utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BusinessUtil {

	/**
	 * 电子证照固定oid  生成证照唯一标识使用
	 */
	public static final String ELECLICENSE_OID="1.2.156.3003.2";

	public enum Designation {

		/**
		 * 证照标识算法标识
		 */
		ISO_7064_MOD_37_HYBRID_36
	}

	/**
	 * 适用于字母数字的校验字符系统的数值对应表
	 *
	 * @author heric wan
	 * @date 2018-11-15
	 */
	private static final String[] ALPHANUMBERIC_STRINGS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B",
			"C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
			"X", "Y", "Z", "*" };

	public static String createVersionNumber(String curVersionNumber) {
		return "V3.00";
	}
	/**
	 *
	 * 根据当前日期获取流水号
	 * @author wuxx
	 * @date: 2018年11月15日 下午3:02:09
	 * @return
	 */
	public static String getFlowNumber(){
		String stringId="";
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		String temp = sf.format(new Date());
		int random=(int) (Math.random()*10000);
		stringId=temp+random;
		return stringId;
	}

	/**
	 * 通过递归法法计算混合系统证照标识符
	 *
	 * @author heric wan
	 * @date 2018-11-15
	 */
	public static String getCertificateIdentifier(String inputString, Designation designation) {
		int M = 0; // 模数1
		List<String> mapping = null;
		switch (designation) {
			case ISO_7064_MOD_37_HYBRID_36:
				M = 36;
				mapping = Arrays.asList(ALPHANUMBERIC_STRINGS);
				break;
			default:
				break;
		}
		int Mplus1 = M + 1; // 模数2
		char[] strArray = inputString.toCharArray();
		int S = 0;
		int P = M;
		int n = strArray.length + 1;
		for (int i = n; i >= 2; i--) {
			S = P + mapping.indexOf(String.valueOf(strArray[n - i]));
			P = ((S % M == 0 ? M : S % M) * 2) % Mplus1;
		}
		return inputString + mapping.get((M + 1 - P % M) % M);
	}
	/**
	 * 根据规则获取电子证照编码： 颁证单位代码类型（2位）+颁证单位代码+持证者代码类型（2位）+ 持证者代码+证照版本号（2位）+证照编号（8位）
	 *
	 * @author cbc
	 * @date 2016年12月2日
	 * @param organCodeType：颁证单位代码类型,
	 * @param organCode：颁证单位代码,
	 * @param personCodeType：持证者代码类型,
	 * @param personCode：持证者代码,
	 * @param versionNum：证照版本号,
	 * @param licenseCode：证照编号
	 *
	 * @return
	 */
	public static String getElecLicenseNumber(String organCodeType, String organCode, String personCodeType,
											  String personCode, String versionNum, String licenseCode) {
		if (versionNum == null || "".equals(versionNum)) {
			versionNum = "V1";
		}
		return organCodeType.trim() + organCode.trim() + personCodeType.trim() + personCode.trim()
				+ versionNum.substring(0, 2).trim() + licenseCode.trim();
	}
}
