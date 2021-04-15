package com.xaut.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author xiaogang.zhang
 * @date 2021/3/1
 * @description 用户类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long userId;
    /**
     * 昵称
     */
    private String userName;
    /**
     * 账号
     */
    private String admin;
    private String password;
    private String email;
    private String gender;
    /**
     * 积分
     */
    private Long integral;
    /**
     * 标签：1,2,3,4
     */
    private String label;
    /**
     * 身份： 1 用户  ，  2 管理
     */
    private Integer status;
    private Date createTime;
    private Date lastEditTime;
    private List<String> interestList;

    /**
     * 收藏量
     */
    private Integer collectionNumber;
    /**
     * 博客文章数量
     */
    private Integer blogNumber;

    public User(String userName,String admin,String password,String email){
        this.userName = userName;
        this.admin=admin;
        this.password=password;
        this.email=email;
        this.gender = "男";
    }
}
