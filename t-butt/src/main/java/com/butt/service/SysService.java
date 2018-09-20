package com.butt.service;

import com.butt.entity.Guessing;
import com.butt.model.SysGussOrderListModel;
import com.butt.model.SysOrderListModel;
import com.butt.model.SysRechargeListModel;
import com.butt.model.SysWithdrawListModel;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 后台管理操作相关的业务接口
 * @Date: Created in 23:09 2018/9/19
 * @Modified By:
 */
public interface SysService {
    /** 财务管理-->查看所有充值，按时间排序 */
    PageInfo<SysRechargeListModel> findAll(Integer pageNum);

    /** 财务管理-->查看所有的提现记录，按时间排序 */
    PageInfo<SysWithdrawListModel> findWiAll(Integer pageNum);

    /** 财务管理-->查询今日收入和总收入 */
    Map<String,Object> findReCount();

    /** 财务管理--> 修改提现状态为已转账，并且给用户发送信息 */
    Map<String,Object> changeReState(Integer id);

    /** 订单管理--> 查看所有的订单，按时间排序 */
    PageInfo<SysOrderListModel> findAllOrder(Integer pageNum);

    /** 订单管理--> 修改订单状态未已发货 */
    Map<String,Object> upOrderState(Integer id);

    /** 订单管理--> 查看今日订单数，总订单数 */
    Map<String,Object> findODCount();

    /** 开奖管理--> 查询参加促销并且未开奖的订单 */
    PageInfo<SysGussOrderListModel> findNotOGO(Integer pageNum);

    /** 开奖管理--> 修改订单结果为未中奖 */
    Map<String,Object> upFail(Integer id, String gu);

    /** 查看今天所有的开奖信息 */
    PageInfo<Guessing> todayGuDetail(Integer pageNum);
}
