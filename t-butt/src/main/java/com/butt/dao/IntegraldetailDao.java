package com.butt.dao;

import com.butt.entity.Integraldetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Component;

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
}
