package com.butt.service.impl;

import com.butt.dao.OrderinfoDao;
import com.butt.entity.Orderinfo;
import com.butt.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: JavaTansanlin
 * @Description: 订单相关的业务
 * @Date: Created in 19:24 2018/9/16
 * @Modified By:
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    /** 订单dao */
    @Autowired
    private OrderinfoDao orderinfoDao;

    /** 查看我的所有订单，按时间排序降序 */
    @Override
    public PageInfo<Orderinfo> findMyOrder(String oid, Integer index) {
        PageHelper.startPage(index, 15);
        List<Orderinfo> list = orderinfoDao.findOrderListByOid(oid);
        return new PageInfo<Orderinfo>(list);
    }

}