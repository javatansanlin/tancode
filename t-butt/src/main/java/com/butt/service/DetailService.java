package com.butt.service;

import com.butt.entity.Integraldetail;
import com.butt.entity.Moneydetail;
import com.github.pagehelper.PageInfo;

/**
 * @Author: JavaTansanlin
 * @Description: 资金，积分相关的业务逻辑
 * @Date: Created in 21:24 2018/9/16
 * @Modified By:
 */
public interface DetailService {

    /** 根据用户oid查询用户资金明细 */
    PageInfo<Moneydetail> findMDeatail(String oid ,Integer index);

    /** 根据用户oid查询用户积分明细 */
    PageInfo<Integraldetail> findIDetail(String oid ,Integer index);
}
