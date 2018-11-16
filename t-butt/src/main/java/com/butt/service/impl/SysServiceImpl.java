package com.butt.service.impl;

import com.butt.dao.GuessingDao;
import com.butt.dao.OrderinfoDao;
import com.butt.dao.RechargeDao;
import com.butt.dao.WithdrawDao;
import com.butt.entity.Guessing;
import com.butt.entity.Orderinfo;
import com.butt.model.SysGussOrderListModel;
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

    /** 充值dao */
    @Autowired
    private RechargeDao rechargeDao;

    /** 提现dao */
    @Autowired
    private WithdrawDao withdrawDao;

    /** 订单dao */
    @Autowired
    private OrderinfoDao orderinfoDao;

    /** 竞猜dao */
    @Autowired
    private GuessingDao guessingDao;

    /**
     * 财务管理-->查看所有充值，按时间排序
     */
    @Override
    public PageInfo<SysRechargeListModel> findAll(Integer pageNum) {
        PageHelper.startPage(pageNum, 10);
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
            map.put("content", new String("亲爱的用户:" + phone + "，您的提现已经到账，注意查看，感谢您的支持！"));
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
    public PageInfo<SysOrderListModel> findAllOrder(Integer pageNum ,Integer type) {
        PageHelper.startPage(pageNum, 10);
        List<SysOrderListModel> all = null;
        if (type==1){
            all = orderinfoDao.findTiHuoAll();
        }else {
            all = orderinfoDao.findAll();
        }
        for (SysOrderListModel s:all) {
            if (s.getState()==1){
                s.setStateName("未升级");
            }else if (s.getState()==2){
                s.setStateName("提货X1");
            }else if (s.getState()==3){
                s.setStateName("升级中");
            }else if (s.getState()==4){
                s.setStateName("升级失败");
            }else if (s.getState()==5){
                s.setStateName("升级成功");
            }else if (s.getState()==6){
                s.setStateName("提货X1");
            }else if (s.getState()==7){
                s.setStateName("升级失败，兑换积分");
            }else if (s.getState()==8){
                s.setStateName("提货X2");
            }else if (s.getState()==9){
                s.setStateName("升级成功");
            }else if (s.getState()==10){
                s.setStateName("升级成功，兑换积分");
            }else if (s.getState()==33){
                s.setStateName("订单发货，订单完成");
            }
        }
        return new PageInfo<SysOrderListModel>(all);
    }

    /** 订单管理--> 修改订单状态未已发货 */
    @Override
    public Map<String, Object> upOrderState(Integer id) {
        Map<String ,Object> result = new HashMap<>();
        //判断参数
        if (id==null || id<=0){
            result.put("code" ,1);
            result.put("msg" ,"参数错误");
            return result;
        }
        //查询订单
        Orderinfo order = orderinfoDao.findOrderByID(id);
        if (order==null || ( order.getState()!=2 && order.getState()!=6 && order.getState()!=8 ) ){
            result.put("code" ,2);
            result.put("msg" ,"该订单不能操作");
            return result;
        }
        //修改状态
        order.setState(33);
        orderinfoDao.updateOrderAddreAndState(order);
        result.put("code" ,3);
        result.put("msg" ,"修改成功！");
        return result;
    }

    /** 订单管理--> 查看今日订单数，总订单数 */
    @Override
    public Map<String, Object> findODCount() {
        Map<String ,Object> result = new HashMap<>();
        //查询总订单数
        Integer all = orderinfoDao.findOrderCount(null, null);
        //查询今天订单数
        Integer today = orderinfoDao.findOrderCount(DateUtil.getStringDate(DateUtil.getStartTime()), DateUtil.getStringDate(DateUtil.getEndTime()));
        result.put("code" ,3);
        result.put("today" ,today);
        result.put("all" ,all);
        return result;
    }

    /** 开奖管理--> 查询参加促销并且未开奖的订单 */
    @Override
    public PageInfo<SysGussOrderListModel> findNotOGO(Integer pageNum) {
        PageHelper.startPage(pageNum, 10);
        List<SysGussOrderListModel> all = guessingDao.findNotOpenGuAndOi();
        //逻辑处理
        if (all!=null && all.size()>0){
            for (SysGussOrderListModel aol:all) {
                //判断用户选择的竞猜结果
                if ("D".equals(aol.getGuess()) || "X".equals(aol.getGuess())){//大小
                    if (aol.getDx().equals(aol.getGuess())){
                        aol.setIsGu("中奖");
                    }else {
                        aol.setIsGu("未中奖");
                    }
                }
                if ("J".equals(aol.getGuess()) || "O".equals(aol.getGuess())) {//鸡藕
                    if (aol.getJo().equals(aol.getGuess())){
                        aol.setIsGu("中奖");
                    }else {
                        aol.setIsGu("未中奖");
                    }
                }
                //处理奇偶字符串
                if ("D".equals(aol.getGuess())){
                    aol.setGuess("大");
                }else if("X".equals(aol.getGuess())){
                    aol.setGuess("小");
                }else if ("J".equals(aol.getGuess())){
                    aol.setGuess("鸡");
                }else if ("O".equals(aol.getGuess())){
                    aol.setGuess("藕");
                }
                if ("J".equals(aol.getJo())){
                    aol.setJo("鸡");
                }else {
                    aol.setJo("藕");
                }
                if ("D".equals(aol.getDx())){
                    aol.setDx("大");
                }else {
                    aol.setDx("小");
                }
            }
        }
        return new PageInfo<SysGussOrderListModel>(all);
    }

    /** 开奖管理--> 修改订单结果为未中奖 */
    @Override
    public Map<String, Object> upFail(Integer id, String gu) {
        Map<String ,Object> result = new HashMap<>();
        //判断参数
        if (id==null || id<=0 || gu==null || ( !"D".equals(gu) && !"X".equals(gu) && !"J".equals(gu) && !"O".equals(gu) )){
            result.put("code" ,1);
            result.put("msg" ,"参数不正确");
            return result;
        }
        //查询订单
        Orderinfo order = orderinfoDao.findOrderByID(id);
        if (order==null){
            result.put("code" ,2);
            result.put("msg" ,"订单错误");
            return result;
        }
        //修改用户的竞猜结果
        order.setGuess(gu);
        orderinfoDao.updateOrderGuessAndState(order);

        result.put("code" ,3);
        result.put("msg" ,"操作成功");
        return result;
    }

    /** 查看今天所有的开奖信息 */
    @Override
    public PageInfo<Guessing> todayGuDetail(Integer pageNum) {
        PageHelper.startPage(pageNum, 10);
        List<Guessing> listGu = guessingDao.findListGu(DateUtil.getStringDate(DateUtil.getStartTime()), DateUtil.getStringDate(DateUtil.getEndTime()));
        for (Guessing gu:listGu) {
            if ("D".equals(gu.getDx())){
                gu.setDx("大");
            }else {
                gu.setDx("小");
            }
            if ("J".equals(gu.getJo())){
                gu.setJo("鸡");
            }else {
                gu.setJo("藕");
            }
        }
        return new PageInfo<Guessing>(listGu);
    }
}
