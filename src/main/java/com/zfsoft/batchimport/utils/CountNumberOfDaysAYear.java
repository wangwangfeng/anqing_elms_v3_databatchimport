package com.zfsoft.batchimport.utils;



import com.zfsoft.batchimport.common.BaseStaticParameter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CountNumberOfDaysAYear {
    /**
     * 判断今年是否是闰年,返回一年的天数
     * 
     * @author wsc
     * @date 2017年5月31日
     * @return int
     */
	public int countNumberOfDaysAYear(){
		 Calendar now = Calendar.getInstance();  
	     int year=now.get(Calendar.YEAR);
	     int month=now.get(Calendar.MONTH);
	     int day=now.get(Calendar.DATE);
	     if(year%4==0&&year%100!=0||year%400==0&&month<2&&day<=29)
	    	 return 366;
	     else
	    	 return 365;
	}
	 /**
     * 根据年 月 日 来获取证照终止日期
     * 
     * @author gaoll
     * @date 2018年11月21日
     * @return Date
     */
	@SuppressWarnings("static-access")
    public static Date addDate(Date  date,String day,String type) throws Exception { 
       Calendar calendar = new GregorianCalendar();
       calendar.setTime(date); 
       if(type.equals(BaseStaticParameter.ELECLICENCE_TYPE_TIME_1)){
    	   calendar.add(calendar.YEAR, Integer.parseInt(day));//把日期往后增加几年.整数往后推,负数往前移动
       }
       if(type.equals(BaseStaticParameter.ELECLICENCE_TYPE_TIME_2)){
    	   calendar.add(calendar.MONTH, Integer.parseInt(day));//把日期往后增加几个月.整数往后推,负数往前移动
       }
       if(type.equals(BaseStaticParameter.ELECLICENCE_TYPE_TIME_3)){
    	   calendar.add(calendar.DATE, Integer.parseInt(day));//把日期往后增加一天.整数往后推,负数往前移动 
       }
       date=calendar.getTime();   //这个时间就是日期往后推的结果 
		return date;
		}
		
	
}
