package com.butt.service.impl;

import com.alibaba.fastjson.JSON;
import com.butt.dao.GuessingDao;
import com.butt.dao.OrderinfoDao;
import com.butt.entity.Guessing;
import com.butt.entity.Orderinfo;
import com.butt.service.GuessingService;
import com.butt.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 竞猜的相关service
 * @Date: Created in 21:26 2018/9/14
 * @Modified By:
 */
@Service
@Transactional
public class GuessingServiceImpl implements GuessingService {

    /** 竞猜dao */
    @Autowired
    private GuessingDao guessingDao;

    /** 订单dao */
    @Autowired
    private OrderinfoDao orderinfoDao;

    /** 获取竞猜结果，并且添加到数据库 */
    @Override
    public void guessingGoing() {
        //添加一条新摇奖
        Guessing newGuessing = new Guessing();
        guessingDao.insertOne(newGuessing);
        //获取摇奖id，拿到上一次的摇奖id
        Integer newId = newGuessing.getId();
        System.out.println("新建的摇奖id-->"+newId);
        if (newId!=null && newId>1){
            //产生一个随机数
            int iRandom = (int)(1+Math.random()*10);
            String jo = iRandom%2==0?"O":"J";
            String dx = iRandom>5?"D":"X";
            //查询出上一次的摇奖，并且更新信息
            Guessing oldGuessing = guessingDao.findGuessById(newId - 1);
            if (oldGuessing!=null){
                oldGuessing.setLastnum(iRandom);
                oldGuessing.setJo(jo);
                oldGuessing.setDx(dx);
                guessingDao.updateById(oldGuessing);
            }
        }
    }

    /** 查找待开奖的订单，并且判断有没有中奖 */
    @Override
    public void setGuess() {
        //查询出状态3-点了促销，正在开奖中的订单
        List<Orderinfo> orderinfoList = orderinfoDao.findNotGuess();
        if (orderinfoList!=null && orderinfoList.size()>0){
            for (Orderinfo ot : orderinfoList) {
                //查询该中奖id的订单
                Guessing guess = guessingDao.findGuessById(ot.getGuessid());
                if (guess!=null && guess.getLastnum()!=null ){//判断是否已经开奖
                    //判断用户选择的竞猜结果
                    if ("D".equals(ot.getGuess()) || "X".equals(ot.getGuess())){//大小
                        if (guess.getDx().equals(ot.getGuess())){
                            ot.setState(5);
                        }else {
                            ot.setState(4);
                        }
                    }
                    if ("J".equals(ot.getGuess()) || "O".equals(ot.getGuess())) {//鸡藕
                        if (guess.getJo().equals(ot.getGuess())){
                            ot.setState(5);
                        }else {
                            ot.setState(4);
                        }
                    }
                    //更新订单状态
                    orderinfoDao.updateOrderGuessAndState(ot);
                }
            }
        }
    }
}
