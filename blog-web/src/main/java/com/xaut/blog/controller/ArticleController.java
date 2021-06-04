package com.xaut.blog.controller;

import com.xaut.blog.entity.*;
import com.xaut.blog.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private HttpUtils httpUtils;

    private final String BLOG_SERVER_URL = "http://localhost:8080";


    /**
     * 查看文章
     *
     * @param request
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    public String look(HttpServletRequest request, Model model, @PathVariable("id") Long id) {

        //1. 获取当前用户登录信息
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser != null) {
            model.addAttribute("loginUser", loginUser);
        }

        //2. 获取文章
        CommonResult<Article> result = restTemplate.exchange(BLOG_SERVER_URL + "/article/getbyid/" + id, HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<Article>>() {
        }).getBody();

        Article article = null;

        if (result != null) {
            if (result.getCode() == 200) {
                log.info(String.valueOf(result.getData()));
                //查询成功,跳转文章详情界面
//                model.addAttribute("article",result.getData());
                article = result.getData();
                session.setAttribute("on_article", article);
            } else {
                return "view/error/article404";
            }
        } else {
            //查询失败
            return "view/error/article404";
        }

        //3. 查询作者信息
        CommonResult<User> result2 = restTemplate.exchange(BLOG_SERVER_URL + "/user/getbyid/" + result.getData().getOwnerUserId(), HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<User>>() {
        }).getBody();
        if (result2 != null) {
            if (result2.getCode() == 200) {
//                model.addAttribute("author",result2.getData());
                article.setAuthor(result2.getData());
            }
            log.info(String.valueOf(result2.getData()));
        }

        model.addAttribute("article", article);
        session.setAttribute("article", article);
        session.setAttribute("articleOP", "update");

        //4. 获取评论
        CommonResult<List<Comment>> result3 = restTemplate.exchange(BLOG_SERVER_URL + "/comment/getcommentlist/" + article.getArticleId(), HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<List<Comment>>>() {
        }).getBody();
        List<Comment> comments = new ArrayList<>();

        if (result3 != null) {
            if (result3.getCode() == 200) {
                comments = result3.getData();
            }
        }
        model.addAttribute("comments", comments);
        log.info(String.valueOf(comments));

        //5. 是否点赞
        if (loginUser != null) {
            CommonResult<Like> result5 = restTemplate.exchange(BLOG_SERVER_URL + "/like/islike?ownerUserId=" + loginUser.getUserId() + "&&articleId=" + article.getArticleId(), HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<Like>>() {
            }).getBody();
            if (result5 != null) {
                if (result5.getCode() == 200) {
                    log.info(result5.toString());
                    model.addAttribute("isLike", true);
                } else {
                    model.addAttribute("isLike", false);
                }
            } else {
                model.addAttribute("isLike", false);
            }
        } else {
            model.addAttribute("isLike", false);
        }

        //6. 是否收藏
        if (loginUser != null) {
            CommonResult<Like> result6 = restTemplate.exchange(BLOG_SERVER_URL + "/collection/iscollection?ownerUserId=" + loginUser.getUserId() + "&&ownerArticleId=" + article.getArticleId(), HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<Like>>() {
            }).getBody();
            if (result6 != null) {
                if (result6.getCode() == 200) {
                    model.addAttribute("isCollection", true);
                } else {
                    model.addAttribute("isCollection", false);
                }
            } else {
                model.addAttribute("isCollection", false);
            }
        } else {
            model.addAttribute("isCollection", false);
        }

        return "view/admin/article";

    }


    /**
     * 文章编辑
     *
     * @param request
     * @param model
     * @param articleId
     * @return
     */
    @GetMapping("/edit/{id}")
    private String edit(HttpServletRequest request, Model model, @PathVariable("id") Long articleId) {
        HttpSession session = request.getSession();
        Article article = (Article) session.getAttribute("article");
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("article", article);
        model.addAttribute("loginUser", loginUser);


        return "view/admin/publish";

    }

    @PostMapping("/comment")
    public String comment(HttpServletRequest request, Model model) {
        String content = request.getParameter("comment_content");
        String articleId = request.getParameter("articleId");

        // 1. 获取登录信息
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);


        // 2. 未登录，跳转登录
        if (loginUser == null) {
            session.setAttribute("jumpPage", "article/detail");
            session.setAttribute("articleNo", articleId);
            return "view/admin/login";
        }

        //3. 已登录,评论
        Comment comment = new Comment();
        comment.setOwnerArticleId(Long.parseLong(articleId));
        comment.setOwnerUserId(loginUser.getUserId());
        comment.setText(content);

        RequestEntity<String> entity = httpUtils.getRequestEntity(BLOG_SERVER_URL + "/comment/insert", comment);

        CommonResult result = restTemplate.exchange(entity, new ParameterizedTypeReference<CommonResult>() {
        }).getBody();
        if (result != null) {
            if (result.getCode() == 200) {
                //评论成功
            } else {
                //评论失败
            }
        }
        //2. 获取文章
        CommonResult<Article> result2 = restTemplate.exchange(BLOG_SERVER_URL + "/article/getbyid/" + articleId, HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<Article>>() {
        }).getBody();

        Article article = null;

        if (result2 != null) {
            if (result2.getCode() == 200) {
                log.info(String.valueOf(result2.getData()));
                //查询成功,跳转文章详情界面
//                model.addAttribute("article",result.getData());
                article = result2.getData();
                session.setAttribute("on_article", article);
            } else {
                return "view/error/article404";
            }
        } else {
            //查询失败
            return "view/error/article404";
        }

        //3. 查询作者信息
        CommonResult<User> result3 = restTemplate.exchange(BLOG_SERVER_URL + "/user/getbyid/" + result2.getData().getOwnerUserId(), HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<User>>() {
        }).getBody();
        if (result3 != null) {
            if (result3.getCode() == 200) {
                article.setAuthor(result3.getData());
            }
            log.info(String.valueOf(result3.getData()));
        }

        model.addAttribute("article", article);
        session.setAttribute("article", article);
        session.setAttribute("articleOP", "update");

        //4. 获取评论
        CommonResult<List<Comment>> result4 = restTemplate.exchange(BLOG_SERVER_URL + "/comment/getcommentlist/" + article.getArticleId(), HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<List<Comment>>>() {
        }).getBody();
        List<Comment> comments = new ArrayList<>();

        if (result4 != null) {
            if (result4.getCode() == 200) {
                comments = result4.getData();
            }
        }
        model.addAttribute("comments", comments);
        log.info(String.valueOf(comments));

        //5. 是否点赞
        CommonResult<Like> result5 = restTemplate.exchange(BLOG_SERVER_URL + "/like/islike?ownerUserId=" + loginUser.getUserId() + "&&articleId=" + articleId, HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<Like>>() {
        }).getBody();
        if (result5 != null) {
            if (result5.getCode() == 200) {
                log.info(result5.toString());
                model.addAttribute("isLike", true);
            } else {
                model.addAttribute("isLike", false);
            }
        } else {
            model.addAttribute("isLike", false);
        }

        return "view/admin/article";

    }

    /**
     * 点赞
     * @param request
     * @return
     */
    @GetMapping("/like")
    @ResponseBody
    public Map<String, Object> like(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        Article on_article = (Article) session.getAttribute("on_article");

        if (loginUser == null) {
            modelMap.put("success", false);
            modelMap.put("message", "请先登录");
            return modelMap;
        }

        if (on_article == null) {
            modelMap.put("success", false);
            modelMap.put("message", "文章出错");
            return modelMap;
        }


        Like like = new Like(loginUser.getUserId(), on_article.getArticleId());
        RequestEntity<String> entity = httpUtils.getRequestEntity(BLOG_SERVER_URL + "/like/like", like);
        CommonResult result = restTemplate.exchange(entity, new ParameterizedTypeReference<CommonResult>() {
        }).getBody();
        if (result != null) {
//            if(result.getCode()==200){
                modelMap.put("success", true);
//            }
//            modelMap.put("success", false);
        } else {
            modelMap.put("success", false);

        }
        return modelMap;
    }
    /**
     * 点赞
     * @param request
     * @return
     */
    @GetMapping("/unlike")
    @ResponseBody
    public Map<String, Object> unlike(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        Article on_article = (Article) session.getAttribute("on_article");

        if (loginUser == null) {
            modelMap.put("success", false);
            modelMap.put("message", "请先登录");
            return modelMap;
        }

        if (on_article == null) {
            modelMap.put("success", false);
            modelMap.put("message", "文章出错");
            return modelMap;
        }


        CommonResult result = restTemplate.exchange(BLOG_SERVER_URL + "/like/unlike?ownerUserId="+loginUser.getUserId()+"&&ownerArticleId="+on_article.getArticleId(), HttpMethod.GET,null,new ParameterizedTypeReference<CommonResult>() {
        }).getBody();
        if (result != null) {
//            if(result.getCode()==200){
                modelMap.put("success", true);
//            }
//            modelMap.put("success", false);
        } else {
            modelMap.put("success", false);

        }


        return modelMap;
    }


    /**
     * 收藏
     * @param request
     * @return
     */
    @GetMapping("/collection")
    @ResponseBody
    public Map<String, Object> collection(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        Article on_article = (Article) session.getAttribute("on_article");

        if (loginUser == null) {
            modelMap.put("success", false);
            modelMap.put("message", "请先登录");
            return modelMap;
        }

        if (on_article == null) {
            modelMap.put("success", false);
            modelMap.put("message", "文章出错");
            return modelMap;
        }


        Collection collection = new Collection(loginUser.getUserId(), on_article.getArticleId());
        RequestEntity<String> entity = httpUtils.getRequestEntity(BLOG_SERVER_URL + "/collection/insert", collection);
        CommonResult result = restTemplate.exchange(entity, new ParameterizedTypeReference<CommonResult>() {
        }).getBody();
        if (result != null) {
//            if(result.getCode()==200){
                modelMap.put("success", true);
//            }
//            modelMap.put("success", false);
        } else {
            modelMap.put("success", false);

        }
        return modelMap;
    }


    /**
     * 收藏
     * @param request
     * @return
     */
    @GetMapping("/uncollection")
    @ResponseBody
    public Map<String, Object> uncollection(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        Article on_article = (Article) session.getAttribute("on_article");

        if (loginUser == null) {
            modelMap.put("success", false);
            modelMap.put("message", "请先登录");
            return modelMap;
        }

        if (on_article == null) {
            modelMap.put("success", false);
            modelMap.put("message", "文章出错");
            return modelMap;
        }


        CommonResult result = restTemplate.exchange(BLOG_SERVER_URL + "/collection/deletebyid?ownerUserId="+loginUser.getUserId()+"&&ownerArticleId="+on_article.getArticleId(), HttpMethod.GET,null,new ParameterizedTypeReference<CommonResult>() {
        }).getBody();
        if (result != null) {
//            if(result.getCode()==200){
                modelMap.put("success", true);
//            }
//            modelMap.put("success", false);
        } else {
            modelMap.put("success", false);

        }
        return modelMap;
    }


}
