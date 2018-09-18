package com.butt.dao;

import com.butt.entity.Moneydetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: JavaTansanlin
 * @Description: 用户金额明细dao
 * @Date: Created in 16:36 2018/9/15
 * @Modified By:
 */
@Component
public interface MoneydetailDao {

    /** 插入一条记录 */
    @Insert("insert into MONEYDETAIL(U_ID,MONEY,CU_MONEY,REMARKE,REGISTERTIME) values (#{uId},#{money},#{cuMoney},#{remarke},NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertOne(Moneydetail moneydetail);

    /** 根据用户oid查询全部的资金明细 */
    @Select("SELECT md.* FROM moneydetail AS md LEFT JOIN member AS mem ON md.U_ID = mem.ID WHERE mem.OID=#{oid} ORDER BY REGISTERTIME DESC")
    List<Moneydetail> findAllByOid(String oid);
}
