package com.butt.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @Author: JavaTansanlin
 * @Description: 时间工具类
 * @Date: Created in 1:56 2018/9/14
 * @Modified By:
 */
public class DateUtil {

    /** 时间转换为unix时间截 */
    public static String Date2TimeStamp(Date date) {
        try {
            return String.valueOf(date.getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 计算两个时间段的秒数
     */
    public static Long getSecond(Date nowTime ,Date futureTime){
        return (futureTime.getTime()-nowTime.getTime())/1000;
    }

    /**
     * 秒数为条件，获取当前的时间加上秒数
     */
    public static Date getSecondDate(Integer second){
        return new Date(System.currentTimeMillis()+(second*1000));
    }

    /**
     * 把日期转换成String
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 获取指定时间的指定多少秒前或者后的时间
     * 把日期往后增加SECOND 秒.整数往后推,负数往前移动
     */
    public static Date getNewSecond(Date date ,Integer second) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);//设置参数时间
        c.add(Calendar.SECOND,second);//把日期往后增加SECOND 秒.整数往后推,负数往前移动
        return c.getTime();
    }

    /**
     * 取得今天的开始时间
     * @return
     */
    public static Date getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 取得今天的结束时间
     * @return
     */
    public static Date getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 获取指定时间的开始时间
     */
    public static Date getStartTime(Date time) {
        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(time);//把时间赋予
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 获取指定时间的结束时间
     */
    public static Date getEndTime(Date time) {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.setTime(time);//把时间赋予
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

}
