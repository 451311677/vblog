package com.xaut.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author zxg
 * @date 2021/4/6
 * @description 博客文章类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    private Long articleId;
    private String articleName;
    private Long ownerUserId;
    private Integer pageView;
    private String label;
    private String text;

    /**
     * 1.发布，2.草稿
     */
    private Integer state;
    private Date createTime;
    private Date lastEditTime;

    /**
     * 标签列表
     */
    private List<String> labelList;
    /**
     * 被收藏数
     */
    private Integer collectionNumber;

    /**
     * 点赞数
     */
    private Integer likeNumber;

    /**
     * 评论数
     */
    private Integer commentNumber;

    /**
     * 作者
     */
    private User author;

    /**
     * 简单描述
     */
    private String desc;




}
