package com.xaut.blog.service;

import com.xaut.blog.dao.CareDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zxg
 * @date 2021/4/28
 * @description
 */
@Service
public class CareService {

    @Autowired
    private CareDao careDao;

    /**
     * 获取粉丝数量
     * @param userId
     * @return
     */
    public int fans(Long userId){
        return careDao.fans(userId);
    }

    /**
     * 获取关注数量
     * @param fanId
     * @return
     */
    public int cares(Long fanId){
        return careDao.cares(fanId);
    }

    /**
     * 关注
     * @param userId
     * @param fanId
     * @return
     */
    public int insert(Long userId,Long fanId){
        return careDao.insert(userId,fanId);
    }

    /**
     * 取关
     * @return
     */
    public int delete(Long userId,Long fanId){
        return careDao.delete(userId, fanId);
    }
    /**
     * 查询
     * @param userId
     * @param fanId
     * @return
     */
    public int get(Long userId,Long fanId){
        return careDao.get(userId, fanId);
    }
}
