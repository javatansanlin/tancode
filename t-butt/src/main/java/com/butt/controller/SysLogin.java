package com.butt.controller;

import com.butt.dao.SysmemberDao;
import com.butt.entity.Sysmenber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 后台登陆
 * @Date: Created in 17:49 2018/9/18
 * @Modified By:
 */
@Controller
@RequestMapping("/sys")
public class SysLogin {

    @Autowired
    SysmemberDao sysmemberDao;

    @PostMapping("/login")
    @ResponseBody
    public Map<String ,Object> login(HttpSession session ,String name , String pwd){
        Map<String ,Object> result = new HashMap<>();
        if (name==null || "".equals(name.trim()) || pwd==null || "".equals(pwd.trim())){
            result.put("code" ,1);
            result.put("msg" ,"请输入账户名和密码");
            return result;
        }
        Sysmenber mem = sysmemberDao.findSysMemByNameAndPwd(name, pwd);
        if (mem==null){
            result.put("code" ,2);
            result.put("msg" ,"账户名或密码错误");
            return result;
        }
        //放到session
        session.setAttribute("user" ,mem);
        result.put("code" ,3);
        result.put("msg" ,"登陆成功");
        return result;
    }

}
