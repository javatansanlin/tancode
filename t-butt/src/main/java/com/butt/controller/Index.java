package com.butt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: JavaTansanlin
 * @Description: 主页跳转
 * @Date: Created in 11:04 2018/9/18
 * @Modified By:
 */
@Controller
public class Index {

    @RequestMapping("/")
    public String index() {
        return "forward:/Index.html";
    }

}
