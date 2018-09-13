package t.butt.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: JavaTansanlin
 * @Description: 用户
 * @Date: Created in 22:18 2018/9/13
 * @Modified By:
 */
@Data
public class Member {
    /** 主键 */
    private Integer id;
    /** 微信openid */
    private String oid;
    /** 用户头像 */
    private String img;
    /** 用户名称 */
    private String name;
    /** 用户手机号 */
    private String phone;
    /** 用户金额 */
    private Double money;
    /** 用户积分 */
    private Double integral;
    /** 注册时间 */
    private Date registertime;
}
