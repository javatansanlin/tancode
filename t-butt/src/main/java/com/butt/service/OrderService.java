package com.butt.service;

import com.butt.model.OrderinfoModel;
import com.github.pagehelper.PageInfo;

/**
 * @Author: JavaTansanlin
 * @Description: 订单相关的业务
 * @Date: Created in 19:22 2018/9/16
 * @Modified By:
 */
public interface OrderService {

    /** 查看我的所有订单，按时间排序降序 */
    PageInfo<OrderinfoModel> findMyOrder(String oid , Integer index);

}
