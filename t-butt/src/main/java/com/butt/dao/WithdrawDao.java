package com.butt.dao;

import com.butt.entity.Withdraw;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Component;

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
}
