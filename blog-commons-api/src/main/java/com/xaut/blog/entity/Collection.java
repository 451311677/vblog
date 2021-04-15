package com.xaut.blog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zxg
 * @date 2021/4/6
 * @description 收藏类
 */
@Data
@NoArgsConstructor
public class Collection {
    private Long collectionId;
    private Long ownerUserId;
    private Long ownerArticleId;
}
