package com.butt.controller;

import com.butt.model.OrderinfoModel;
import com.butt.service.OrderService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: JavaTansanlin
 * @Description: 订单相关的管理
 * @Date: Created in 19:19 2018/9/16
 * @Modified By:
 */
@RestController
@RequestMapping("/mem/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /** 查看我的所有订单，按时间排序降序 */
    @PostMapping("findMe")
    PageInfo<OrderinfoModel> findMe(String oid , Integer index){
        return orderService.findMyOrder(oid ,index);
    }


}
