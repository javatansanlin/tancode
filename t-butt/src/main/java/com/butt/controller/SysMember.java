package com.butt.controller;

import com.butt.entity.Member;
import com.butt.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 后台-客户管理
 * @Date: Created in 21:10 2018/9/18
 * @Modified By:
 */
@RestController
@RequestMapping("/sys/u")
public class SysMember {

    @Autowired
    UserService userService;

    /** 查询所有的用户资料，有用户名条件 ，按注册时间排序*/
    @PostMapping("/findAll")
    PageInfo<Member> findAll(String name ,Integer pageNum){
        if (pageNum==null || pageNum<=0){
            pageNum = 1;
        }
        return userService.findAll(name ,pageNum);
    }

    /** 查看今日注册数和总用户数 */
    @PostMapping("/findCount")
    Map<String ,Object> findCount(){
        return userService.findCount();
    }

}
