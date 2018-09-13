package t.butt.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: JavaTansanlin
 * @Description: 用户提现
 * @Date: Created in 22:29 2018/9/13
 * @Modified By:
 */
@Data
public class Withdraw {
    /** 主键id */
    private Integer id;
    /** 用户id */
    private Integer uId;
    /** 提现金额 */
    private Double wMoney;
    /** 提现时间 */
    private Date wTime;
    /** 提现到的银行卡 */
    private Integer bankcard;
    /** 提现状态 : 1-未完成，3-已完成 */
    private Integer state;
}
