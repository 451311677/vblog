package com.xaut.blog.dao;

import com.xaut.blog.entity.Like;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zxg
 * @date 2021/4/14
 * @description
 */
@Mapper
public interface LikeDao {

    /**
     * 点赞
     * @param like
     * @return
     */
    int insert(Like like);

    /**
     * 取消点赞
     * @param ownerUserId
     * @param articleId
     * @return
     */
    int delete(Long ownerUserId,Long articleId);

    /**
     * 查询文章的点赞列表
     * @param ownerArticleId
     * @return
     */
    List<Like> getLikeListByArticle(Long ownerArticleId);

    /**
     * 查询文章是否自己已点赞
     * @param ownerArticleId
     * @param ownerUserId
     * @return
     */
    Like getLikeByUserId(Long ownerArticleId,Long ownerUserId);

    /**
     * 查询文章点赞数
     * @param ownerArticleId
     * @return
     */
    int count(Long ownerArticleId);

}
