package com.butt.dao;

import com.butt.entity.Integraldetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: JavaTansanlin
 * @Description: 用户积分明细dao
 * @Date: Created in 16:41 2018/9/15
 * @Modified By:
 */
@Component
public interface IntegraldetailDao {

    /** 插入一条记录 */
    @Insert("insert into INTEGRALDETAIL(U_ID,INTEGRAL,CU_INTEGRAL,REMARKE,REGISTERTIME) values (#{uId},#{integral},#{cuIntegral},#{remarke},NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertOne(Integraldetail integraldetail);

    /** 根据用户oid查询全部的积分明细 */
    @Select("SELECT idt.* FROM integraldetail AS idt LEFT JOIN member AS mem ON idt.U_ID = mem.ID WHERE mem.OID=#{oid} ORDER BY REGISTERTIME DESC")
    List<Integraldetail> findAllByOid(String oid);

}
