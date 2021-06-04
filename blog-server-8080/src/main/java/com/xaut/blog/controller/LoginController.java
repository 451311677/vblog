package com.xaut.blog.controller;

import com.xaut.blog.entity.CommonResult;
import com.xaut.blog.entity.User;
import com.xaut.blog.service.UserService;
import com.xaut.blog.util.EmailUtils;
import com.xaut.blog.util.RandomUtils;
import com.xaut.blog.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private RandomUtils randomUtils;

    @Autowired
    private RedisUtils redisUtils;


    /**
     * 登录
     *
     * @param request
     * @param loginUser
     * @return
     */
    @PostMapping("/login")
    public CommonResult<?> login(HttpServletRequest request, @RequestBody User loginUser) {
        HttpSession session = request.getSession();

        User user = userService.getUserByAdmin(loginUser.getAdmin());
        if (user != null) {
            if (user.getPassword().equals(loginUser.getPassword())) {
                log.info("[" + loginUser.getAdmin() + "] : 登录成功");
                //保存登录状态
                session.setAttribute("loginUser", user);
                return new CommonResult<User>(200, "登录成功", user);
            } else {
                log.info("[" + loginUser.getAdmin() + "] : 登录失败：密码错误");
                return new CommonResult<User>(400, "密码错误", loginUser);
            }

        }
        log.info("[" + loginUser.getAdmin() + "] : 登录失败：用户名不存在");

        return new CommonResult<User>(400, "用户名不存在", loginUser);
    }

    /**
     * 是否登录
     *
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
        return new CommonResult<User>(400, "未登录");
    }

    /**
     * 退出
     *
     * @param request
     * @return
     */
    @GetMapping("/quit")
    public CommonResult<?> quit(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("loginUser");
        return new CommonResult<>(200, "退出成功");
    }

//    @PostMapping("/register")
//    public CommonResult<?> register(HttpServletRequest request,@RequestBody User user){
//        System.out.println(user);
//        return new CommonResult<>(200,"注册成功");
//    }

    /**
     * 生成验证码并发送邮箱
     *
     * @param request
     * @return
     */
    @GetMapping("/sendemail")
    public Map<String, Object> sendEmail(HttpServletRequest request) {
        HashMap<String, Object> modelMap = new HashMap<>();

        String email = request.getParameter("email");

        String captcha = randomUtils.creatCaptcha();
        String emailContent = "VBlog 忘记密码，验证码为：<b>" + captcha + "</b>，5分钟内有效。";
        try {
            emailUtils.sendEmail(email, "验证码", emailContent);
            // 验证码加入缓存，时限5分钟
            redisUtils.setEx(email, captcha, 5);
        } catch (GeneralSecurityException | MessagingException e) {
            e.printStackTrace();
            modelMap.put("success", false);
        } catch (Exception e) {
            modelMap.put("success", false);
        }
        modelMap.put("success", true);
        return modelMap;
    }

    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    @GetMapping("updatepassword")
    public CommonResult<?> updateUserPassword(HttpServletRequest request) {
        log.info("update------>password");
        // 获取验证码
        String email = request.getParameter("email");
        String captcha = request.getParameter("captcha");
        String password = request.getParameter("password");

        // 1. 对比验证码
        String captcha2 = redisUtils.get(email);
        if (!captcha.equals(captcha2)) {
            return new CommonResult<>(400, "验证码不正确");
        }

        log.info("验证码正确");

        // 2. 根据邮箱修改密码
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return new CommonResult<>(400, "邮箱未注册");
        }
        log.info("邮箱正确");
        //设置新密码
        user.setPassword(password);
        try {
            userService.updateUser(user);
        } catch (Exception e) {
            return new CommonResult<>(400, "密码重置失败");
        }
        log.info("密码修改成功");
        return new CommonResult<>(200, "密码重置成功");
    }
}


