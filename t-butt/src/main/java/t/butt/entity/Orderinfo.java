package t.butt.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: JavaTansanlin
 * @Description: 订单
 * @Date: Created in 23:10 2018/9/13
 * @Modified By:
 */
@Data
public class Orderinfo {
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
     * */
    private Integer state;
    /** 竞猜（D-大，X-小，J-鸡，O-藕） */
    private String guess;
    /** 开奖时间（用于定时去更改该用户订单的状态） */
    private Date guesstime;
    /** 参与竞猜的竞猜id */
    private Integer guessid;
    /** 订单的创建时间 */
    private Date registertime;
}
