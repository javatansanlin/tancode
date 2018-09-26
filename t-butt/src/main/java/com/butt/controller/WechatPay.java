package com.butt.controller;

import com.alibaba.fastjson.JSON;
import com.butt.config.wx.PayResponse;
import com.butt.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 微信支付相关的接口
 * @Date: Created in 11:08 2018/9/26
 * @Modified By:
 */
@Controller
@RequestMapping("/wxpay")
public class WechatPay {

    @Autowired
    private PayService payService;

    /** redis操作 */
    @Autowired
    private RedisTemplate redisTemplate;

    /** 充值 */
    @RequestMapping("recharge")
    public ModelAndView recharge(Double money , String oid ,HttpServletRequest request ,Map<String ,Object> map){
        PayResponse payResponse = payService.recharge(money, oid ,request);
        if(payResponse==null){
            System.out.println("返回值空-----------");
            return new ModelAndView(request.getContextPath()+"/err.html");
        }
        map.put("payResponse", payResponse);
        System.out.println(JSON.toJSON(payResponse));

        return new ModelAndView(request.getContextPath()+"/pay.html",map);
    }

    /** 充值回调 */
    @RequestMapping("/notity")
    @ResponseBody
    public ModelAndView notity(HttpServletRequest request ,@RequestBody String notifyData){
        payService.noty(notifyData);
        return new ModelAndView(request.getContextPath()+"/paysuccess.html");
    }
}
