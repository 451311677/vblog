package com.xaut.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zxg
 * @date 2021/4/14
 * @description 点赞类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    private Long likeId;
    private Long ownerUserId;
    private Long ownerArticleId;
    /**
     * 类别，1 文章点赞，2 动态点赞
     */
//    private Integer category;
}
