package com.butt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 描述: 微信配置
 * @author javatansanlin
 * @create 2018-08-17 15:25
 */
@Component
@Configuration
public class WechatAcountConfig {

    @Value("${wechat.mpAppId}")
    private String mpAppId;

    @Value("${wechat.mpAppSecret}")
    private String mpAppSecret;
    @Value("${wechat.mchId}")
    private String mchId;
    @Value("${wechat.mchKey}")
    private String mchKey;
    @Value("${wechat.notifyUrl}")
    private String notifyUrl;

    public String getMpAppId() {
        return mpAppId;
    }

    public String getMpAppSecret() {
        return mpAppSecret;
    }

    public String getMchId() {
        return mchId;
    }

    public String getMchKey() {
        return mchKey;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    @Override
    public String toString() {
        return "WechatAcountConfig{" +
                "mpAppId='" + mpAppId + '\'' +
                ", mpAppSecret='" + mpAppSecret + '\'' +
                ", mchId='" + mchId + '\'' +
                ", mchKey='" + mchKey + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                '}';
    }
}
