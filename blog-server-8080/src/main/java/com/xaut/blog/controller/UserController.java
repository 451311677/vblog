package com.xaut.blog.controller;

import com.xaut.blog.entity.CommonResult;
import com.xaut.blog.entity.User;
import com.xaut.blog.service.ArticleService;
import com.xaut.blog.service.CareService;
import com.xaut.blog.service.CollectionService;
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

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private CareService careService;


    @GetMapping("/get/{admin}")
    public CommonResult<?> getUser(@PathVariable("admin") String admin) {
        User user = userService.getUserByAdmin(admin);
        if (user != null) {
            //查询用户文章数量
            user.setBlogNumber(Math.max(articleService.userCount(user.getUserId()),0));

            //查询收藏数量
            user.setCollectionNumber(Math.max(collectionService.getCountByUser(user.getUserId()),0));

            //查询访问量
            user.setView(Math.max(articleService.pageViewCount(user.getUserId()),0));

            //查询粉丝数量
            user.setFans(Math.max(careService.fans(user.getUserId()),0));

            //查询关注数量
            user.setCare(Math.max(careService.cares(user.getUserId()),0));

            return new CommonResult<User>(200, "查询成功", user);
        }
        return new CommonResult<>(400, "查询失败");
    }

    @GetMapping("/getbyid/{id}")
    public CommonResult<?> getUserById(@PathVariable("id") Long userId){
        User user = userService.getUserById(userId);
        if (user != null) {
            //查询用户文章数量
            user.setBlogNumber(Math.max(articleService.userCount(userId),0));

            //查询收藏数量
            user.setCollectionNumber(Math.max(collectionService.getCountByUser(userId),0));

            //查询访问量
            user.setView(Math.max(articleService.pageViewCount(userId),0));

            //查询粉丝数量
            user.setFans(Math.max(careService.fans(userId),0));

            //查询关注数量
            user.setCare(Math.max(careService.cares(userId),0));

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
