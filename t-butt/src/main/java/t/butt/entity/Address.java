package t.butt.entity;

import lombok.Data;

/**
 * @Author: JavaTansanlin
 * @Description: 用户收货地址
 * @Date: Created in 22:38 2018/9/13
 * @Modified By:
 */
@Data
public class Address {
    /** 主键 */
    private Integer id;
    /** 用户id */
    private Integer uId;
    /** 收货人 */
    private String name;
    /** 收货人电话 */
    private String phone;
    /** 收货人地址 */
    private String addre;
}
