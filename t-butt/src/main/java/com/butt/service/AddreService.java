package com.butt.service;

import com.butt.entity.Address;

import java.util.List;
import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 用户收货地址相关的额业务接口
 * @Date: Created in 16:07 2018/9/16
 * @Modified By:
 */
public interface AddreService {

    /** 根据用户的oid查询用户的所有收货地址 */
    List<Address> findAllByOid(String oid);
    /** 新增收货地址 */
    Map<String, Object> addAddre(String oid ,String name ,String phone ,String addre);
    /** 修改收货地址 */
    Map<String,Object> editAddre(String oid ,Integer id ,String name ,String phone ,String addre);
    /** 删除收货地址 **/
    Map<String ,Object> deleAddre(String oid ,Integer id);
}
