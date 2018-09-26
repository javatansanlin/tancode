package com.butt.dao;

import com.butt.entity.Guessing;
import com.butt.model.SysGussOrderListModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: JavaTansanlin
 * @Description: 竞猜dao
 * @Date: Created in 23:23 2018/9/14
 * @Modified By:
 */
@Component
public interface GuessingDao {

    /** 根据id（即是开奖期数）查询竞猜数据 */
    @Select("SELECT * FROM guessing WHERE ID = #{id}")
    Guessing findGuessById(Integer id);

    /** 插入一条数据 */
    @Insert("insert into guessing(LASTNUM,JO,DX,REGISTERTIME) values (#{lastnum},#{jo},#{dx},NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertOne(Guessing guessing);

    /** 根据开奖期数更新数据 **/
    @Update("UPDATE guessing SET LASTNUM=#{lastnum},JO=#{jo},DX=#{dx},REGISTERTIME=NOW() WHERE ID=#{id}")
    int updateById(Guessing guessing);

    /** 获取最近一条未开奖的数据 */
    @Select("SELECT * FROM guessing WHERE LASTNUM IS NULL ORDER BY ID DESC LIMIT 1")
    Guessing findOneNotGuess();

    /** 查询未开奖并且订单状态为3的订单信息和竞猜信息 */
    @Select("SELECT " +
            "oi.ID," +
            "oi. CODE," +
            "oi.PRICE," +
            "oi.GUESS," +
            "oi.GUESSTIME ,mem.NAME, gu.DX," +
            "oi.REGISTERTIME as ordertime, " +
            "mem.PHONE, " +
            "gu.JO " +
            "FROM " +
            "orderinfo AS oi " +
            "LEFT JOIN guessing AS gu ON oi.GUESSID = gu.ID " +
            "LEFT JOIN member AS mem ON mem.ID = oi.U_ID " +
            "WHERE " +
            "oi.STATE = 3 " +
            "AND gu.LASTNUM IS NOT NULL")
    List<SysGussOrderListModel> findNotOpenGuAndOi();

    /** 查询指定时间的开奖信息 */
    @Select({
            "<script>",
            "SELECT * FROM guessing WHERE 1=1 AND LASTNUM IS NOT NULL ",
            "<if test='start != null and start !=&quot;&quot;'>AND REGISTERTIME &gt;= #{start}</if>",
            "<if test='end != null and end !=&quot;&quot;'>AND REGISTERTIME &lt;= #{end}</if>",
            "</script>"
    })
    List<Guessing> findListGu(@Param("start") String start ,@Param("end") String end);
}
