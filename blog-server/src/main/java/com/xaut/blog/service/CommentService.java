package com.xaut.blog.service;

import com.xaut.blog.dao.CommentDao;
import com.xaut.blog.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zxg
 * @date 2021/4/11
 * @description
 */
@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    /**
     * 新增评论
     * @param comment
     * @return
     */
    public int insert(Comment comment){
        return commentDao.insert(comment);
    }

    /**
     * 删除评论
     * @param commentId
     * @return
     */
    public int delete(Long commentId){
        return commentDao.delete(commentId);
    }

    /**
     * 根据文章id查询评论列表
     * @param ownerArticleId
     * @return
     */
    public List<Comment> getCommentList(Long ownerArticleId){
        return commentDao.getCommentList(ownerArticleId);
    }
}
