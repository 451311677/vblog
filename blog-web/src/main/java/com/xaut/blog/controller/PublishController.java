package com.xaut.blog.controller;

import com.xaut.blog.entity.Article;
import com.xaut.blog.entity.CommonResult;
import com.xaut.blog.entity.Label;
import com.xaut.blog.entity.User;
import com.xaut.blog.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author zxg
 * @date 2021/4/19
 * @description 发布博客
 */
@Controller
@Slf4j
public class PublishController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpUtils httpUtils;

    private static final String BLOG_SERVER_URL = "http://localhost:8080";

    @PostMapping("/publishlabel")
    public String publishlabel(HttpServletRequest request, Model model, Article article) {
        HttpSession session = request.getSession();
//        String articleName = request.getParameter("articleName");
//        String text = request.getParameter("text");

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "view/admin/login";
        }

        String articleOP = (String) session.getAttribute("articleOP");
        Article onlineArticle;
        if("update".equals(articleOP)){
            onlineArticle = (Article) session.getAttribute("article");
            log.info("# "+ onlineArticle);
            if(article.getArticleName()!=null&& !"".equals(article.getArticleName())){
                onlineArticle.setArticleName(article.getArticleName());
            }
            onlineArticle.setText(article.getText());
            session.setAttribute("article",onlineArticle);
            model.addAttribute("articleName", onlineArticle.getArticleName());

        }else{
            article.setOwnerUserId(loginUser.getUserId());
            //发布
            article.setState(1);
            session.setAttribute("article", article);

            System.out.println(article);
            model.addAttribute("articleName", article.getArticleName());

        }
        model.addAttribute("loginUser", loginUser);

        //获取初始标签
        CommonResult<List<Label>> result = restTemplate.exchange(BLOG_SERVER_URL + "/label/init/12", HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<List<Label>>>() {
        }).getBody();
        if (result != null) {
            if (result.getCode() == 200) {
                System.out.println(result.getData());
                model.addAttribute("labelList", result.getData());
            }
        }

        return "view/admin/publishlabel";
    }

    @PostMapping("/publish")
    public String publishlabel(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Article article = (Article) session.getAttribute("article");
        String label = request.getParameter("label");

        label = label.trim().replaceAll("( )+", ",");

        //设置标签
        article.setLabel(label);

        String articleOP = (String) session.getAttribute("articleOP");
        RequestEntity<String> requestEntity;
        if("update".equals(articleOP)){
            //更新
            requestEntity = httpUtils.getRequestEntity(BLOG_SERVER_URL + "/article/update", article);
        }else{
            //发布插入
            requestEntity = httpUtils.getRequestEntity(BLOG_SERVER_URL + "/article/insert", article);
        }


        CommonResult<String> result = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<CommonResult<String>>() {
        }).getBody();

        session.removeAttribute("articleOP");

        if(result!=null){
            if(result.getCode()==200){
                log.info("发布成功");
            }
        }
        log.info("发布失败");

        return "view/index";

//# 一级标题
//## 二级标题
//### 三级标题
//
//1. 的撒
//2. dd
//
//- zxg
//- www
//- uu

    }
}
