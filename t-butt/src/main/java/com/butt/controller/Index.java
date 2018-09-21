package com.butt.controller;

import com.butt.config.WechatAcountConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: JavaTansanlin
 * @Description: 主页跳转
 * @Date: Created in 11:04 2018/9/18
 * @Modified By:
 */
@Controller
public class Index {

    @RequestMapping("/")
    public String index() {
        return "forward:/index.html";
    }

    @Autowired
    WechatAcountConfig wechatAcountConfig;

    @RequestMapping("/wxGo")
    public void  wxGo(){
        //String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + wechatAcountConfig.getMpAppId() + "&redirect_uri=http%3A%2F%2www.leiyy.cn%2F&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect"
    }

}
