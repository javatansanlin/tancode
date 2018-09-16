package com.butt.dao;

import com.butt.entity.Address;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @Author: JavaTansanlin
 * @Description: 地址dao
 * @Date: Created in 18:46 2018/9/15
 * @Modified By:
 */
@Component
public interface AddressDao {

    /** 根据用户id和地址id查询地址 */
    @Select("SELECT * FROM address WHERE U_ID = #{uid} AND ID = #{id}")
    Address findAddreByUidAndID(@Param("uid") Integer uid ,@Param("id") Integer id);

}
