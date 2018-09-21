package com.butt.controller;

import com.butt.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 后台商品的操作
 * @Date: Created in 2:31 2018/9/14
 * @Modified By:
 */
@Controller
@RequestMapping("sys")
public class SysGoodsController {

    /** 商品service */
    @Autowired
    private GoodsService goodsService;

    /** 增加商品 */
    @PostMapping("addGoods")
    @ResponseBody
    public Map<String ,Object> addGoods(int type ,String name ,double price ,String remarke ,MultipartFile[] file){
        return goodsService.addGoods(type,name ,price ,remarke ,file);
    }

    /** 修改商品 */
    @PostMapping("editGoods")
    @ResponseBody
    public Map<String ,Object> editGoods(Integer id ,int type ,String name ,double price ,String remarke ,MultipartFile[] file){
        return goodsService.editGoods(id,type,name ,price ,remarke ,file);
    }

}
