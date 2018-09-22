package com.butt.controller;

import com.alibaba.fastjson.JSON;
import com.butt.config.WechatAcountConfig;
import com.butt.entity.Member;
import com.butt.service.UserService;
import com.butt.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 主页跳转
 * @Date: Created in 11:04 2018/9/18
 * @Modified By:
 */
@Controller
public class Index {

    /** 用户dao */
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index() {
        return "forward:/index.html";
    }

    @Autowired
    WechatAcountConfig wechatAcountConfig;

    @RequestMapping("/wxAu")
    public String wxAu(){
        return "redirect: https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + wechatAcountConfig.getMpAppId() + "&redirect_uri=http%3A%2F%2Flyy.96ime.com%2FwxGo&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
    }

    @RequestMapping("/wxGo")
    public void wxGo(String state , String code , HttpServletRequest request , HttpServletResponse response){

        System.out.println("微信页面授权返回的状态："+state);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + wechatAcountConfig.getMpAppId() + "&secret=" + wechatAcountConfig.getMpAppSecret()+"&code="+code+"&grant_type=authorization_code";
        String result = HttpUtil.sendGet(url);
        Map<String, Object> map = JSON.parseObject(result).getInnerMap();
        String access_token = map.get("access_token")+"";
        String openid = map.get("openid") + "";
        System.out.println("返回的refresh_token："+access_token);
        System.out.println("返回的openid："+openid);

        String url2 = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
        String userInfo = HttpUtil.sendGet(url2);
        Map<String, Object> map2 = JSON.parseObject(userInfo).getInnerMap();
        Member member = new Member();
        member.setOid(map2.get("openid")+"");
        member.setImg(map2.get("headimgurl")+"");
        member.setName(map2.get("nickname")+"");
        member.setMoney(0.0);
        member.setIntegral(0.0);
        userService.inserMem(member);
        try {
            response.sendRedirect(request.getContextPath()+"/index.html?oid="+map2.get("openid")+"");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
