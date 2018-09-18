package com.butt.dao;

import com.butt.entity.Sysmenber;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @Author: JavaTansanlin
 * @Description: 系统后台用户dao
 * @Date: Created in 19:12 2018/9/18
 * @Modified By:
 */
@Component
public interface SysmemberDao {

    /** 根据用户名和密码查询记录 */
    @Select("SELECT * FROM sysmenber WHERE NAME=#{name} AND PASSWORD=#{pwd}")
    Sysmenber findSysMemByNameAndPwd(@Param("name") String name ,@Param("pwd") String pwd);
}
