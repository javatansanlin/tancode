package com.butt.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import com.butt.entity.Goods;

import java.util.List;

/**
 * @Author: JavaTansanlin
 * @Description:
 * @Date: Created in 2:24 2018/9/14
 * @Modified By:
 */
@Component
public interface GoodsDao {

    /** 插入一条记录 */
    @Insert("insert into GOODS(TYPE,NAME,PRICE,INTEGRAL,IMG,IMGT,IMGTT,REMARKE) values (#{type},#{name},#{price},#{integral},#{img},#{imgt},#{imgtt},#{remarke})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertOne(Goods goods);

    /** 根据类型查询商品 */
    @Select("SELECT * FROM goods WHERE TYPE = #{type}")
    List<Goods> findGoodsByType(Integer type);

    /** 根据id查询商品 **/
    @Select("SELECT * FROM goods WHERE ID = #{id}")
    Goods findGoodsById(Integer id);
}
