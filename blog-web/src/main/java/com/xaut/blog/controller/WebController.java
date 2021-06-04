package com.xaut.blog.controller;

import com.xaut.blog.entity.Article;
import com.xaut.blog.entity.CommonResult;
import com.xaut.blog.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiaogang.zhang
 * @date 2021/2/26
 * @description
 */
@Controller
@Slf4j
public class WebController {

    private static final String BLOG_WEB_URL = "http://localhost:81";
    private static final String BLOG_SERVER_URL = "http://localhost:8080";

    @Resource
    private RestTemplate restTemplate;

    /**
     * 首页
     * @return
     */
    @GetMapping("/")
    public String index(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);

        CommonResult<List<Article>> result = restTemplate.exchange(BLOG_SERVER_URL + "/article/getbyname/Blog", HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<List<Article>>>() {
        }).getBody();
        if(result!=null){
            if(result.getCode()==200){
                log.info(String.valueOf(result.getData()));
                model.addAttribute("articles",result.getData());
            }
        }
        model.addAttribute("orderMode",0);

        return "/view/index";
    }

    /**
     * 最新
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/index")
    public String indexNew(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);

        CommonResult<List<Article>> result = restTemplate.exchange(BLOG_SERVER_URL + "/article/getbyname/Blog", HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<List<Article>>>() {
        }).getBody();
        if(result!=null){
            if(result.getCode()==200){
                log.info(String.valueOf(result.getData()));
                model.addAttribute("articles",result.getData());
            }
        }
        model.addAttribute("orderMode",0);
        return "view/index";
    }

    @GetMapping("/index/{orderMode}")
    public String indexHot(HttpServletRequest request, Model model, @PathVariable("orderMode") Integer orderMode){
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);

        if(orderMode==0){
            //获取最新
        }else{
            //获取最热
        }

        return "view/index";

    }

    /**
     * 检索文章
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/search")
    private String search(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);

        String keyword = request.getParameter("keyword");
        log.info("检索文章 keywoord:"+keyword);


        model.addAttribute("keyword",keyword);

        if(keyword==null|| keyword.length()==0){
            //关键词为空
            CommonResult<List<Article>> result = restTemplate.exchange(BLOG_SERVER_URL + "/article/getbyname/Blog", HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<List<Article>>>() {
            }).getBody();
            if(result!=null){
                if(result.getCode()==200){
                    log.info(String.valueOf(result.getData()));
                    model.addAttribute("articles",result.getData());
                }
            }

        } else{
            CommonResult<List<Article>> result = restTemplate.exchange(BLOG_SERVER_URL + "/article/getbyname/" + keyword, HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<List<Article>>>() {
            }).getBody();

            if(result!=null){
                if(result.getCode()==200){
                    //查询成功
                    model.addAttribute("articles",result.getData());

                }
            }

        }

        return "view/index";
    }


//    /**
//     * 通用页面跳转
//     * @param httpServletRequest
//     * @return
//     */
//    @GetMapping("/toPage")
//    public String toPage(HttpServletRequest httpServletRequest){
//        String url = httpServletRequest.getParameter("url");
//        return url;
//    }

//    @GetMapping("/login")
//    public String login(){
//        return "/view/admin/login";
//    }

}
