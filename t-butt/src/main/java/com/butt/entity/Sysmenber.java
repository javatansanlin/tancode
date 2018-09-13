package com.butt.entity;

import lombok.Data;

/**
 * @Author: JavaTansanlin
 * @Description: 后台管理员
 * @Date: Created in 23:19 2018/9/13
 * @Modified By:
 */
@Data
public class Sysmenber {
    /** 主键 */
    private Integer id;
    /** 登陆名 */
    private String name;
    /** 登陆密码 */
    private String password;
}
