package com.xaut.blog.controller;

import com.xaut.blog.entity.Article;
import com.xaut.blog.entity.CommonResult;
import com.xaut.blog.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author zxg
 * @date 2021/4/24
 * @description 博客文章
 */
@Controller
@Slf4j
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private RestTemplate restTemplate;

    private final String BLOG_SERVER_URL = "http://localhost:8080";

    @GetMapping("/detail/{id}")
    public String look(HttpServletRequest request, Model model, @PathVariable("id") Long id){

        //1. 获取当前用户登录信息
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if(loginUser!=null){
            model.addAttribute("loginUser",loginUser);
        }

        //2. 获取文章
        CommonResult<Article> result = restTemplate.exchange(BLOG_SERVER_URL + "/article/getbyid/" + id, HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<Article>>() {
        }).getBody();

        if(result!=null){
            if(result.getCode()==200){
                log.info(String.valueOf(result.getData()));
                //查询成功,跳转文章详情界面
                model.addAttribute("article",result.getData());
            }else{
                return "view/error/article404";
            }
        }else{
            //查询失败
            return "view/error/article404";
        }

        //3. 查询作者信息
        CommonResult<User> result2 = restTemplate.exchange(BLOG_SERVER_URL + "/user/getbyid/" + result.getData().getOwnerUserId(), HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<User>>() {
        }).getBody();
        if(result2!=null){
            if(result2.getCode()==200){
                model.addAttribute("author",result2.getData());
            }
        }
        log.info(String.valueOf(result2.getData()));

        return "view/admin/article";

    }
}
