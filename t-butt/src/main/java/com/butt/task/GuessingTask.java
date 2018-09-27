package com.butt.task;

import com.alibaba.fastjson.JSON;
import com.butt.service.GuessingService;
import com.butt.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 竞猜实时获取开奖结果的定时器
 * @Date: Created in 20:24 2018/9/14
 * @Modified By:
 */
@Component
public class GuessingTask {

    /** 竞猜业务类 */
    @Autowired
    GuessingService guessingService;

    /** 摇奖 */
    @Scheduled(cron = "0 0/5 * * * *")
    public void scheduled(){
        guessingService.guessingGoing();
    }

    /** 处理猜奖 */
    @Scheduled(cron = "0 0/7 * * * *")
    public void scheduledTwo(){
        guessingService.setGuess();
    }
}
