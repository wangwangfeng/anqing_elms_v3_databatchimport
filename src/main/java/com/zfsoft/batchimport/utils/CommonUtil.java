package com.zfsoft.batchimport.utils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Random;

/**
 * 常用方法的处理
 * 
 * @author gaolh
 * @date 2016年3月24日
 * 
 */
public class CommonUtil {
	/**
	 * 对指定字符串进行MD5加密
	 * 
	 * @param piStr
	 * @return
	 */
	public static String md5(String piStr) {
		String encodeStr = "";
		byte[] digesta = null;
		try {
			MessageDigest alg = MessageDigest.getInstance("MD5");
			alg.update(piStr.getBytes());
			digesta = alg.digest();
			encodeStr = byte2hex(digesta);
		} catch (Exception e) {
		}
		return encodeStr;
	}

	private static String byte2hex(byte[] piByte) {
		String reStr = "";
		for (int i = 0; i < piByte.length; i++) {
			int v = piByte[i] & 0xFF;
			if (v < 16)
				reStr += "0";
			reStr += Integer.toString(v, 16).toLowerCase();
		}
		return reStr;
	}

	/**
	 * 根据时间以及随机数生成文件名
	 * 
	 * @author gaolh
	 * @date 2016-8-17
	 * 
	 * @return
	 */
	public static String generateNewFileName() {
		return DateUtil.formatDate("yyyyMMddHHmmssSSS") + new Random().nextInt(10000);
	}

	/**
	 * 判断请求是否为Ajax请求
	 * 
	 * @param request
	 *            请求对象
	 * @return Ajax请求-true，否则为false
	 */
	public static boolean isAjaxRequest(ServletRequest request) {
		return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
	}
	
	/**
	 * 判断集合是否为空
	 * @param collection
	 * @return
	 */
	public static boolean collectionIsNullOrSpace(Collection<?> collection) {
        if (collection == null || collection.isEmpty()
                || collection.size() == 0) {
            return false;
        }
        return true;
    }
	
	/**
	 * 二分法冒泡排序
	 * @author chenyq
	 * @date 2019年2月20日
	 * @param arr 需要排序数组
	 */
	public static int[]  advanceInsertSortWithBinarySearch(int[] arr) {
	    for (int i = 1; i < arr.length; i++) {
	        int temp = arr[i];
	        int low = 0, high = i - 1;
	        int mid = -1;
	        while (low <= high) {            
	            mid = low + (high - low) / 2;            
	            if (arr[mid] > temp) {               
	                high = mid - 1;            
	            } else { // 元素相同时，也插入在后面的位置                
	                low = mid + 1;            
	            }        
	        }        
	        for(int j = i - 1; j >= low; j--) {            
	            arr[j + 1] = arr[j];        
	        }        
	        arr[low] = temp;    
	    }
	    return arr;
	}
	/**
     * @description:根据传入的开始时间毫秒数，打印输出时间损耗，并返回重置的开始时间毫秒数
     * @author yaosw
     * @date 2018年12月25日
     * @param startTime 开始时间毫秒数
     * @param memo 输出备注
     * @param stdCost 输出的时间阈值，超出该阈值才会输出
     * @return
     */
    public static long consolePrintTimeCost(long startTime,String memo,long stdCost){
        long timeCost = System.currentTimeMillis()- startTime;
            //打印输出时间值
            System.out.println(memo+"cost："+timeCost+" ms");
        return System.currentTimeMillis();
    }

    /**
     * @description 计算百分比
     * @author wangsh
     * @date
     * @param
     * @return
     */
    public  static String calculatedPercentage(Integer num , int all){
    	if(num == null) num = 0;
		NumberFormat numberFormat = NumberFormat.getInstance();
		// 设置精确到小数点后2位
		numberFormat.setMaximumFractionDigits(2);
		return numberFormat.format((float)num/(float)all*100) +"%";
	}
}
