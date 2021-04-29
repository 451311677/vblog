package com.xaut.blog.util;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author zxg
 * @date 2021/4/16
 * @description 生成随机数工具类
 */
@Component
public class RandomUtils {

    public String creatCaptcha(){
        Random random = new Random();
        int captcha = random.nextInt(900000)+100000;
        return String.valueOf(captcha);
    }
}
