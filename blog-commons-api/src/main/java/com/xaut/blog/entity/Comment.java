package com.xaut.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zxg
 * @date 2021/4/6
 * @description 评论类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Long commentId;
    private Long ownerUserId;
    private Long ownerArticleId;
    private String text;

}
