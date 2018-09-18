package com.butt.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 发送验证码的业务层
 * 标准接口
 * @author javatansanlin
 */
public interface SendsmsService {
	
	/** 发送验证码  */
	Map<String ,Object> sendsms(HttpSession session, String phone, String oid);

}
