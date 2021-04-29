package com.xaut.blog.dao;

import com.xaut.blog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zxg
 * @date 2021/4/11
 * @description
 */
@Mapper
public interface CommentDao {

    /**
     * 新增评论
     * @param comment
     * @return
     */
    int insert(Comment comment);

    /**
     * 删除评论
     * @param commentId
     * @return
     */
    int delete(Long commentId);

    /**
     * 根据文章id查询评论列表
     * @param ownerArticleId
     * @return
     */
    List<Comment> getCommentList(Long ownerArticleId);

    /**
     * 查询文章评论数
     * @param ownerArticleId
     * @return
     */
    int count(Long ownerArticleId);
}
