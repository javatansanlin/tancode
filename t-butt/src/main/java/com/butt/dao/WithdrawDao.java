package com.butt.dao;

import com.butt.entity.Withdraw;
import com.butt.model.SysWithdrawListModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: JavaTansanlin
 * @Description: 提现dao
 * @Date: Created in 23:52 2018/9/16
 * @Modified By:
 */
@Component
public interface WithdrawDao {

    /** 插入一条记录 */
    @Insert("insert into WITHDRAW(U_ID,W_MONEY,STATE,CODE,NAME,PHONE,AREA,W_TIME) values (#{uId},#{wMoney},#{state},#{code},#{name},#{phone},#{area},NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertOne(Withdraw withdraw);

    /** 修改提现状态未已提现 */
    @Update("update WITHDRAW set STATE=2 where ID=#{id}")
    int updateState(Integer id);

    /** 查询所有的指定状态提现记录，按时间排序 */
    @Select("SELECT w.*,m.NAME AS uname FROM withdraw AS w LEFT JOIN member AS m ON w.U_ID=m.ID WHERE w.STATE=#{type} ORDER BY w.W_TIME DESC")
    List<SysWithdrawListModel> findWidByState(@Param("type") Integer type);

    /** 查看指定时间的充值总额 */
    @Select({
            "<script>",
            "SELECT COUNT(W_MONEY) FROM withdraw WHERE 1=1",
            "<if test='start != null and start !=&quot;&quot;'>AND W_TIME &gt;= #{start}</if>",
            "<if test='end != null and end !=&quot;&quot;'>AND W_TIME &lt;= #{end}</if>",
            "</script>"
    })
    Double fingMoneyCount(@Param("start") String start ,@Param("end") String end);

    /** 根据提现id查询该记录的用户手机号 */
    @Select("SELECT m.PHONE FROM withdraw AS w LEFT JOIN member AS m ON w.U_ID = m.ID WHERE w.ID=#{id};")
    String findUPhoneById(Integer id);
}
