package com.butt.service.impl;

import com.butt.dao.OrderinfoDao;
import com.butt.dao.RechargeDao;
import com.butt.dao.WithdrawDao;
import com.butt.model.SysOrderListModel;
import com.butt.model.SysRechargeListModel;
import com.butt.model.SysWithdrawListModel;
import com.butt.service.SysService;
import com.butt.util.DateUtil;
import com.butt.util.HttpUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 后台管理操作相关的业务接口
 * @Date: Created in 23:11 2018/9/19
 * @Modified By:
 */
@Service
@Transactional
public class SysServiceImpl implements SysService {

    /**
     * 充值dao
     */
    @Autowired
    private RechargeDao rechargeDao;

    /**
     * 提现dao
     */
    @Autowired
    private WithdrawDao withdrawDao;

    /**
     * 订单dao
     */
    @Autowired
    private OrderinfoDao orderinfoDao;

    /**
     * 财务管理-->查看所有充值，按时间排序
     */
    @Override
    public PageInfo<SysRechargeListModel> findAll(Integer pageNum) {
        PageHelper.startPage(pageNum, 15);
        List<SysRechargeListModel> list = rechargeDao.findAll();
        return new PageInfo<SysRechargeListModel>(list);
    }

    /**
     * 财务管理-->查看所有的提现记录，按时间排序
     */
    @Override
    public PageInfo<SysWithdrawListModel> findWiAll(Integer pageNum) {
        PageHelper.startPage(pageNum, 15);
        List<SysWithdrawListModel> wid = withdrawDao.findWidByState(1);//未提现
        return new PageInfo<SysWithdrawListModel>(wid);
    }

    /**
     * 财务管理-->查询今日收入和总收入
     */
    @Override
    public Map<String, Object> findReCount() {
        Map<String, Object> result = new HashMap<>();
        //查询今日收入
        Double todayCount = rechargeDao.fingMoneyCount(DateUtil.getStringDate(DateUtil.getStartTime()), DateUtil.getStringDate(DateUtil.getEndTime()));
        //查询总收入
        Double allCount = rechargeDao.fingMoneyCount(null, null);
        result.put("code", 3);
        result.put("today", todayCount);
        result.put("all", allCount);
        return result;
    }

    private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

    /**
     * 财务管理--> 修改提现状态为已转账，并且给用户发送信息
     */
    @Override
    public Map<String, Object> changeReState(Integer id) {
        Map<String, Object> result = new HashMap<>();
        //判断参数
        if (id == null || id <= 0) {
            result.put("code", 1);
            result.put("msg", "参数错误");
            return result;
        }
        //查询出该记录用户的手机号
        String phone = withdrawDao.findUPhoneById(id);
        if (phone != null || phone.length() == 11) {
            //给该手机号发送信息
            Map<String, Object> map = new HashMap<>();
            map.put("account", "C57222454");
            map.put("password", "0fc50f81274d582362c697e74e0929a1");
            map.put("mobile", phone);
            map.put("content", new String("亲爱的洋洋商城用户:" + phone + "，您的提现已经到账，注意查看，感谢您的支持！"));
            HttpUtil.sendPost(Url, map);
        }
        //修改状态
        withdrawDao.updateState(id);
        result.put("code", 3);
        result.put("msg", "修改成功");
        return result;
    }

    /**
     * 订单管理--> 查看所有的订单，按时间排序
     */
    @Override
    public PageInfo<SysOrderListModel> findAllOrder(Integer pageNum) {
        PageHelper.startPage(pageNum, 15);
        List<SysOrderListModel> all = orderinfoDao.findAll();
        return new PageInfo<SysOrderListModel>(all);
    }

}
