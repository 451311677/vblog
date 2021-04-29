package com.xaut.blog.service;

import com.xaut.blog.dao.CollectionDao;
import com.xaut.blog.entity.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zxg
 * @date 2021/4/12
 * @description
 */
@Service
public class CollectionService {

    @Autowired
    private CollectionDao collectionDao;

    /**
     * 增加收藏
     * @param collection
     * @return
     */
    public int insert(Collection collection){
        return collectionDao.insert(collection);
    }

    /**
     * 删除收藏
     * @param collectionId
     * @return
     */
    public int delete(Long collectionId){
        return collectionDao.delete(collectionId);
    }

    /**
     * 根据用户id获取收藏列表
     * @param ownerUserId
     * @return
     */
    public List<Collection> getCollectionList(Long ownerUserId){
        return collectionDao.getCollectionList(ownerUserId);
    }

    /**
     * 根据用户id获取收藏数量
     * @param ownerUserId
     * @return
     */
    public int getCountByUser(Long ownerUserId){
        return collectionDao.getCountByUser(ownerUserId);
    }

    /**
     * 获取文章收藏数
     * @param ownerArticleId
     * @return
     */
    public int count(Long ownerArticleId){
        return collectionDao.count(ownerArticleId);
    }

}
