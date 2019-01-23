package com.butt.controller;

import com.butt.model.SysGussOrderListModel;
import com.butt.service.SysService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 后台竞猜管理
 * @Date: Created in 23:58 2018/9/18
 * @Modified By:
 */
@RestController
@RequestMapping("/sys/gu")
public class SysGuess {

    /** 后台相关业务逻辑 */
    @Autowired
    private SysService sysService;

    /** 查询参加促销并且未开奖的订单 */
    @PostMapping("/findNotOGO")
    PageInfo<SysGussOrderListModel> findNotOGO(Integer pageNum){
        if (pageNum==null || pageNum<=0){
            pageNum = 1;
        }
        return sysService.findNotOGO(pageNum);
    }

    /** 修改订单结果，注意修改掉用户的竞猜结果 */
    @PostMapping("/upFail")
    Map<String ,Object> upFail(Integer id ,String gu){
        return sysService.upFail(id,gu);
    }


}
