package com.butt.service;

import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 用户的相关业务接口
 * @Date: Created in 0:48 2018/9/15
 * @Modified By:
 */
public interface UserService {

    /** 根据用户oid查询用户信息 */
    Map<String,Object> findUserByOid(String oid);
}
