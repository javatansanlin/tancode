package com.butt.config;

import com.butt.config.wx.WxPayH5Config;
import com.butt.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 描述:
 *
 * @author partner
 * @create 2018-08-17 15:21
 */
@Component
public class WechatPayConfig {
    @Autowired
    private WechatAcountConfig acountConfig;

    @Bean
    public BestPayServiceImpl bestPayService() {
        BestPayServiceImpl bestPayService=new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config());
        return bestPayService;
    }

    @Bean
    public WxPayH5Config wxPayH5Config() {
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId(acountConfig.getMpAppId());
        wxPayH5Config.setAppSecret(acountConfig.getMpAppSecret());
        wxPayH5Config.setMchId(acountConfig.getMchId());
        wxPayH5Config.setMchKey(acountConfig.getMchKey());
        wxPayH5Config.setNotifyUrl(acountConfig.getNotifyUrl());
        return wxPayH5Config;
    }
}
