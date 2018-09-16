package com.butt.service;

import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 购买下单业务逻辑接口
 * @Date: Created in 14:03 2018/9/15
 * @Modified By:
 */
public interface BuyService {
    /** 点击购买商品 */
    Map<String ,Object> buyTheShop(String oid ,Integer goodsId ,int count);

    /**
     * 确认收货，选择收货地址
     * type-类型：2-下单后选择确认收货
     *           6-未中奖选择提货
     *           8-中奖选择提货，物品X2
     * */
    Map<String ,Object> takeGoods(Integer orderId ,String oid ,Integer type ,Integer addressId);

    /** 参与促销，选择大小鸡藕 */
    Map<String ,Object> partCuxiao(Integer orderId ,String oid ,String guessResult);

    /**
     * 参与促销后兑换积分
     * type-类型：7-未中奖选择兑换积分
     *           10-中奖选择兑换积分1=100
     * */
    Map<String ,Object> changeIntegra(Integer orderId ,String oid ,Integer type);

    /** 中奖选择兑换金额 */
    Map<String ,Object> changeMoney(Integer orderId ,String oid);

}
