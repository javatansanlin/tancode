package com.butt.dao;

import com.butt.entity.Orderinfo;
import com.butt.model.OrderinfoModel;
import com.butt.model.SysOrderListModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: JavaTansanlin
 * @Description: 订单dao
 * @Date: Created in 16:43 2018/9/15
 * @Modified By:
 */
@Component
public interface OrderinfoDao {

    /** 创建初始订单 */
    @Insert("insert into ORDERINFO(U_ID,CODE,G_ID,CONTS,PRICE,STATE,GUESS,GUESSTIME,GUESSID,REGISTERTIME) " +
            "values (#{uId},#{code},#{gId},#{conts},#{price},#{state},#{guess},#{guesstime},#{guessid},NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertOne(Orderinfo orderinfo);

    /** 根据用户id和订单id查询该订单 */
    @Select("SELECT * FROM orderinfo WHERE U_ID = #{uid} AND ID = #{orderId}")
    Orderinfo findOrderByIdAndUid(@Param("uid") Integer uid ,@Param("orderId") Integer orderId);

    /** 更新收货地址和状态 */
    @Update("UPDATE orderinfo SET STATE= #{state},ADDRE_NAME=#{addreName},ADDRE_PHONE=#{addrePhone},ADDRE=#{addre} WHERE ID=#{id}")
    int updateOrderAddreAndState(Orderinfo orderinfo);

    /** 根据用户选择的竞猜结果更新相关信息 */
    @Update("UPDATE orderinfo SET STATE= #{state},GUESS=#{guess},GUESSID=#{guessid},GUESSTIME=#{guesstime} WHERE ID=#{id}")
    int updateOrderGuessAndState(Orderinfo orderinfo);

    /** 查询状态3，并且开奖时间的订单 */
    @Select("SELECT * FROM orderinfo WHERE STATE=3 AND GUESSTIME<=NOW()")
    List<Orderinfo> findNotGuess();

    /** 根据用户oid查询用户的所有订单 */
    @Select("SELECT odr.ID,odr.G_ID,odr.CONTS,odr.PRICE,odr.STATE,odr.REGISTERTIME,gs.NAME AS gname,gs.PRICE AS gprice,gs.IMG FROM orderinfo AS odr LEFT JOIN member AS mem ON odr.U_ID = mem.ID LEFT JOIN guessing AS gue ON odr.GUESSID = gue.ID LEFT JOIN goods AS gs ON odr.G_ID = gs.ID WHERE mem.OID = #{oid} AND gs.TYPE=#{type} ORDER BY odr.REGISTERTIME DESC")
    List<OrderinfoModel> findOrderListByOid(@Param("oid") String oid ,@Param("type") Integer type);

    /** 查询所有的订单，按照时间降序 */
    @Select("SELECT oi.*,gd.NAME AS goods_name,mem.phone as uphone FROM orderinfo AS oi LEFT JOIN goods AS gd ON oi.G_ID = gd.ID LEFT JOIN member AS mem ON oi.U_ID = mem.ID where not in (2,6,8) ORDER BY oi.REGISTERTIME DESC")
    List<SysOrderListModel> findAll();

    /** 查询所有提货的订单，按照时间降序 */
    @Select("SELECT oi.*,gd.NAME AS goods_name,mem.phone as uphone FROM orderinfo AS oi LEFT JOIN goods AS gd ON oi.G_ID = gd.ID LEFT JOIN member AS mem ON oi.U_ID = mem.ID where in (2,6,8) ORDER BY oi.REGISTERTIME DESC")
    List<SysOrderListModel> findTiHuoAll();

    /** 根据id查询订单记录 */
    @Select("SELECT * FROM orderinfo WHERE ID = #{id}")
    Orderinfo findOrderByID(Integer id);

    /** 根据时间查询订单数 */
    @Select({
            "<script>",
            "SELECT COUNT(ID) FROM orderinfo WHERE 1=1",
            "<if test='start != null and start !=&quot;&quot;'>AND REGISTERTIME &gt;= #{start}</if>",
            "<if test='end != null and end !=&quot;&quot;'>AND REGISTERTIME &lt;= #{end}</if>",
            "</script>"
    })
    Integer findOrderCount(@Param("start") String start ,@Param("end") String end);
}
