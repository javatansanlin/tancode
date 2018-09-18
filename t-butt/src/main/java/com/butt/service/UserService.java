package com.butt.service;

import com.butt.entity.Member;

import javax.servlet.http.HttpSession;
import java.util.List;
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

    /** 查询所有的用户资料，有用户名条件 ，按注册时间排序*/
    List<Member> findAll(String name);

    /** 查看今日注册数和总用户数 */
    Map<String,Object> findCount();
}
