package com.butt.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: JavaTansanlin
 * @Description: 用户积分明细
 * @Date: Created in 23:05 2018/9/13
 * @Modified By:
 */
@Data
public class Integraldetail {
    /** 主键 */
    private Integer id;
    /** 用户id */
    private Integer uId;
    /** 金额 */
    private Double integral;
    /** 当前金额 */
    private Double cuIntegral;
    /** 备注 */
    private String remarke;
    /** 操作时间 */
    private Date registertime;
}
