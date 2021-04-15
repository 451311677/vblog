package com.xaut.blog.util;

import com.alibaba.fastjson.JSON;
import com.xaut.blog.entity.User;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * @author zxg
 * @date 2021/4/15
 * @description http工具类
 */
@Component
public class HttpUtils{

    public RequestEntity<String> getRequestEntity(String url, Object data){
        URI uri = UriComponentsBuilder.fromUriString(url).build().toUri();
        String s = JSON.toJSONString(data);

        return RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).header("Content-Type", "application/json").body(s);
    }
}
