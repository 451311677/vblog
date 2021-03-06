package com.xaut.blog.dao;

import com.xaut.blog.entity.Article;
import com.xaut.blog.entity.Collection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zxg
 * @date 2021/4/12
 * @description
 */
@Mapper
public interface CollectionDao {

    /**
     * 增加收藏
     * @param collection
     * @return
     */
    int insert(Collection collection);

    /**
     * 删除收藏
     * @param collectionId
     * @return
     */
    int delete(Long collectionId);

    int deleteById(Long ownerUserId,Long ownerArticleId);

    /**
     * 根据用户id获取收藏列表
     * @param ownerUserId
     * @return
     */
    List<Article> getCollectionList(Long ownerUserId);

    /**
     * 根据用户id获取收藏数量
     * @param ownerUserId
     * @return
     */
    int getCountByUser(Long ownerUserId);

    /**
     * 文章被收藏数量
     * @param ownerArticleId
     * @return
     */
    int count(Long ownerArticleId);


    /**
     * 文章是否被收藏
     * @param ownerUserId
     * @param ownerArticleId
     * @return
     */
    Collection isCollection(Long ownerUserId,Long ownerArticleId);
}
