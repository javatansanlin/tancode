package com.butt.dao;

import com.butt.entity.Member;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @Author: JavaTansanlin
 * @Description: 用户的dao
 * @Date: Created in 0:49 2018/9/15
 * @Modified By:
 */
@Component
public interface MemberDao {

    //根据oid查询出该用户
    @Select("SELECT * FROM member WHERE OID = #{oid}")
    Member findMemByOid(String oid);

    //根据oid查询用户并且上锁
    @Select("SELECT * FROM member WHERE OID = #{oid} FOR UPDATE")
    Member findMemByOidAndLock(String oid);

    //根据用户id增加用户余额
    @Update("UPDATE member SET MONEY= MONEY+#{updateMoney} WHERE ID=#{id}")
    int increaseMoney(@Param("updateMoney") double updateMoney ,@Param("id") Integer id);

    //增加用户积分
    @Update("UPDATE member SET INTEGRAL= INTEGRAL+#{updateGra} WHERE ID=#{id}")
    int increaseIntegra(@Param("updateGra") double updateGra ,@Param("id") Integer id);

    //减去用户余额
    @Update("UPDATE member SET MONEY= MONEY-#{updateMoney} WHERE ID=#{id}")
    int mimuMoney(@Param("updateMoney") double updateMoney ,@Param("id") Integer id);

    //减去用户积分
    @Update("UPDATE member SET INTEGRAL= INTEGRAL-#{updateGra} WHERE ID=#{id}")
    int mimuIntegra(@Param("updateGra") double updateGra ,@Param("id") Integer id);
}
