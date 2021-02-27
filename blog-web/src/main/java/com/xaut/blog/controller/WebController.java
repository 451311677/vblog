package com.xaut.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xiaogang.zhang
 * @date 2021/2/26
 * @description
 */
@Controller
public class WebController {

    @GetMapping("/")
    public String index(){
        return "view/index";
    }
    @GetMapping("/index")
    public String index2(){
        return "view/index";
    }

    @GetMapping("/md")
    public String md(){
        return "view/test/mymd";
    }
//    @GetMapping("/mymd")
//    public String index2(){
//        return "view/index";
//    }
}
