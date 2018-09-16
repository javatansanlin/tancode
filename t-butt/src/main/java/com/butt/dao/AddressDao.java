package com.butt.dao;

import com.butt.entity.Address;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

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

    /** 查询用户oid下的所有地址 */
    @Select("SELECT * FROM address AS ade LEFT JOIN member AS u ON ade.U_ID = u.ID WHERE u.OID = #{oid}")
    List<Address> findAllByOid(String oid);

    /** 根据用户的oid和地址id查出指定的地址信息 */
    @Select("SELECT * FROM address AS ade LEFT JOIN member AS u ON ade.U_ID = u.ID WHERE u.OID = #{oid} AND ade.ID = #{id}")
    Address findOneByOidAndID(@Param("oid") String oid ,@Param("id") Integer id);

    /** 插入一条记录 */
    @Insert("insert into address(U_ID,NAME,PHONE,ADDRE) values (#{uId},#{name},#{phone},#{addre})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertOne(Address address);

    /** 根据id更新记录 */
    @Update("UPDATE address SET NAME = #{name},PHONE=#{phone},ADDRE=#{addre} WHERE ID = #{id}")
    int updateByID(Address address);

    /** 根据id删除记录 */
    @Delete("DELETE FROM address WHERE ID = #{id}")
    int deleOne(Integer id);

}
