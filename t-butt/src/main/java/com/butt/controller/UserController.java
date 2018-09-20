package com.butt.controller;

import com.butt.entity.Guessing;
import com.butt.service.SendsmsService;
import com.butt.service.SysService;
import com.butt.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description:
 * @Date: Created in 4:45 2018/9/14
 * @Modified By:
 */
@RestController
@RequestMapping("/mem")
public class UserController {

    /** 发送验证业务 */
    @Autowired
    SendsmsService sendsmsService;

    /** 用户的相关业务 */
    @Autowired
    UserService userService;

    @RequestMapping("/oaAuth")
    public String oaAuth(){
        System.out.println("微信验证进来了");
        return "success";
    }

    /** 根据用户oid查询用户信息 */
    @PostMapping("/me")
    public Map<String ,Object> me(String oid){
        return userService.findUserByOid(oid);
    }

    /** 用户提现 */
    @PostMapping("withdraw")
    public Map<String ,Object> withdraw(String oid ,double withdrawMoney ,int carId){
        return userService.userWithdraw(oid ,withdrawMoney ,carId);
    }

    /** 发送验证码给手机号 */
    @PostMapping("/sms")
    public Map<String ,Object> sms(HttpSession session, String phone, String oid){
        return sendsmsService.sendsms(session ,phone ,oid);
    }

    /** 绑定手机号 */
    @PostMapping("/bandPhone")
    public Map<String ,Object> bandPhone(HttpSession session,String oid ,String code , String phone){
        return userService.bandPhone(session ,oid ,code,phone);
    }

    /** 业务逻辑 */
    @Autowired
    private SysService sysService;

    /** 查看今天所有的开奖信息 */
    @PostMapping("/todayGuDetail")
    PageInfo<Guessing> todayGuDetail(Integer pageNum){
        if (pageNum==null || pageNum<=0){
            pageNum = 1;
        }
        return sysService.todayGuDetail(pageNum);
    }

}
