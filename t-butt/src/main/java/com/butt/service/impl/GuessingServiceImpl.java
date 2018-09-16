package com.butt.service.impl;

import com.alibaba.fastjson.JSON;
import com.butt.dao.GuessingDao;
import com.butt.entity.Guessing;
import com.butt.service.GuessingService;
import com.butt.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private GuessingDao guessingDao;

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

}
