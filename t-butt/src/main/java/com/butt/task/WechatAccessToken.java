package com.butt.task;

import com.alibaba.fastjson.JSONObject;
import com.butt.config.WechatAcountConfig;
import com.butt.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 描述:
 *
 * @author ZMY
 * @date 2018/8/2716:24
 */
@Component
public class WechatAccessToken {

    private String access_token = "";

    private Logger logger = LoggerFactory.getLogger(WechatAccessToken.class);

    @Autowired
    WechatAcountConfig wechatAcountConfig;

    public String getAccessToken() {
        if (access_token==""){
            setAccess_token();
        }
        return access_token;
    }

    //@Scheduled(fixedRate = 3600000)
    public void setAccess_token() {
        try {

            String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + wechatAcountConfig.getMpAppId() + "&secret=" + wechatAcountConfig.getMpAppSecret();
            String tokenResult = HttpUtil.sendGet(tokenUrl);
            System.out.println("获取凭证的token：" + tokenResult);
            JSONObject jsonObject = JSONObject.parseObject(tokenResult);
            Map<String, Object> innerMap = jsonObject.getInnerMap();
            String access_token = innerMap.get("access_token").toString();
            logger.info("获取凭证的token：" + tokenResult);
            this.access_token = access_token;
        }catch (NullPointerException e){
            setAccess_token();
            logger.error(e.getMessage());
        }

    }

}
