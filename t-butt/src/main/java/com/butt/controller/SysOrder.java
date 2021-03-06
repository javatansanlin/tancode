package com.butt.controller;

import com.butt.entity.Orderinfo;
import com.butt.model.SysOrderListModel;
import com.butt.service.SysService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 后台订单管理
 * @Date: Created in 23:29 2018/9/18
 * @Modified By:
 */
@RestController
@RequestMapping("/sys/order")
public class SysOrder {

    /** 后台业务 */
    @Autowired
    private SysService sysService;

    /** 查看所有的订单，按时间排序 type : 1-提货订单 ，2-其他订单（包括升级未升级订单）*/
    @PostMapping("/findAll")
    PageInfo<SysOrderListModel> findAll(Integer pageNum ,Integer type){
        if (pageNum==null || pageNum<=0){
            pageNum = 1;
        }
        return sysService.findAllOrder(pageNum ,type);
    }

    /** 修改订单状态未已发货 */
    @PostMapping("/upOrderState")
    Map<String ,Object> upOrderState(Integer id){
        return sysService.upOrderState(id);
    }

    /** 查看今日订单数，总订单数 */
    @PostMapping("/findODCount")
    Map<String ,Object> findODCount(){
        return sysService.findODCount();
    }
}
