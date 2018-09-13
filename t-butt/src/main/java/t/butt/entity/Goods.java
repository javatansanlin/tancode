package t.butt.entity;

import lombok.Data;

/**
 * @Author: JavaTansanlin
 * @Description: 商品
 * @Date: Created in 22:33 2018/9/13
 * @Modified By:
 */
@Data
public class Goods {
    /** 主键 */
    private Integer id;
    /** 类型：1.正常商品，2积分商品 */
    private Integer type;
    /** 商品名 */
    private String name;
    /** 商品价格 */
    private Double price;
    /** 商品积分 */
    private Double integral;
    /** 商品图片一 */
    private String img;
    /** 商品图片二 */
    private String imgt;
    /** 商品图片三 */
    private String imgtt;
    /** 商品描述 */
    private String remarke;
}
