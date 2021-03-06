package com.xaut.blog.dao;

import com.xaut.blog.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zxg
 * @date 2021/4/8
 * @description
 */
@Mapper
public interface ArticleDao {

    /**
     * 发布文章
     * @param article
     * @return
     */
    int insert(Article article);

    /**
     * 更新文章
     * @param article
     * @return
     */
    int update(Article article);

    /**
     * 删除文章
     * @param articleId
     * @return
     */
    int delete(Long articleId);

    /**
     * 通过Id查询文章
     * @param articleId
     * @return
     */
    Article getById(Long articleId);

    /**
     * 通过名字查询文章
     * @param articleName
     * @return
     */
    List<Article> getByName(@Param("articleName") String articleName);

    /**
     * 通过标签查询文章
     * @param articleIdList
     * @return
     */
    List<Article> getByLabel(List<String> articleIdList);


    /**
     * 通过用户id查询文章
     * @param ownerUserId
     * @return
     */
    List<Article> getByOwnerUserId(Long ownerUserId);

    /**
     * 通过用户admin查询文章
     * @param admin
     * @return
     */
    List<Article> getByUserAdmin(String admin);

    /**
     * 查询用户文章数量
     * @param userId
     * @return
     */
    int userCount(Long userId);

    /**
     * 查询用户文章访问量
     * @param userId
     * @return
     */
    int pageViewCount(Long userId);
}

