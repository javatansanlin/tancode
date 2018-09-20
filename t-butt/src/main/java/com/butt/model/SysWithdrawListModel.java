package com.butt.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author: JavaTansanlin
 * @Description: 后台显示提现记录模板
 * @Date: Created in 23:16 2018/9/19
 * @Modified By:
 */
@Data
public class SysWithdrawListModel {
    /** 主键id */
    private Integer id;
    /** 用户id */
    private Integer uId;
    /** 提现金额 */
    private Double wMoney;
    /** 提现时间 */
    private Date wTime;
    /** 提现状态 : 1-未完成，3-已完成 */
    private Integer state;
    /** 银行卡号 */
    private String code;
    /** 持卡人姓名 */
    private String name;
    /** 预留手机号 */
    private String phone;
    /** 开户地址 */
    private String area;
    /** 用户名 */
    private String uname;
}
