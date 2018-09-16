package com.butt.controller;

import com.butt.service.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 购买下单相关的接口
 * @Date: Created in 11:26 2018/9/15
 * @Modified By:
 */
@RestController
@RequestMapping("/mem/buy")
public class BuyController {

    /** 业务dao */
    @Autowired
    private BuyService buyService;

    /** 点击购买商品 */
    @PostMapping("/shop")
    public Map<String ,Object> shop(String oid ,Integer goodsId ,int count){
        return buyService.buyTheShop(oid,goodsId,count);
    }

    /**
     * 确认收货，选择收货地址
     * type-类型：2-下单后选择确认收货
     *           6-未中奖选择提货
     *           8-中奖选择提货，物品X2
     * */
    @PostMapping("/takeGoods")
    public Map<String ,Object> takeGoods(Integer orderId ,String oid ,Integer type ,Integer addressId){
        return buyService.takeGoods(orderId ,oid ,type, addressId);
    }

    /** 参与促销，选择大小鸡藕 */
    @PostMapping("/partCuxiao")
    public Map<String ,Object> partCuxiao(Integer orderId ,String oid ,String guessResult){
        return buyService.partCuxiao(orderId ,oid ,guessResult);
    }

    /**
     * 参与促销后兑换积分
     * type-类型：7-未中奖选择兑换积分
     *           10-中奖选择兑换积分1=100
     * */
    @PostMapping("/changeIntegra")
    public Map<String ,Object> changeIntegra(Integer orderId ,String oid ,Integer type){
        return buyService.changeIntegra(orderId ,oid ,type);
    }

    /** 中奖选择兑换金额 */
    @PostMapping("changeMoney")
    public Map<String ,Object> changeMoney(Integer orderId ,String oid){
        return buyService.changeMoney(orderId ,oid);
    }

}
