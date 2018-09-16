package com.butt.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 取salt
 * @author javatansanlin
 */
public class Salt {

	/**
	 * 获取盐值
	 * @return
	 */
	public static String getSalt(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 获取订单号
	 * @return
	 */
	public static String getOrderNum(){
		DateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		int a = (int)(Math.random()*(9999-1000+1))+1000;
		String format = dateFormat.format(new Date());
		return format+a;
	}
}
