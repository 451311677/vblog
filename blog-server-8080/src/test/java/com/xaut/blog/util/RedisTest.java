package com.xaut.blog.util;

import com.xaut.blog.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zxg
 * @date 2021/4/12
 * @description
 */
public class RedisTest extends BaseTest {
    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void set(){
        redisUtils.set("zxg","123");
    }

    @Test
    public void get(){
        String zxg = redisUtils.get("zxg");
        System.out.println(zxg);
    }

    @Test
    public void getAndSet(){
        redisUtils.getAndSet("zxg","234");
    }

    @Test
    public void delete(){
        redisUtils.delete("zxg");
    }
}
