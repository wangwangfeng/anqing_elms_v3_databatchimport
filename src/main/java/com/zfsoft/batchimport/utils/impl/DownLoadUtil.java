package com.zfsoft.batchimport.utils.impl;

import com.zfsoft.batchimport.utils.FileUtil;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author wangsh
 * @description
 * @date 2019-9-26
 * @Copyright
 */
public class DownLoadUtil  {

    public static void downLoadFile(HttpServletResponse response, String filePath , String orginFileName ){
        if(StringUtil.isEmpty(filePath))return;
        File file = new File(filePath) ;
        if(!file.exists()) return;

        if(StringUtil.isEmpty(orginFileName))orginFileName = file.getName();
        try{
            downLoadFile(response, new FileInputStream(file),  orginFileName);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * @param
     * @return
     * @description 文件下载方法
     * @author wangsh
     * @date
     */
    public static void downLoadFile(HttpServletResponse response, InputStream fis, String orginFileName) {
        OutputStream outputStream = null;
        // 重置buffer
        response.resetBuffer();
        // 设定编码为UTF-8
        response.setCharacterEncoding("UTF-8");
        // 设置头部为下载信息
        response.setHeader("Content-type", "application/force-download;charset=UTF-8");
        /*response.setHeader("content-disposition",
                "attachment;filename=" + StringUtils.substringAfter(fileName, "UTF-8"));// 设置文件名*/
        try {
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(orginFileName, "UTF-8"));
            outputStream = response.getOutputStream();

            downLoadFile(outputStream , fis );
        } catch (Exception e) {
            response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
        }finally {
            try{
                if(fis!=null) fis.close();
            }catch (Exception e){

            }
        }
    }

    /**
     * @description  下载到指定位置
     * @author wangsh
     * @date
     * @param fis 文件流
     * @param aimPath 目标地址
     * @param newName 新文件名
     * @return
     */
    public static void downLoadFile(InputStream fis  , String aimPath , String newName) throws Exception {
        if(StringUtil.isEmpty(aimPath)) throw new Exception("无效的目标地址,无法下载");
        File aimFile = new File(aimPath);
        if(!aimFile.exists())aimFile.mkdirs();

        if(fis == null) throw new Exception("输入流为空,无法下载");

        FileOutputStream fos = new FileOutputStream(new File(aimPath , newName ));
        downLoadFile(fos , fis  );

        if(fos !=null){
            fos.flush();
            fos.close();
        }
    }

    public static void downLoadFile(OutputStream fos , InputStream fis  ){
        try {
            if(fis == null || fos == null ) return;
            FileUtil.copyStream(fis, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
