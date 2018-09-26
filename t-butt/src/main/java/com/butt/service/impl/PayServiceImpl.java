package com.butt.service.impl;

import com.butt.config.wx.BestPayTypeEnum;
import com.butt.config.wx.PayRequest;
import com.butt.config.wx.PayResponse;
import com.butt.dao.MemberDao;
import com.butt.dao.MoneydetailDao;
import com.butt.entity.Member;
import com.butt.service.PayService;
import com.butt.util.Salt;
import org.springframework.beans.factory.annotation.Autowired;
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

    /** 用户金额明细dao */
    @Autowired
    MoneydetailDao moneydetailDao;

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
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(oid);
        payRequest.setOrderAmount(money);
        payRequest.setOrderId(Salt.getOrderNum());
        payRequest.setOrderName("余额充值");
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        PayResponse payResponse = bestPayService.pay(payRequest);
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
    }
}
