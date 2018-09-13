package t.butt.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: JavaTansanlin
 * @Description: 用户银行卡
 * @Date: Created in 22:24 2018/9/13
 * @Modified By:
 */
@Data
public class Bankcard {
    /** 主键 */
    private Integer id;
    /** 用户id */
    private Integer uId;
    /** 银行卡号 */
    private String code;
    /** 持卡人姓名 */
    private String name;
    /** 预留手机号 */
    private String phone;
    /** 开户地址 */
    private String area;
    /** 添加时间 */
    private Date registertime;
}
