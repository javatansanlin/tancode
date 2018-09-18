package com.butt.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.butt.dao.MemberDao;
import com.butt.entity.Member;
import com.butt.service.SendsmsService;
import com.butt.util.HttpUtil;
import com.butt.util.XmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 发送验证码的业务层
 * 实现类
 * @author javatansanlin
 */
@Service
@Transactional
public class SendsmsServiceImpl implements SendsmsService{

	/** 用户dao */
	@Autowired
	private MemberDao memberDao;

	private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

	@Override
	public Map<String ,Object> sendsms(HttpSession session, String phone, String oid) {
		Map<String ,Object> result = new HashMap<>();
		result.put("code" ,1);
		if (phone==null || phone.length()!=11 || oid==null){
			result.put("msg" ,"请输入手机");
			return result;
		}
		//查询该用户是否存在记录中
		Member memByOid = memberDao.findMemByOid(oid);
		if (memByOid==null){
			result.put("msg" ,"用户不存在，发送失败");
			return result;
		}
		try {
			String mobile_code = (int) ((Math.random() * 9 + 1) * 100000) + "";
			Map<String ,Object> map = new HashMap<>();
			map.put("account" ,"C57222454");
			map.put("password" ,"0fc50f81274d582362c697e74e0929a1");
			map.put("mobile" ,phone);
			map.put("content" ,new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。"));
			String s = HttpUtil.sendPost(Url, map);
			Map<String, String> toMap = XmlUtil.toMap(s);
			if("2".equals(toMap.get("code"))){
				//把发送给手机的验证码放到session中
				session.setAttribute(oid,mobile_code);
				session.setAttribute(oid+"-phone",mobile_code);
				result.put("msg" ,"发送成功，请查收！");
			}else if("4085".equals(toMap.get("code"))){
				result.put("msg" ,"该号码在于今天已经超过发送次数！");
			}else if("4086".equals(toMap.get("code"))){
				result.put("msg" ,"该号码使用过于频繁，请稍后再试！");
			}else if("406".equals(toMap.get("code"))){
				result.put("msg" ,"手机格式不正确！");
			}else{
				result.put("msg" ,"发送失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg" ,"系统错误，发送失败");
		}
		return result;
	}

}
