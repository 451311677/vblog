package com.xaut.blog.controller;

import com.xaut.blog.dao.LikeDao;
import com.xaut.blog.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxg
 * @date 2021/4/14
 * @description 点赞
 */
@RestController
@RequestMapping("like")
@Slf4j
public class LikeController {

    private LikeService likeService;

}
