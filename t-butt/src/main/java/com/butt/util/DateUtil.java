package com.butt.util;

import java.util.Date;

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

}
