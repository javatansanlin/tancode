package com.butt.controller;

import com.butt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
