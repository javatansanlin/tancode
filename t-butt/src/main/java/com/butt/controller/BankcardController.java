package com.butt.controller;

import com.butt.entity.Bankcard;
import com.butt.service.BankcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 银行卡相关的接口
 * @Date: Created in 0:07 2018/9/17
 * @Modified By:
 */
@RestController
@RequestMapping("/mem/card")
public class BankcardController {

    /** 银行卡业务 */
    @Autowired
    BankcardService bankcardService;

    /** 根据用户oid查询用户名下所有的银行卡 */
    @PostMapping("/findAll")
    public List<Bankcard> findAll(String oid){
        return bankcardService.findAllByOid(oid);
    }

    /** 根据id删除银行卡 */
    @PostMapping("/dele")
    public Map<String ,Object> dele(String oid ,Integer id){
        return bankcardService.deleCard(oid ,id);
    }

    /** 增加一个银行卡 */
    @PostMapping("/add")
    public Map<String ,Object> add(String oid ,String code ,String name ,String phone ,String area){
        return bankcardService.addCard(oid ,code,name,phone,area);
    }

    /** 修改一个银行卡 */
    @PostMapping("edit")
    public Map<String ,Object> edit(String oid ,Integer id ,String code ,String name ,String phone ,String area){
        return bankcardService.editCard(oid,id,code,name,phone,area);
    }
}
