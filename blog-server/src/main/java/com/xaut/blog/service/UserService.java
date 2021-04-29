package com.xaut.blog.service;

import com.xaut.blog.dao.UserDao;
import com.xaut.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zxg
 * @date 2021/4/6
 * @description
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 添加用户
     * @param user
     * @return
     */
    public int insertUser(User user) {
        //数据初始化
        user.setUserName(user.getAdmin());  //初始name 为 admin
        user.setGender("男");
        user.setStatus(1);  //普通用户
        user.setCreateTime(new Date());
        user.setLastEditTime(new Date());
        user.setIntegral(0L);  //初始积分为0

        return userDao.insertUser(user);
    }

    /**
     * 查询用户
     * @param user
     * @return
     */
    public int updateUser(User user) {
        user.setLastEditTime(new Date());
        return userDao.updateUser(user);
    }

    /**
     * 查询用户
     * @param admin
     * @return
     */
    public User getUserByAdmin(String admin) {
        return userDao.getUserByAdmin(admin);
    }

    /**
     * 根据昵称查询用户
     * @param userName
     * @return
     */
    public User getUserByName(String userName){
        return userDao.getUserByName(userName);
    }


    /**
     * 根据邮箱查询用户
     * @param email
     * @return
     */
    public User getUserByEmail(String email){
        return userDao.getUserByEmail(email);
    }


    /**
     * 根据id查询用户
     * @param userId
     * @return
     */
    public User getUserById(Long userId){
        return userDao.getUserById(userId);
    }
}
