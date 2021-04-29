package com.xaut.blog.dao;

import com.xaut.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author zxg
 * @date 2021/4/6
 * @description
 */
@Mapper
public interface UserDao {

    /**
     * 注册用户
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 根据账号查询用户
     * @param admin
     * @return
     */
    User getUserByAdmin(String admin);

    /**
     * 根据昵称查询用户
     * @param userName
     * @return
     */
    User getUserByName(String userName);

    /**
     * 根据邮箱查询用户
     * @param email
     * @return
     */
    User getUserByEmail(String email);

    /**
     * 通过id查询用户
     * @param userId
     * @return
     */
    User getUserById(Long userId);
}
