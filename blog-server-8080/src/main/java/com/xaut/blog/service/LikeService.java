package com.xaut.blog.service;

import com.xaut.blog.dao.LikeDao;
import com.xaut.blog.entity.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zxg
 * @date 2021/4/14
 * @description
 */
@Service
public class LikeService {

    @Autowired
    private LikeDao likeDao;

    /**
     * 点赞
     * @param like
     * @return
     */
    public int insert(Like like){
        return likeDao.insert(like);
    }

    /**
     * 取消点赞
     * @param ownerUserId
     * @param articleId
     * @return
     */
    public int delete(Long ownerUserId,Long articleId){
        return likeDao.delete(ownerUserId, articleId);
    }

    /**
     * 查询文章的点赞列表
     * @param ownerArticleId
     * @return
     */
    public List<Like> getLikeListByArticle(Long ownerArticleId){
        return likeDao.getLikeListByArticle(ownerArticleId);
    }

    /**
     * 查询文章是否自己已点赞
     * @param ownerArticleId
     * @param ownerUserId
     * @return
     */
    public Like getLikeByUserId(Long ownerArticleId,Long ownerUserId){
        return likeDao.getLikeByUserId(ownerArticleId,ownerUserId);
    }

    public int count(Long ownerArticleId){
        return likeDao.count(ownerArticleId);
    }




}
