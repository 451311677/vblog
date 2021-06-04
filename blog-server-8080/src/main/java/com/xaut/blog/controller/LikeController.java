package com.xaut.blog.controller;

import com.xaut.blog.dao.LikeDao;
import com.xaut.blog.entity.CommonResult;
import com.xaut.blog.entity.Like;
import com.xaut.blog.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zxg
 * @date 2021/4/14
 * @description 点赞
 */
@RestController
@RequestMapping("/like")
@Slf4j
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/like")
    public CommonResult<?> like(@RequestBody Like like) {
        try {
            int count = likeService.insert(like);
            if (count > 0) {
                return new CommonResult<>(200, "点赞成功");
            }
            return new CommonResult<>(400, "点赞失败");
        } catch (Exception e) {
            return new CommonResult<>(400, "点赞失败");
        }
    }


    @GetMapping("/unlike")
    public CommonResult<?> unlike(HttpServletRequest request) {
        Long ownerUserId = Long.valueOf(request.getParameter("ownerUserId"));
        Long articleId = Long.valueOf(request.getParameter("ownerArticleId"));
        try {
            int count = likeService.delete(ownerUserId, articleId);

            if (count > 0) {
                return new CommonResult<>(200, "取消点赞成功");
            }
            return new CommonResult<>(400, "取消点赞失败");
        } catch (Exception e) {
            return new CommonResult<>(400, "取消点赞失败");
        }
    }

    @GetMapping("/islike")
    public CommonResult<?> islike(HttpServletRequest request){
        Long ownerUserId = Long.valueOf(request.getParameter("ownerUserId"));
        Long articleId = Long.valueOf(request.getParameter("articleId"));
        Like like = likeService.getLikeByUserId(articleId, ownerUserId);
        if(like!=null){
            return  new CommonResult<Like>(200,"查询成功",like);
        }
        return new CommonResult<>(400,"查询失败");
    }


}
