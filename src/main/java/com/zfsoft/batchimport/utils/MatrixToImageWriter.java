package com.zfsoft.batchimport.utils;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import tk.mybatis.mapper.util.StringUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;

/**
 * 二维码生成工具类
 * @description
 * @author chenbw
 * @date 2018年6月22日
 * @Copyright 版权由上海卓繁信息技术股份有限公司拥有
 */
public class MatrixToImageWriter {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private MatrixToImageWriter() {
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        matrix = deleteWhite(matrix);// 删除白边
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }
    public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }

    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    /**
     * 生成二维码
     * 
     * @author chenbw
     * @date 2018年6月22日
     * @param text
     *            二维码内容
     * @param savePath
     *            保存地址
     * @param imageName
     *            文件名
     * @param width
     *            宽度
     * @param height
     *            高度
     * @return
     * @throws Exception
     */
    public static String createQRcodeImage(String text, String savePath, String imageName, int width, int height)
            throws Exception {
        // 二维码的图片格式
        String format = "jpg";
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        // 内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        // 生成二维码
        String imagePath = savePath + "/" + imageName + "." + format;
        File outputFile = new File(imagePath);
        File parentFile = outputFile.getParentFile();
        // 如果父文件夹不存在，创建文件夹
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagePath ;
    }
    public static String createQRcodeImage(String text, String savePath,String attaOid, String imageName, int width, int height)
            throws Exception {
        // 二维码的图片格式
        String format = "jpg";
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        // 内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        BufferedImage image = toBufferedImage(bitMatrix);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String fastdfsPath = "";
        try {
            ImageIO.write(image, format, os);
            InputStream inputStream = new ByteArrayInputStream(os.toByteArray());
            fastdfsPath =  new AbstractFileTool().upLoad(inputStream,  imageName );
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            os.close();
        }
        return fastdfsPath ;
    }


    /**
     * 得到项目路径
     * 
     * @author dusd
     * @date 2014-2-8
     * @return
     */
    public static String webPath() {
        String tmpPath = MatrixToImageWriter.class.getResource("/").getPath();
        String[] arrPath = tmpPath.split("/");
        tmpPath = "";
        for (int i = 0; i < arrPath.length - 2; i++) {
            if (StringUtil.isNotEmpty(arrPath[i]))
                tmpPath += arrPath[i] + "/";
        }
        return tmpPath;
    }

    /**
     * 删除白边
     * 
     * @author fangga
     * @date 2017年8月5日
     * @param matrix
     * @return
     */
    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }
    /**
     * 用来测试方法,不删除
     * @author chenyq
     * @date 2019年4月22日
     * @param args
     * @throws Exception
     */
    // 二维码生成测试
    public static void main(String[] args) {
        try {
            System.out.println(
                    "二维码图片路径" + MatrixToImageWriter.createQRcodeImage("111111111111", "D:\\0622", "1111", 100, 100));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
