package com.xaut.blog.service;

import com.xaut.blog.dao.UserDao;
import com.xaut.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return userDao.insertUser(user);
    }

    /**
     * 查询用户
     * @param user
     * @return
     */
    public int updateUser(User user) {
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
}
