package com.butt.dao;

import com.butt.entity.Guessing;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

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

}
