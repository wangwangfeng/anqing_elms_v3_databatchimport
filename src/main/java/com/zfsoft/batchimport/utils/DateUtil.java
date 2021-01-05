package com.zfsoft.batchimport.utils;


import com.github.pagehelper.util.StringUtil;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DateUtil extends PropertyEditorSupport {

    // 各种时间格式
    public static final SimpleDateFormat date_sdf = new SimpleDateFormat(
            "yyyy-MM-dd");
    // 各种时间格式
    public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat(
            "yyyyMMdd");
    // 各种时间格式
    public static final SimpleDateFormat date_sdf_wz = new SimpleDateFormat(
            "yyyy年MM月dd日");
    public static final SimpleDateFormat time_sdf = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat(
            "yyyyMMddHHmmss");
    public static final SimpleDateFormat yyyymmddhhmmsss = new SimpleDateFormat(
            "yyyyMMddHHmmsss");
    public static final SimpleDateFormat hhmmsss = new SimpleDateFormat(
            "HHmmsss");
    public static final SimpleDateFormat short_time_sdf = new SimpleDateFormat(
            "HH:mm");
    public static final SimpleDateFormat datetimeFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    // 以毫秒表示的时间
    private static final long DAY_IN_MILLIS = 24 * 3600 * 1000;
    private static final long HOUR_IN_MILLIS = 3600 * 1000;
    private static final long MINUTE_IN_MILLIS = 60 * 1000;
    private static final long SECOND_IN_MILLIS = 1000;

    // 指定模式的时间格式
    private static SimpleDateFormat getSDFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 当前日历，这里用中国时间表示
     * 
     * @return 以当地时区表示的系统当前日历
     */
    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 指定毫秒数表示的日历
     * 
     * @param millis
     *            毫秒数
     * @return 指定毫秒数表示的日历
     */
    public static Calendar getCalendar(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(millis));
        return cal;
    }

    /**
     * 当前日期
     * 
     * @return 系统当前时间
     */
    public static Date getDate() {
        return new Date();
    }

    /**
     * 指定毫秒数表示的日期
     * 
     * @param millis
     *            毫秒数
     * @return 指定毫秒数表示的日期
     */
    public static Date getDate(long millis) {
        return new Date(millis);
    }

    /**
     * 字符串转换时间戳
     * 
     * @param str
     * @return
     */
    public static Timestamp str2Timestamp(String str) {
        Date date = str2Date(str, date_sdf);
        return new Timestamp(date.getTime());
    }

    /**
     * 字符串转换日期
     * 
     * @param str
     * @return
     */
    public static Date str2Date(String str) {
        Date date = str2Date(str, date_sdf);
        return date;
    }

    /**
     * 
     * 返回当前时间的字符串
     * @author wuxx
     * @date: 2018年12月6日 上午10:42:06  
     * @return
     */
    public static String getNowDateStr() {
        return yyyymmddhhmmss.format(new Date());
    }
    
    /**
     * 字符串转换成日期
     * 
     * @param str
     * @param sdf
     * @return
     */
    public static Date str2Date(String str, SimpleDateFormat sdf) {
        if (null == str || "".equals(str)) {
            return null;
        }
        Date date = null;
        try {
            date = sdf.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期转换为字符串
     * 
     * @param date
     *            日期
     * @param format
     *            日期格式
     * @return 字符串
     */
    public static String date2Str(SimpleDateFormat date_sdf) {
        Date date = getDate();
        if (null == date) {
            return null;
        }
        return date_sdf.format(date);
    }

    /**
     * 格式化时间
     * 
     * @param date
     * @param format
     * @return
     */
    public static String dateformat(String date, String format) {
        SimpleDateFormat sformat = new SimpleDateFormat(format);
        Date _date = null;
        try {
            _date = sformat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sformat.format(_date);
    }

    /**
     * 日期转换为字符串
     * 
     * @param date
     *            日期
     * @param format
     *            日期格式
     * @return 字符串
     */
    public static String date2Str(Date date, SimpleDateFormat date_sdf) {
        if (null == date) {
            return null;
        }
        return date_sdf.format(date);
    }

    /**
     * 日期转换为字符串
     * 
     * @param date
     *            日期
     * @param format
     *            日期格式
     * @return 字符串
     */
    public static String getNowDate(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 指定毫秒数的时间戳
     * 
     * @param millis
     *            毫秒数
     * @return 指定毫秒数的时间戳
     */
    public static Timestamp getTimestamp(long millis) {
        return new Timestamp(millis);
    }

    /**
     * 以字符形式表示的时间戳
     * 
     * @param time
     *            毫秒数
     * @return 以字符形式表示的时间戳
     */
    public static Timestamp getTimestamp(String time) {
        return new Timestamp(Long.parseLong(time));
    }

    /**
     * 系统当前的时间戳
     * 
     * @return 系统当前的时间戳
     */
    public static Timestamp getTimestamp() {
        return new Timestamp(new Date().getTime());
    }

    /**
     * 指定日期的时间戳
     * 
     * @param date
     *            指定日期
     * @return 指定日期的时间戳
     */
    public static Timestamp getTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 指定日历的时间戳
     * 
     * @param cal
     *            指定日历
     * @return 指定日历的时间戳
     */
    public static Timestamp getCalendarTimestamp(Calendar cal) {
        return new Timestamp(cal.getTime().getTime());
    }

    /**
     * 系统当前的时间戳
     * 
     * @return 系统当前的时间戳
     */
    public static Timestamp getSqlTimestamp() {
        Date dt = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = df.format(dt);
        Timestamp buydate = Timestamp.valueOf(nowTime);
        return buydate;
    }

    /**
     * 系统时间的毫秒数
     * 
     * @return 系统时间的毫秒数
     */
    public static long getMillis() {
        return new Date().getTime();
    }

    /**
     * 指定日历的毫秒数
     * 
     * @param cal
     *            指定日历
     * @return 指定日历的毫秒数
     */
    public static long getMillis(Calendar cal) {
        return cal.getTime().getTime();
    }

    /**
     * 指定日期的毫秒数
     * 
     * @param date
     *            指定日期
     * @return 指定日期的毫秒数
     */
    public static long getMillis(Date date) {
        return date.getTime();
    }

    /**
     * 指定时间戳的毫秒数
     * 
     * @param ts
     *            指定时间戳
     * @return 指定时间戳的毫秒数
     */
    public static long getMillis(Timestamp ts) {
        return ts.getTime();
    }

    /**
     * 将日期按照一定的格式转化为字符串 默认方式表示的系统当前日期，具体格式：年-月-日
     * 
     * @return 默认日期按“年-月-日“格式显示
     */
    public static String formatDate() {
        return date_sdf.format(getCalendar().getTime());
    }

    /**
     * 获取时间字符串
     */
    public static String getDataString(SimpleDateFormat formatstr) {
        return formatstr.format(getCalendar().getTime());
    }

    /**
     * 指定日期的默认显示，具体格式：年-月-日
     * 
     * @param cal
     *            指定的日期
     * @return 指定日期按“年-月-日“格式显示
     */
    public static String formatDate(Calendar cal) {
        return date_sdf.format(cal.getTime());
    }

    /**
     * 指定日期的默认显示，具体格式：年-月-日
     * 
     * @param date
     *            指定的日期
     * @return 指定日期按“年-月-日“格式显示
     */
    public static String formatDate(Date date) {
        return date_sdf.format(date);
    }

    /**
     * 指定毫秒数表示日期的默认显示，具体格式：年-月-日
     * 
     * @param millis
     *            指定的毫秒数
     * @return 指定毫秒数表示日期按“年-月-日“格式显示
     */
    public static String formatDate(long millis) {
        return date_sdf.format(new Date(millis));
    }

    /**
     * 默认日期按指定格式显示
     * 
     * @param pattern
     *            指定的格式
     * @return 默认日期按指定格式显示
     */
    public static String formatDate(String pattern) {
        return getSDFormat(pattern).format(getCalendar().getTime());
    }

    /**
     * 指定日期按指定格式显示
     * 
     * @param cal
     *            指定的日期
     * @param pattern
     *            指定的格式
     * @return 指定日期按指定格式显示
     */
    public static String formatDate(Calendar cal, String pattern) {
        return getSDFormat(pattern).format(cal.getTime());
    }

    /**
     * 指定日期按指定格式显示
     * 
     * @param date
     *            指定的日期
     * @param pattern
     *            指定的格式
     * @return 指定日期按指定格式显示
     */
    public static String formatDate(Date date, String pattern) {
        return getSDFormat(pattern).format(date);
    }

    /**
     * 将日期按照一定的格式转化为字符串 默认方式表示的系统当前日期，具体格式：年-月-日 时：分
     * 
     * @return 默认日期按“年-月-日 时：分“格式显示
     */
    public static String formatTime() {
        return time_sdf.format(getCalendar().getTime());
    }

    /**
     * 将日期按照一定的格式转化为字符串 指定毫秒数表示日期的默认显示，具体格式：年-月-日 时：分
     * 
     * @param millis
     *            指定的毫秒数
     * @return 指定毫秒数表示日期按“年-月-日 时：分“格式显示
     */
    public static String formatTime(long millis) {
        return time_sdf.format(new Date(millis));
    }

    /**
     * 将日期按照一定的格式转化为字符串 指定日期的默认显示，具体格式：年-月-日 时：分
     * 
     * @param cal
     *            指定的日期
     * @return 指定日期按“年-月-日 时：分“格式显示
     */
    public static String formatTime(Calendar cal) {
        return time_sdf.format(cal.getTime());
    }

    /**
     * 将日期按照一定的格式转化为字符串 指定日期的默认显示，具体格式：年-月-日 时：分
     * 
     * @param date
     *            指定的日期
     * @return 指定日期按“年-月-日 时：分“格式显示
     */
    public static String formatTime(Date date) {
        return time_sdf.format(date);
    }

    /**
     * 将日期按照一定的格式转化为字符串 默认方式表示的系统当前日期，具体格式：时：分
     * 
     * @return 默认日期按“时：分“格式显示
     */
    public static String formatShortTime() {
        return short_time_sdf.format(getCalendar().getTime());
    }

    /**
     * 指定毫秒数表示日期的默认显示，具体格式：时：分
     * 
     * @param millis
     *            指定的毫秒数
     * @return 指定毫秒数表示日期按“时：分“格式显示
     */
    public static String formatShortTime(long millis) {
        return short_time_sdf.format(new Date(millis));
    }

    /**
     * 指定日期的默认显示，具体格式：时：分
     * 
     * @param cal
     *            指定的日期
     * @return 指定日期按“时：分“格式显示
     */
    public static String formatShortTime(Calendar cal) {
        return short_time_sdf.format(cal.getTime());
    }

    /**
     * 指定日期的默认显示，具体格式：时：分
     * 
     * @param date
     *            指定的日期
     * @return 指定日期按“时：分“格式显示
     */
    public static String formatShortTime(Date date) {
        return short_time_sdf.format(date);
    }

    /**
     * 将字符串按照一定的格式转化为日期或时间 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
     * 
     * @param src
     *            将要转换的原始字符窜
     * @param pattern
     *            转换的匹配格式
     * @return 如果转换成功则返回转换后的日期
     * @throws ParseException
     * @throws AIDateFormatException
     */
    public static Date parseDate(String src, String pattern)
            throws ParseException {
        return getSDFormat(pattern).parse(src);

    }

    /**
     * 将字符串按照一定的格式转化为日期或时间 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
     * 
     * @param src
     *            将要转换的原始字符窜
     * @param pattern
     *            转换的匹配格式
     * @return 如果转换成功则返回转换后的日期
     * @throws ParseException
     * @throws AIDateFormatException
     */
    public static Calendar parseCalendar(String src, String pattern)
            throws ParseException {

        Date date = parseDate(src, pattern);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * 为字符串日期添加天数
     * 
     * @author gaolh
     * @date 2016-7-11
     * 
     * @param src
     *            字符串日期
     * @param pattern
     *            字符串日期格式
     * @param amount
     *            添加的天数
     * @return 计算后的结果
     * @throws ParseException
     *             格式化异常
     */
    public static String formatAddDate(String src, String pattern, int amount)
            throws ParseException {
        Calendar cal;
        cal = parseCalendar(src, pattern);
        cal.add(Calendar.DATE, amount);
        return formatDate(cal);
    }

    /**
     * 为日期添加天数
     * 
     * @author gaolh
     * @date 2016-7-11
     * 
     * @param date
     *            日期
     * @param amount
     *            添加的天数
     * @return 计算后的结果
     */
    public static Date addDate(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, amount);
        return cal.getTime();
    }

    /**
     * 将字符串按照一定的格式转化为日期或时间 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
     * 
     * @param src
     *            将要转换的原始字符窜
     * @param pattern
     *            转换的匹配格式
     * @return 如果转换成功则返回转换后的时间戳
     * @throws ParseException
     */
    public static Timestamp parseTimestamp(String src, String pattern)
            throws ParseException {
        Date date = parseDate(src, pattern);
        return new Timestamp(date.getTime());
    }

    /**
     * 计算两个时间之间的差值，根据标志的不同而不同
     * 
     * @param flag
     *            计算标志，表示按照年/月/日/时/分/秒等计算
     * @param calSrc
     *            减数
     * @param calDes
     *            被减数
     * @return 两个日期之间的差值
     */
    public static int dateDiff(char flag, Calendar calSrc, Calendar calDes) {

        long millisDiff = getMillis(calSrc) - getMillis(calDes);

        if (flag == 'y') {
            return (calSrc.get(Calendar.YEAR) - calDes.get(Calendar.YEAR));
        }

        if (flag == 'd') {
            return (int) (millisDiff / DAY_IN_MILLIS);
        }

        if (flag == 'h') {
            return (int) (millisDiff / HOUR_IN_MILLIS);
        }

        if (flag == 'm') {
            return (int) (millisDiff / MINUTE_IN_MILLIS);
        }

        if (flag == 's') {
            return (int) (millisDiff / SECOND_IN_MILLIS);
        }

        return 0;
    }
    
    /**
     * 计算两个时间之间的差值，根据标志的不同而不同
     * 
     * @param flag
     *            计算标志，表示按照年/月/日/时/分/秒等计算
     * @param dateSrc
     *            减数
     * @param dateDes
     *            被减数
     * @return 两个日期之间的差值
     */
    public static int dateDiff(char flag, Date dateSrc, Date dateDes) {
        if (dateSrc == null || dateDes == null) {
            return 0;
        }
        Calendar calSrc = Calendar.getInstance();
        calSrc.setTime(dateSrc);

        Calendar calDes = Calendar.getInstance();
        calDes.setTime(dateDes);

        long millisDiff = getMillis(calSrc) - getMillis(calDes);

        if (flag == 'y') {
            return (calSrc.get(Calendar.YEAR) - calDes.get(Calendar.YEAR));
        }

        if (flag == 'd') {
            return (int) (millisDiff / DAY_IN_MILLIS);
        }

        if (flag == 'h') {
            return (int) (millisDiff / HOUR_IN_MILLIS);
        }

        if (flag == 'm') {
            return (int) (millisDiff / MINUTE_IN_MILLIS);
        }

        if (flag == 's') {
            return (int) (millisDiff / SECOND_IN_MILLIS);
        }

        return 0;
    }

    /**
     * String类型 转换为Date, 如果参数长度为10 转换格式”yyyy-MM-dd“ 如果参数长度为19 转换格式”yyyy-MM-dd
     * HH:mm:ss“ * @param text String类型的时间值
     */
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.hasText(text)) {
            try {
                if (text.indexOf(":") == -1 && text.length() == 10) {
                    setValue(date_sdf.parse(text));
                } else if (text.indexOf(":") > 0 && text.length() == 19) {
                    setValue(datetimeFormat.parse(text));
                } else {
                    throw new IllegalArgumentException(
                            "Could not parse date, date format is error ");
                }
            } catch (ParseException ex) {
                IllegalArgumentException iae = new IllegalArgumentException(
                        "Could not parse date: " + ex.getMessage());
                iae.initCause(ex);
                throw iae;
            }
        } else {
            setValue(null);
        }
    }

    /**
     * 获取当前时间的年份
     * 
     * @return
     */
    public static int getYear() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(getDate());
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 
     * @param flag
     *            增加时间类型，1代表天数，2代表月数，3代表年数
     * @param initDate
     *            初始时间
     * @param addTime
     *            增加时间数目
     * @param subDays
     *            需要减去的天数，此处单位默认为天
     * @return
     */
    public static Date calcuDate(Integer flag, Date initDate, Integer addTime,
            Integer subDays) {
        Calendar cal = Calendar.getInstance();
        Date deadDateLine;
        switch (flag) {
        case 1:// 天
            int addDays = addTime - subDays;
            deadDateLine = DateUtil.addDate(initDate, addDays);
            break;
        case 2:// 月
            cal.setTime(initDate);
            cal.add(Calendar.MONTH, addTime);
            deadDateLine = DateUtil.addDate(cal.getTime(), 0 - subDays);
            break;
        case 3:// 年
            cal.setTime(initDate);
            cal.add(Calendar.YEAR, addTime);
            deadDateLine = DateUtil.addDate(cal.getTime(), subDays);
            break;
        default:
            return null;
        }
        return deadDateLine;
    }

    /**
     * 比较两个时间大小
     * 
     * @param dateOne
     *            日期1
     * @param dateTwo
     *            日期2
     * @return
     */
    public static boolean compareDate(Date dateOne, Date dateTwo) {
        if (dateOne != null && dateTwo != null) {
            if (dateOne.getTime() > dateTwo.getTime()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当天开始时间
     * 
     * @return
     */
    public static Date getBeginADay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        // 今天开始时间
        Date start = calendar.getTime();
        return start;

    }

    /**
     * 获取当天结束时间
     * 
     * @return
     */
    public static Date getEndADay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.SECOND, -1);
        // 今天结束时间
        Date end = calendar.getTime();
        return end;

    }
    /**
     * 本月开始时间
     * @return
     */
    public static Date getTimesMonthmorning() {  
        Calendar cal = Calendar.getInstance();  
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));  
        return  cal.getTime(); 
        
    }  
    // 获得本月最后一天24点时间  
    public static Date getTimesMonthnight() {  
        Calendar cal = Calendar.getInstance();  
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));  
        cal.set(Calendar.HOUR_OF_DAY, 24);  
        return cal.getTime();  
    }  
  
    /**
     * 
     * 计算2个时间段相隔天数
     * @author wuxx
     * @date: 2018年11月26日 下午5:11:28  
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    public static Long getDaysBetween(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);
        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);
        return (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24);
    }
    
    
    /**
     * @author chenyq
     * @since 20190709
   	 * 获取当前日期与周一相差的天数
   	 * @return
   	 */
   	public static int getMondayPlus(){
   		Calendar day=Calendar.getInstance();
   		int dayOfWeek=day.get(Calendar.DAY_OF_WEEK);
   		if(dayOfWeek==1){ //一周中第一天（周日）
   			return -6;
   		}else{
   			return 2-dayOfWeek;
   		}
   	}
    
   	/**
	 * 获取当前季度 起始时间
	 * @author chenyq
     * @since 20190709
	 * @return
	 */
	public static Date getStartQuarter(){
		 Calendar today = getCalendar() ;
         int currentMonth = today.get(Calendar.MONTH) + 1; 
		 try { 
	            if (currentMonth >= 1 && currentMonth <= 3) 
	            	today.set(Calendar.MONTH, 0); 
	            else if (currentMonth >= 4 && currentMonth <= 6) 
	            	today.set(Calendar.MONTH, 3); 
	            else if (currentMonth >= 7 && currentMonth <= 9) 
	            	today.set(Calendar.MONTH, 4); 
	            else if (currentMonth >= 10 && currentMonth <= 12) 
	            	today.set(Calendar.MONTH, 9); 
	        } catch (Exception e) { 
	            e.printStackTrace(); 
	        }
        today.set(Calendar.DAY_OF_MONTH , 1);
		return today.getTime();
	}

	/**
	 * 获取当季的结束时间
	 * @author chenyq
     * @since 20190709
	 */
	public static Date getEndQuarter(){
		Calendar today = getCalendar() ;
        int currentMonth = today.get(Calendar.MONTH) + 1; 
        try { 
            if (currentMonth >= 1 && currentMonth <= 3) { 
            	today.set(Calendar.MONTH, 2); 
            	today.set(Calendar.DATE, 31); 
            } else if (currentMonth >= 4 && currentMonth <= 6) { 
            	today.set(Calendar.MONTH, 5); 
            	today.set(Calendar.DATE, 30); 
            } else if (currentMonth >= 7 && currentMonth <= 9) { 
            	today.set(Calendar.MONTH,8); 
            	today.set(Calendar.DATE, 30); 
            } else if (currentMonth >= 10 && currentMonth <= 12) { 
            	today.set(Calendar.MONTH, 11); 
            	today.set(Calendar.DATE, 31); 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return today.getTime(); 
	}
	/**
	 * 获取当年起始时间
	 */
	public static Date getStartYear(){
		Calendar today = getCalendar() ;
        try { 
        	today.set(Calendar.MONTH, 0); 
        	today.set(Calendar.DAY_OF_MONTH, today.getActualMinimum(Calendar.DAY_OF_MONTH)); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return today.getTime(); 
	}
	/**
	 * 获取当年结束时间
	 */
	public static Date getEndYear(){
		Calendar today = getCalendar() ;
		 try { 
	        	today.set(Calendar.MONTH, 11); 
	            today.set(Calendar.DAY_OF_MONTH, today.getMaximum(Calendar.DAY_OF_MONTH)); 
	      } catch (Exception e) { 
	            e.printStackTrace(); 
	      } 
	      return today.getTime(); 
	}
	/**
	 * 本周开始时间
	 * @author chenyq 2019年7月9日
	 * @return      
	 * @return Date      
	 * @exception 出现的错误描述
	 */
	public static Date getStartWeek(){
		Calendar currentDate = new GregorianCalendar(); 
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);
				
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return currentDate.getTime();
	}
	
	public static void main(String args[]) {
		System.out.println(getStartWeek());
	}

    /**
     *
     * 判断字符串是否是数字
     * @author wuxx
     * @date: 2018年11月23日 下午5:18:34
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        final String number = "0123456789";
        for (int i = 0; i < str.length(); i++) {
            if (number.indexOf(str.charAt(i)) == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断数据是否能够转成日期格式
     *
     * @author dusd
     * @date 2016年12月12日
     * @param str
     * @param allowNull
     * @param dateFormat
     * @return
     */
    public static Object[] checkInstanceofDate(String describe, String str, String allowNull, String dateFormat) {
        Object[] objs = new Object[2];
        String errorMessage = null;
        try {
            if (StringUtil.isEmpty(str.toString())) {
                if (allowNull.toLowerCase().equals("y")) {
                    errorMessage = "";
                } else {
                    errorMessage =describe +  "不允许为空";
                }
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat();
                // by yuy 2019-12-22
                String yearStr = "2019";
                String monthStr = "12";
                String dayStr = "22";
                if (str.contains("年") && str.contains("月")  && str.contains("日")) {
                    yearStr = str.substring(0, str.indexOf("年"));
                    monthStr = str.substring(str.indexOf("年")+1, str.indexOf("月"));
                    dayStr = str.substring(str.indexOf("月")+1, str.indexOf("日"));
                } else if (str.contains("-")) {
                    yearStr = str.split("-")[0];
                    monthStr = str.split("-")[1];
                    dayStr = str.split("-")[2];
                } else if (str.contains("/")) {
                    yearStr = str.split("/")[0];
                    monthStr = str.split("/")[1];
                    dayStr = str.split("/")[2];
                } else if (str.contains(".")) {
                    yearStr = str.split(".")[0];
                    monthStr = str.split(".")[1];
                    dayStr = str.split(".")[2];
                } else {
                    if (str.length() == 8) {
                        yearStr = str.substring(0, 4);
                        monthStr = str.substring(4, 6);
                        dayStr = str.substring(6, 8);
                    }
                }
                if (Integer.valueOf(yearStr) > 9999 || Integer.valueOf(monthStr) > 12 || Integer.valueOf(dayStr) > 31) {
                    objs[1] = null;
                } else {
                    try {
                        sdf.applyPattern(dateFormat);
                        objs[1] = sdf.parse(str.toString());
                    } catch (Exception e) {
                        try {
                            sdf.applyPattern("yyyy/MM/dd");
                            objs[1] = sdf.parse(str.toString());
                        } catch (Exception e1) {
                            try {
                                sdf.applyPattern("yyyy-MM-dd");
                                objs[1] = sdf.parse(str.toString());
                            } catch (Exception e2) {
                                try {
                                    sdf.applyPattern("yyyy.MM.dd");
                                    objs[1] = sdf.parse(str.toString());
                                } catch (Exception e3) {
                                    try {
                                        sdf.applyPattern("yyyyMMdd");
                                        objs[1] = sdf.parse(str.toString());
                                    }   catch (Exception e5) {
                                        try {
                                            sdf.applyPattern("yyyy年MM月dd日");
                                            objs[1] = sdf.parse(str.toString());
                                        } catch (Exception e6) {
                                            objs[1] = null;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(null==objs[1]){
                errorMessage = describe + "时间类型填写错误";
            }
            objs[0] = errorMessage;

        } catch (Exception e) {
            errorMessage = describe + "格式错误";
            objs[0] = errorMessage;
            return objs;
        }
        return objs;
    }

    /**
     * 验证时间字符串格式输入是否正确
     * @author chenyq
     * @date chenyq
     * @param timeStr
     * @return
     */
    public static boolean valiDateTimeWithLongFormat(String timeStr) {
        String format = "((19|20)[0-9]{2})(-|/|.|年)(0?[1-9]|1[012])(-|/|.|年)(0?[1-9]|[12][0-9]|3[01])(日|)";

        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(timeStr);
        if (matcher.matches()) {
            pattern = Pattern.compile("(\\d{4})-(\\d+)-(\\d+).*");
            matcher = pattern.matcher(timeStr);
            if (matcher.matches()) {
                int y = Integer.valueOf(matcher.group(1));
                int m = Integer.valueOf(matcher.group(2));
                int d = Integer.valueOf(matcher.group(3));
                if (d > 28) {
                    Calendar c = Calendar.getInstance();
                    c.set(y, m-1, 1);
                    int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
                    return (lastDay >= d);
                }
            }
            return true;
        }
        return false;
    }

}
