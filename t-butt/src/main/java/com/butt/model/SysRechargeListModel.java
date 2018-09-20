package com.butt.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author: JavaTansanlin
 * @Description: 后台显示充值明细模板
 * @Date: Created in 11:37 2018/9/19
 * @Modified By:
 */
@Data
public class SysRechargeListModel {
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
    /** 用户名 */
    private String name;
}
