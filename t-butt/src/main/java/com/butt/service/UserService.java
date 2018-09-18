package com.butt.service;

import javax.servlet.http.HttpSession;
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

    /** 用户提现 */
    Map<String ,Object> userWithdraw(String oid ,double withdrawMoney ,int carId);

    /** 绑定手机号 */
    Map<String ,Object> bandPhone(HttpSession session, String oid , String code, String phone);
}
