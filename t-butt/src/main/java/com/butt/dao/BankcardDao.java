package com.butt.dao;

import com.butt.entity.Bankcard;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: JavaTansanlin
 * @Description: 银行卡管理dao
 * @Date: Created in 23:09 2018/9/16
 * @Modified By:
 */
@Component
public interface BankcardDao {

    /** 插入一条记录 */
    @Insert("insert into BANKCARD(U_ID,CODE,NAME,PHONE,AREA,REGISTERTIME) values (#{uId},#{code},#{name},#{phone},#{area},NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertOne(Bankcard bankcard);

    /** 更新一条记录 */
    @Update("UPDATE BANKCARD SET CODE=#{code},NAME=#{name},PHONE=#{phone},AREA={area} WHERE ID = #{id}")
    int updateByID(Bankcard bankcard);

    /** 根据id删除一条记录 */
    @Delete("DELETE FROM bankcard WHERE ID = #{id}")
    int deleByID(Bankcard bankcard);

    /** 根据用户oid查询出该用户的所有银行卡记录 */
    @Select("SELECT car.* FROM bankcard AS car LEFT JOIN member AS mem ON mem.ID = car.U_ID WHERE mem.OID = #{oid}")
    List<Bankcard> findAllCardByOid(String oid);

    /** 根据用户id和记录id查询出该记录 */
    @Select("SELECT * FROM bankcard WHERE ID=#{id} AND U_ID=#{uid}")
    Bankcard findCardByUidAndID(@Param("id") Integer id ,@Param("uid") Integer uid);
}
