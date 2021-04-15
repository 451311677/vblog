package com.xaut.blog.controller;

import com.xaut.blog.entity.CommonResult;
import com.xaut.blog.entity.User;
import com.xaut.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;

/**
 * @author xiaogang.zhang
 * @date 2021/3/2
 * @description
 */
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;


    /**
     * 登录
     * @param request
     * @param loginUser
     * @return
     */
    @PostMapping("/login")
    public CommonResult<?> login(HttpServletRequest request, @RequestBody User loginUser) {
        HttpSession session = request.getSession();
        System.out.println("--->" + loginUser);

        User user = userService.getUserByAdmin(loginUser.getAdmin());
        if (user != null) {
            if (user.getPassword().equals(loginUser.getPassword())) {
                log.info("[" + loginUser.getAdmin() + "] : 登录成功");
                //保存登录状态
                session.setAttribute("loginUser", user);
                return new CommonResult<User>(200, "登录成功", user);
            }else{
                log.info("[" + loginUser.getAdmin() + "] : 登录失败：密码错误");
                return new CommonResult<User>(400, "密码错误", loginUser);
            }

        }
        log.info("[" + loginUser.getAdmin() + "] : 登录失败：用户名不存在");

        return new CommonResult<User>(400, "用户名不存在", loginUser);
    }

    /**
     * 是否登录
     * @param request
     * @return
     */
    @GetMapping("/islogin")
    public CommonResult<?> isLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser != null) {
            return new CommonResult<User>(200, "已登录", loginUser);
        }
        return new CommonResult<User>(400, "未登录", null);
    }

    /**
     * 退出
     * @param request
     * @return
     */
    @GetMapping("/quit")
    public CommonResult<?> quit(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("loginUser");
        return new CommonResult<>(200,"退出成功");
    }

//    @PostMapping("/register")
//    public CommonResult<?> register(HttpServletRequest request,@RequestBody User user){
//        System.out.println(user);
//        return new CommonResult<>(200,"注册成功");
//    }
}
