package com.butt.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: JavaTansanlin
 * @Description: 用户钱包明细
 * @Date: Created in 23:05 2018/9/13
 * @Modified By:
 */
@Data
public class Moneydetail {
    /** 主键 */
    private Integer id;
    /** 用户id */
    private Integer uId;
    /** 金额 */
    private Double money;
    /** 当前金额 */
    private Double cuMoney;
    /** 备注 */
    private String remarke;
    /** 操作时间 */
    private Date registertime;
}
