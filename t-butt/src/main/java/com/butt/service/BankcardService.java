package com.butt.service;

import com.butt.entity.Bankcard;

import java.util.List;
import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 银行卡相关业务
 * @Date: Created in 0:08 2018/9/17
 * @Modified By:
 */
public interface BankcardService {

    /** 根据用户oid查询用户名下所有的银行卡 */
    List<Bankcard> findAllByOid(String oid);

    /** 根据id删除银行卡 */
    Map<String ,Object> deleCard(String oid ,Integer id);

    /** 增加一个银行卡 */
    Map<String ,Object> addCard(String oid ,String code ,String name ,String phone ,String area);

    /** 修改一个银行卡 */
    Map<String ,Object> editCard(String oid ,Integer id ,String code ,String name ,String phone ,String area);
}
