package com.butt.dao;

import com.butt.entity.Recharge;
import com.butt.model.SysRechargeListModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: JavaTansanlin
 * @Description: 充值记录相关的dao
 * @Date: Created in 2:54 2018/9/19
 * @Modified By:
 */
@Component
public interface RechargeDao {

    /** 插入一条记录 */
    @Insert("insert into RECHARGE(U_ID,MONEY,CODE,ORDER_CODE,REGISTERTIME values (#{uid},#{money},#{code},#{orderCode},NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertOne(Recharge recharge);

    /** 查询所有的充值记录，按时间排序 */
    @Select("SELECT rc.*,mem.NAME FROM recharge AS rc LEFT JOIN member AS mem ON rc.U_ID = mem.ID ORDER BY rc.REGISTERTIME DESC")
    List<SysRechargeListModel> findAll();

    /** 查看指定时间的充值总额 */
    @Select({
            "<script>",
            "SELECT SUM(MONEY) FROM RECHARGE WHERE 1=1",
            "<if test='start != null and start !=&quot;&quot;'>AND REGISTERTIME &gt;= #{start}</if>",
            "<if test='end != null and end !=&quot;&quot;'>AND REGISTERTIME &lt;= #{end}</if>",
            "</script>"
    })
    Double fingMoneyCount(@Param("start") String start ,@Param("end") String end);

    /** 根据系统订单号查询订单数 */
    @Select("SELECT COUNT(ID) FROM recharge WHERE CODE=#{code}")
    int findConByCode(String code);

}
