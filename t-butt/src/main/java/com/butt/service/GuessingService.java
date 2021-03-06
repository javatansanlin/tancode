package com.butt.service;

/**
 * @Author: JavaTansanlin
 * @Description: 竞猜的相关service
 * @Date: Created in 21:24 2018/9/14
 * @Modified By:
 */
public interface GuessingService {

    /** 获取竞猜结果，并且添加到数据库 */
    void guessingGoing();

    /** 查找待开奖的订单，并且判断有没有中奖 */
    void setGuess();
}
