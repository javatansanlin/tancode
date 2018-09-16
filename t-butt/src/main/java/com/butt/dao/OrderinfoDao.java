package com.butt.dao;

import com.butt.entity.Orderinfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

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

}
