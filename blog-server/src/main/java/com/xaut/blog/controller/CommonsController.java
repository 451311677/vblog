package com.xaut.blog.controller;

import com.xaut.blog.util.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zxg
 * @date 2021/4/15
 * @description 通用
 */
@RestController
public class CommonsController {

    @Autowired
    private IpUtils ipUtils;

    @GetMapping("/getip")
    private String getip(HttpServletRequest request){
        return ipUtils.getIpAddr(request);
    }


}
