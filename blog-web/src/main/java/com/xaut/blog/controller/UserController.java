package com.xaut.blog.controller;

import com.xaut.blog.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author zxg
 * @date 2021/4/19
 * @description
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 跳转
     * @param request
     * @param model
     * @return
     */
    @GetMapping("setting")
    public String setting(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if(loginUser!=null){
            model.addAttribute("loginUser",loginUser);
            return "view/admin/setting";
        }
        return "view/admin/login";
    }

    /**
     * 跳转文章管理界面
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/article")
    public String articleManagement(HttpServletRequest request,Model model){


        return null;
    }
}
