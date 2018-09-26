package com.butt.service.impl;

import com.butt.config.wx.BestPayTypeEnum;
import com.butt.config.wx.PayRequest;
import com.butt.config.wx.PayResponse;
import com.butt.dao.MemberDao;
import com.butt.dao.MoneydetailDao;
import com.butt.dao.RechargeDao;
import com.butt.entity.Member;
import com.butt.entity.Moneydetail;
import com.butt.entity.Recharge;
import com.butt.service.PayService;
import com.butt.util.Salt;
import org.ietf.jgss.Oid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description:
 * @Date: Created in 11:33 2018/9/26
 * @Modified By:
 */
@Service
@Transactional
public class PayServiceImpl implements PayService {

    /** 用户dao */
    @Autowired
    MemberDao memberDao;

    /** redis操作 */
    @Autowired
    private RedisTemplate redisTemplate;

    /** 用户金额明细dao */
    @Autowired
    MoneydetailDao moneydetailDao;

    /** 充值dao */
    @Autowired
    RechargeDao rechargeDao;

    @Autowired
    private BestPayServiceImpl bestPayService;

    /** 充值 */
    @Override
    public PayResponse recharge(Double money, String oid , HttpServletRequest request) {
        //判断参数
        if (money==null || oid==null || money<=0){
            return null;
        }
        Member mem = memberDao.findMemByOid(oid);
        if (mem==null){
            return null;
        }
        //系统订单号
        String orderNum = Salt.getOrderNum();
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(oid);
        payRequest.setOrderAmount(money);
        payRequest.setOrderId(orderNum);
        payRequest.setOrderName("余额充值");
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        PayResponse payResponse = bestPayService.pay(payRequest);
        //设置订单号到缓存中
        redisTemplate.opsForValue().set(orderNum ,oid);
        return payResponse;
    }

    /** 充值回调 */
    @Override
    public void noty(String notifyData) {
        //1. 验证签名 由SDK去判断
        // 2.支付的状态 由SDK去判断
        // 3.支付金额
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        //执行业务逻辑处理
        //根据用户订单号查询该订单
        String orderId = payResponse.getOrderId();//系统订单号
        Double amount = payResponse.getOrderAmount();//金额
        System.out.println("系统订单号为-->"+orderId);
        System.out.println("金额为-->"+amount);
        //获取该订单号缓存中的用户
        String oid = (String) redisTemplate.opsForValue().get(orderId);
        System.out.println("用户-->"+oid);
        //查询用户
        Member mem = memberDao.findMemByOid(oid);
        if (mem!=null){
            //查询该订单号是否存在,存在便不加钱
            int code = rechargeDao.findConByCode(orderId);
            if (code==0){
                //给该用户插入充值记录，并且加余额
                Moneydetail moneydetail = new Moneydetail();
                moneydetail.setUId(mem.getId());
                moneydetail.setMoney(amount);
                moneydetail.setRemarke("充值");
                moneydetail.setCuMoney(mem.getMoney());
                moneydetailDao.insertOne(moneydetail);

                Recharge recharge = new Recharge();
                recharge.setCode(orderId);
                recharge.setOrderCode(payResponse.getOutTradeNo());
                recharge.setUid(mem.getId());
                recharge.setMoney(amount);
                rechargeDao.insertOne(recharge);

                memberDao.increaseMoney(amount ,mem.getId());
            }
        }
    }
}
