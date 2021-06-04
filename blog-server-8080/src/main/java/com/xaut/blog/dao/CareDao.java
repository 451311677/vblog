package com.xaut.blog.dao;

import com.xaut.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zxg
 * @date 2021/4/28
 * @description 关注
 */
@Mapper
public interface CareDao {

    /**
     * 获取粉丝数量
     * @param userId
     * @return
     */
    int fans(Long userId);

    /**
     * 获取关注数量
     * @param fanId
     * @return
     */
    int cares(Long fanId);

    /**
     * 关注
     * @param userId
     * @param fanId
     * @return
     */
    int insert(Long userId,Long fanId);

    /**
     * 取关
     * @return
     */
    int delete(Long userId,Long fanId);

    /**
     * 查询
     * @param userId
     * @param fanId
     * @return
     */
    long get(Long userId,Long fanId);


    /**
     * 获取关注列表
     * @param userId
     * @return
     */
    public List<User> getCareList(Long userId);

}
