package com.butt.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author: JavaTansanlin
 * @Description: 后台显示的订单列表模板
 * @Date: Created in 13:52 2018/9/20
 * @Modified By:
 */
@Data
public class SysOrderListModel {
    /** 主键 */
    private Integer id;
    /** 用户id */
    private Integer uId;
    /** 订单编号 */
    private String code;
    /** 商品id */
    private Integer gId;
    /** 数量 */
    private Integer conts;
    /** 价格 */
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
    private String stateName;
    /** 竞猜（D-大，X-小，J-鸡，O-藕） */
    private String guess;
    /** 开奖时间（用于定时去更改该用户订单的状态） */
    private Date guesstime;
    /** 参与竞猜的竞猜id */
    private Integer guessid;
    /** 订单的创建时间 */
    private Date registertime;
    /** 收货人名字 */
    private String addreName;
    /** 收货人电话 */
    private String addrePhone;
    /** 收货人地址 */
    private String addre;
    /** 商品名 */
    private String goodsName;
    /** 用户绑定的手机号 */
    private String uphone;
}
