package com.butt.controller;

import com.butt.entity.Integraldetail;
import com.butt.entity.Moneydetail;
import com.butt.service.DetailService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: JavaTansanlin
 * @Description: 资金与积分明细相关的接口
 * @Date: Created in 20:43 2018/9/16
 * @Modified By:
 */
@RestController
@RequestMapping("/mem/detail")
public class MIdetailController {

    /** 明细的service */
    @Autowired
    private DetailService detailService;

    /** 根据用户oid查询用户积分明细 */
    @PostMapping("/money")
    public PageInfo<Moneydetail> money(String oid ,Integer index){
        return detailService.findMDeatail(oid ,index);
    }

    /** 根据用户oid查询用户资金明细 */
    @PostMapping("/integral")
    public PageInfo<Integraldetail> integral(String oid ,Integer index){
        return  detailService.findIDetail(oid ,index);
    }
}
