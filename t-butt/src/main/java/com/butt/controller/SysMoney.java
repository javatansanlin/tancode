package com.butt.controller;

import com.butt.model.SysRechargeListModel;
import com.butt.model.SysWithdrawListModel;
import com.butt.service.SysService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 财务管理相关的接口
 * @Date: Created in 22:47 2018/9/18
 * @Modified By:
 */
@RestController
@RequestMapping("/sys/money")
public class SysMoney {

    /** 业务接口 */
    @Autowired
    private SysService sysService;

    /** 查看所有充值，按时间排序 */
    @PostMapping("/findReAll")
    public PageInfo<SysRechargeListModel> findAll(Integer pageNum){
        if (pageNum==null || pageNum>=0){
            pageNum = 1;
        }
        return sysService.findAll(pageNum);
    }

    /** 查看所有未提现成功的提现记录，按时间排序 */
    @PostMapping("/findWiAll")
    public PageInfo<SysWithdrawListModel> findWiAll(Integer pageNum){
        if (pageNum==null || pageNum>=0){
            pageNum = 1;
        }
        return sysService.findWiAll(pageNum);
    }

    /** 查询今日收入和总收入 */
    @PostMapping("/findReCount")
    public Map<String ,Object> findReCount(){
        return sysService.findReCount();
    }

    /** 修改提现状态为已转账，并且给用户发送信息 */
    @PostMapping("/changeReState")
    public Map<String ,Object> changeReState(Integer id){
        return sysService.changeReState(id);
    }
}
