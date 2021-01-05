package com.zfsoft.batchimport.utils;

import tk.mybatis.mapper.util.StringUtil;

import java.io.*;

/**
 * @author HHUA
 */
public class FileUtil {

    /**
     * @param
     * @return
     * @description 根据路径和name 获取项目根目录
     * @author wangsh
     * @date
     */
    public static String getFileName(String path, String name) {
        if (StringUtil.isEmpty(path) && StringUtil.isEmpty(name)) return "";
        if (StringUtil.isEmpty(path)) {
            return name;
        }
        if (StringUtil.isEmpty(name)) {
            return path;
        }
        if (!path.endsWith("/") && !name.startsWith("/")) {
            path += "/";
        }
        return path + name;
    }

    public static int copyToFile(InputStream in, File toFile) throws FileNotFoundException, IOException {
        OutputStream os = new FileOutputStream(toFile);
        int ret = copyStream(in, os);
        os.close();
        return ret;
    }
    /**
     * 文件拷贝
     */
    public static int copyStream(InputStream in, OutputStream out) throws IOException {
        int result = 0;

        byte[] buf = new byte[4096];
        int numRead;
        while ((numRead = in.read(buf)) != -1) {
            out.write(buf, 0, numRead);
            result += numRead;
        }
        out.flush();
        return result;
    }

    public static byte[] getStreamBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copyStream(in, out);
        return out.toByteArray();
    }

    /**
     * 创建目录
     */
    public static void createDir(String dirName) {
        String[] dirList = dirName.split("[//\\\\]");
        StringBuffer tmp = new StringBuffer();
        for (int i = 0; i < dirList.length; i++) {
            tmp.append(dirList[i] + "/");
            File fp = new File(tmp.toString());
            if ((!fp.isDirectory()) && (!fp.isFile())) {
                fp.mkdir();
            }
        }
    }

    /**
     * 删除目录
     */
    public static void deleteDir(String dirName) {
        File fp = new File(dirName);
        File[] aa = fp.listFiles();
        if(aa != null){
            for (int i = 0; i < aa.length; i++) {
                if (aa[i].isFile()) {
                    aa[i].delete();
                } else if (aa[i].isDirectory()) {
                    deleteDir(aa[i].getPath());
                }
            }
        }
        fp.delete();
    }

    /**
     * 删除文件
     */
    public static void delFile(String fileName) {
        File fp = new File(fileName);
        if (fp.isFile()) {
            fp.delete();
        }
    }

    /**
     * 批量删除文件
     */
    public static void delFile(String[] fileName) {
        if (fileName == null) {
            return;
        }
        for (int i = 0; i < fileName.length; i++) {
            delFile(fileName[i]);
        }
    }

    public static int copyFile(InputStream is, String toFile) throws FileNotFoundException, IOException {
        File toF = new File(toFile);
        File parentFile = toF.getParentFile();
        // 当文件夹不存在时，自行创建
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        OutputStream os = new FileOutputStream(toFile);
        int ret = copyStream(is, os);
        is.close();
        os.close();
        return ret;
    }

    public static int copyToFile(InputStream in, String toFile) throws FileNotFoundException, IOException {
        OutputStream os = new FileOutputStream(toFile);
        int ret = copyStream(in, os);
        os.close();
        return ret;
    }

    /**
     * 计算文件大小
     */
    static String convertFileSize(long size) {
        int divisor = 1;
        String unit = "bytes";
        long mb = 1048576L;
        long kb = 1024L;
        if (size >= mb) {
            divisor = 1048576;
            unit = "MB";
        } else if (size >= kb) {
            divisor = 1024;
            unit = "KB";
        }
        if (divisor == 1) {
            return size / divisor + " " + unit;
        }
        String aftercomma = String.valueOf(100L * (size % divisor) / divisor);
        if (aftercomma.length() == 1) {
            aftercomma = "0" + aftercomma;
        }
        return size / divisor + "." + aftercomma + " " + unit;
    }

    public static String fileToString(String filePath, String fileName) throws Exception {
        String fileAddress = filePath + fileName;
        File dirFile = new File(fileAddress);
        if (!dirFile.exists()) {
            return "NO";
        }
        String line;
        FileInputStream in = new FileInputStream(dirFile);
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        line = reader.readLine();
        while (line != null) {
            buffer.append(line);
            line = reader.readLine();
        }
        in.close();
        return buffer.toString();
    }

    //    //传递文件路径找不到目录时候创建目录，找得到不做处理
    public static void createDirByfilePath(String filePath) throws FileNotFoundException, IOException {
        File toF = new File(filePath);
        if(!toF.exists()) {
            File parentFile = toF.getParentFile();
            // 当文件夹不存在时，自行创建
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
        }
    }



}
