package com.butt.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: JavaTansanlin
 * @Description: 竞猜
 * @Date: Created in 22:42 2018/9/13
 * @Modified By:
 */
@Data
public class Guessing {
    /** 主键 */
    private Integer id;
    /** 开奖期数 */
    private Integer code;
    /** 开奖结果集 */
    private String result;
    /** 最后一个开奖数字 */
    private Integer lastnum;
    /** 鸡藕结果： J-鸡，O-藕 */
    private String jo;
    /** 大小结果 ：D-大，X-小*/
    private String dx;
    /** 开奖时间 */
    private Date registertime;
}
