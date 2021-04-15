package com.xaut.blog.service;

import com.xaut.blog.dao.ArticleDao;
import com.xaut.blog.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zxg
 * @date 2021/4/8
 * @description
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    /**
     * 发布文章
     * @param article
     * @return
     */
    public int insert(Article article){
        return articleDao.insert(article);
    }

    /**
     * 更新文章
     * @param article
     * @return
     */
    public int update(Article article){
        return articleDao.update(article);
    }

    /**
     * 删除文章
     * @param articleId
     * @return
     */
    public int delete(Long articleId){
        return articleDao.delete(articleId);
    }

    /**
     * 通过Id查询文章
     * @param articleId
     * @return
     */
    public Article getById(Long articleId){
        return articleDao.getById(articleId);
    }

    /**
     * 通过名字查询文章
     * @param articleName
     * @return
     */
    public List<Article> getByName(String articleName){
        return articleDao.getByName(articleName);
    }

    /**
     * 通过标签查询文章
     * @param articleIdList
     * @return
     */
    public List<Article> getByLabel(List<String> articleIdList){
        return articleDao.getByLabel(articleIdList);
    }
}
