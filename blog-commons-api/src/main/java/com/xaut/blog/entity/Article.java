package com.xaut.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private Integer state;
    private Date cteateTime;
    private Date lastEditTime;

}
