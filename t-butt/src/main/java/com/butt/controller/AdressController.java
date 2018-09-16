package com.butt.controller;

import com.butt.entity.Address;
import com.butt.service.AddreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 用户收货地址相关的接口
 * @Date: Created in 11:44 2018/9/15
 * @Modified By:
 */
@RestController
@RequestMapping("/mem/adress")
public class AdressController {

    /** 收货地址service */
    @Autowired
    private AddreService addreService;

    /** 根据用户的oid查询用户的所有收货地址 */
    @PostMapping("/findAll")
    public List<Address> findAll(String oid){
        return  addreService.findAllByOid(oid);
    }

    /** 新增收货地址 */
    @PostMapping("/addAddre")
    public Map<String ,Object> addAddre(String oid ,String name ,String phone ,String addre){
        return addreService.addAddre(oid ,name ,phone,addre);
    }

    /** 修改收货地址 */
    @PostMapping("editAddre")
    public Map<String ,Object> editAddre(String oid ,Integer id ,String name ,String phone ,String addre){
        return addreService.editAddre(oid ,id ,name ,phone ,addre);
    }

    /** 删除收货地址 **/
    @PostMapping("deleAddre")
    public Map<String ,Object> deleAddre(String oid ,Integer id){
        return addreService.deleAddre(oid ,id);
    }

}
