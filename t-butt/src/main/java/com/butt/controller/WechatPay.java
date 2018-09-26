package com.butt.controller;

import com.alibaba.fastjson.JSON;
import com.butt.config.wx.PayResponse;
import com.butt.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

    /** 充值 */
    @GetMapping("recharge")
    public String recharge(Double money , String oid ,HttpServletRequest request ,Map<String ,Object> map){
        PayResponse payResponse = payService.recharge(money, oid ,request);
        String appId = payResponse.getAppId();
        String timeStamp = payResponse.getTimeStamp();
        String nonceStr = payResponse.getNonceStr();
        String packAge = payResponse.getPackAge();
        String signType = payResponse.getSignType();
        String paySign = payResponse.getPaySign();
        System.out.println(JSON.toJSON(payResponse));
        return "redirect:"+request.getContextPath()+"/pay.html?appId="+appId+"&timeStamp="+timeStamp+"&nonceStr="+nonceStr+"&packAge="+packAge+"&signType="+signType+"&paySign="+paySign+"&oid="+oid+"";
    }

    /** 充值回调 */
    @RequestMapping("/notity")
    @ResponseBody
    public String notity(HttpServletRequest request ,@RequestBody String notifyData){
        payService.noty(notifyData);
        return "<xml>" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>" +
                "  <return_msg><![CDATA[OK]]></return_msg>" +
                "</xml>";
    }
}
