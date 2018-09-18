package com.butt.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: JavaTansanlin
 * @Description: 用户充值记录表
 * @Date: Created in 22:57 2018/9/18
 * @Modified By:
 */
@Data
public class Recharge {
    /** 主键 */
    private Integer id;
    /** 用户id */
    private Integer uid;
    /** 充值金额 */
    private Double money;
    /** 系统订单号 */
    private String code;
    /** 支付订单号 */
    private String orderCode;
    /** 充值时间 */
    private Date registertime;
}
