package com.xaut.blog.controller;

import com.xaut.blog.entity.CommonResult;
import com.xaut.blog.entity.User;
import com.xaut.blog.service.CareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

/**
 * @author zxg
 * @date 2021/4/28
 * @description
 */
@Controller
@RequestMapping("/care")
public class CareController {

    @Autowired
    private CareService careService;

    @GetMapping("/user/{id}")
    public CommonResult<?> getCareList(@PathVariable("id") Long userId){
        List<User> careList = careService.getCareList(userId);
        if(careList!=null){
            return new CommonResult<>(200, "查询成功", careList);
        }
        return new CommonResult<>(400,"查询失败");
    }
}
