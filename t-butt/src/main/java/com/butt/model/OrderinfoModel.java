package com.butt.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author: JavaTansanlin
 * @Description: 我的订单查看页面的model
 * @Date: Created in 20:12 2018/9/16
 * @Modified By:
 */
@Data
public class OrderinfoModel {
    /** 主键 */
    private Integer id;
    /** 商品id */
    private Integer gId;
    /** 下单数量 */
    private Integer conts;
    /** 总价格价格 */
    private Double price;
    /** 状态
     * 1-选择确认收货和促销 ，
     * 2-选择确认收货 ，
     * 3-点了促销，正在开奖中，
     * 4-未中奖而且未操作 ，
     * 5-中奖而且未操作，
     * 6-未中奖选择提货，
     * 7-未中奖选择兑换积分，
     * 8-中奖选择提货，
     * 9-中奖选择退货也就是换钱160%，
     * 10-中奖选择兑换积分1=100
     * 33-订单发货，订单完成
     * */
    private Integer state;
    /** 订单的创建时间 */
    private Date registertime;
    /** 商品名称 */
    private String gname;
    /** 商品价格 */
    private Double gprice;
    /** 商品图 */
    private String img;
}
