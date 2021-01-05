package com.zfsoft.batchimport.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * Base64 操作类
 * @author dusd
 * @date 2016年12月15日
 */
public class Base64FileUitl {
	
	/**
	 * 将文件转成base64 字符串
	 * 
	 * @param path 文件路径
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64File(String path){
		byte[] buffer = null;
		FileInputStream inputFile = null;
		String pdfBase64Str = null;
		try {
			File file = new File(path);
			inputFile = new FileInputStream(file);
			buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
			pdfBase64Str = new BASE64Encoder().encode(buffer);
			pdfBase64Str = pdfBase64Str.replace("\n", "").replace("\t", "").replace("\r", "");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pdfBase64Str;
	}

	/**
	 * 将文件转成base64 字符串(包含项目、本地磁盘、ftp、统一文件等路径整合)
	 * 
	 * @param path 文件路径
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64AllFile(String path){
		byte[] buffer = null;
		String pdfBase64Str = null;
		InputStream inputStream = null;
		try {
			inputStream = new AbstractFileTool().getFileInputStreamByRelativePathAndFileName(path);
			buffer = FileUtil.getStreamBytes(inputStream);
			pdfBase64Str = new BASE64Encoder().encode(buffer);
			pdfBase64Str = pdfBase64Str.replace("\n", "").replace("\t", "").replace("\r", "");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pdfBase64Str;
	}
	
	
	
	/**
	 * 将base64字符解码保存文件
	 * 
	 * @param base64Code
	 * @param targetPath
	 * @throws Exception
	 */
	public static void decoderBase64File(String base64Code, String targetPath) throws Exception {
		FileOutputStream out = null;
		try {
			byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
			out = new FileOutputStream(targetPath);
			out.write(buffer);
		} finally {
			if(out != null)
				out.close();
		}

	}

	/**
	 * 将base64字符保存文本文件
	 * 
	 * @param base64Code
	 * @param targetPath
	 * @throws Exception
	 */
	public static void toFile(String base64Code, String targetPath) throws Exception {

		byte[] buffer = base64Code.getBytes();
		FileOutputStream out = new FileOutputStream(targetPath);
		out.write(buffer);
		out.close();
	}
}
