package com.butt.service;

import com.butt.config.wx.PayResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: JavaTansanlin
 * @Description: 充值相关的业务
 * @Date: Created in 11:11 2018/9/26
 * @Modified By:
 */
public interface PayService {
    /** 充值 */
    PayResponse recharge(Double money , String oid , HttpServletRequest request);
    /** 充值回调 */
    void noty(String notifyData);
}
