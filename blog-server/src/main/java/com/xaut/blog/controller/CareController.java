package com.xaut.blog.controller;

import com.xaut.blog.service.CareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
