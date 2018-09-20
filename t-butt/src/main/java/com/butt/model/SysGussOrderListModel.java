package com.butt.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author: JavaTansanlin
 * @Description: 后台管理查看未开奖的订单
 * @Date: Created in 22:19 2018/9/20
 * @Modified By:
 */
@Data
public class SysGussOrderListModel {
    /** 主键 */
    private Integer id;
    /** 订单编号 */
    private String code;
    /** 价格 */
    private Double price;
    /** 竞猜（D-大，X-小，J-鸡，O-藕） */
    private String guess;
    /** 开奖时间（用于定时去更改该用户订单的状态） */
    private Date guesstime;
    /** 用户名 */
    private String name;
    /** 开奖的大小结果 */
    private String dx;
    /** 开奖的奇偶结果 */
    private String jo;
    /** 是否中奖 */
    private String isGu;
}
