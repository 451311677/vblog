package com.xaut.blog.controller;

import com.xaut.blog.entity.Comment;
import com.xaut.blog.entity.CommonResult;
import com.xaut.blog.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zxg
 * @date 2021/4/11
 * @description
 */
@RestController
@Slf4j
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/insert")
    public CommonResult<?> insert(Comment comment) {
        log.info("新增评论："+String.valueOf(comment));
        int count = commentService.insert(comment);
        if (count > 0) {
            return new CommonResult<>(200, "新增评论成功");
        }
        return new CommonResult<>(400, "新增评论失败");
    }

    @GetMapping("/delete/{id}")
    public CommonResult<?> delete(@PathVariable("id") Long commentId) {
        log.info("删除评论："+commentId);
        int count = commentService.delete(commentId);
        if(count>0){
            return new CommonResult<>(200,"删除成功");
        }
        return new CommonResult<>(400,"删除失败");
    }

    @GetMapping("/getcommentlist/{id}")
    public CommonResult<?> getCommentList(@PathVariable("id") Long ownerArticleId){
        List<Comment> list = commentService.getCommentList(ownerArticleId);
        if(list!=null){
            return new CommonResult<List<Comment>>(200,"查询评论成功",list);
        }
        return new CommonResult<>(400,"查询评论失败");
    }
}
