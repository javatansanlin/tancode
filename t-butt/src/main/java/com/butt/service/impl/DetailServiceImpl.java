package com.butt.service.impl;

import com.butt.dao.IntegraldetailDao;
import com.butt.dao.MoneydetailDao;
import com.butt.entity.Integraldetail;
import com.butt.entity.Moneydetail;
import com.butt.service.DetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: JavaTansanlin
 * @Description: 资金，积分相关的业务逻辑
 * @Date: Created in 21:25 2018/9/16
 * @Modified By:
 */
@Service
@Transactional
public class DetailServiceImpl implements DetailService {

    /** 资金明细dao */
    @Autowired
    private MoneydetailDao moneydetailDao;

    /** 积分明细dao */
    @Autowired
    private IntegraldetailDao integraldetailDao;

    /** 根据用户oid查询用户资金明细 */
    @Override
    public PageInfo<Moneydetail> findMDeatail(String oid ,Integer index) {
        PageHelper.startPage(index, 15);
        List<Moneydetail> all = moneydetailDao.findAllByOid(oid);
        return new PageInfo<Moneydetail>(all);
    }

    /** 根据用户oid查询用户积分明细 */
    @Override
    public PageInfo<Integraldetail> findIDetail(String oid ,Integer index) {
        PageHelper.startPage(index, 15);
        List<Integraldetail> all = integraldetailDao.findAllByOid(oid);
        return new PageInfo<Integraldetail>(all);
    }
}
