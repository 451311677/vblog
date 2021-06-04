package com.xaut.blog.controller;

import com.xaut.blog.entity.CommonResult;
import com.xaut.blog.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author zxg
 * @date 2021/4/14
 * @description 跳转服务
 */
@Controller
@Slf4j
public class SkipController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BLOG_SERVER_URL = "http://localhost:8080";

    @GetMapping("/login")
    public String toLogin(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

//        CommonResult<User> result = restTemplate.exchange(BLOG_SERVER_URL + "/login/islogin", HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<User>>() {
//        }).getBody();
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser != null) {
            log.info(String.valueOf(loginUser));
            model.addAttribute("loginUser", loginUser);
            return "/view/index";
        }
        return "/view/admin/login";

    }

    @GetMapping("/register")
    public String toRegister(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser != null) {
            log.info(String.valueOf(loginUser));
            model.addAttribute("loginUser", loginUser);
            return "/view/index";
        }
        return "/view/admin/register";
    }

    @GetMapping("/forget")
    public String toForget(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        if(loginUser!=null){
            model.addAttribute("loginUser",loginUser);
            return "/view/index";
        }
        return "view/admin/forget";
    }

    @GetMapping("/publish")
    public String publish(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if(loginUser!=null){
            model.addAttribute("loginUser",loginUser);
            return  "view/admin/publish";
        }
        return "view/admin/login";
    }



    @GetMapping("/lizi")
    public String test(){
        return "view/index2";
    }


}
