package com.xaut.blog.dao;

import com.xaut.blog.entity.Article;
import org.apache.ibatis.annotations.Mapper;

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
    List<Article> getByName(String articleName);

    /**
     * 通过标签查询文章
     * @param articleIdList
     * @return
     */
    List<Article> getByLabel(List<String> articleIdList);
}

