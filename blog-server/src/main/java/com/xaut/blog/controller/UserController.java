package com.xaut.blog.controller;

import com.xaut.blog.entity.CommonResult;
import com.xaut.blog.entity.User;
import com.xaut.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author zxg
 * @date 2021/4/6
 * @description
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/get/{admin}")
    public CommonResult<?> getUser(@PathVariable("admin") String admin) {
        User user = userService.getUserByAdmin(admin);
        if (user != null) {
            return new CommonResult<User>(200, "查询成功", user);
        }
        return new CommonResult<>(400, "查询失败");
    }

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    @PostMapping("/register")
    public CommonResult<?> insertUser(HttpServletRequest request, @RequestBody User user) {
        HttpSession session = request.getSession();

        user.setUserName(user.getAdmin());  //初始name 为 admin
        user.setGender("男");
        user.setStatus(1);  //普通用户
        user.setCreateTime(new Date());
        user.setLastEditTime(new Date());
        user.setIntegral(0L);  //初始积分为0

        try {
            int count = userService.insertUser(user);
            if (count > 0) {
                session.setAttribute("loginUser", user);
                return new CommonResult<User>(200, "注册成功", user);
            }
            return new CommonResult<>(400, "注册失败");
        } catch (Exception e) {
            return new CommonResult<>(400, "注册失败");
        }
    }

    @PostMapping("/update")
    public CommonResult<?> updateUser(@RequestBody User user) {
        user.setLastEditTime(new Date());
        try {
            int count = userService.updateUser(user);
            if (count > 0) {
                return new CommonResult<>(200, "更新成功");
            }
            return new CommonResult<>(400, "更新失败");
        } catch (Exception e) {
            return new CommonResult<>(400, "更新失败");
        }

    }
}
