package com.xaut.blog.controller;

import com.xaut.blog.dao.LabelDao;
import com.xaut.blog.entity.Article;
import com.xaut.blog.entity.CommonResult;
import com.xaut.blog.entity.Like;
import com.xaut.blog.service.*;
import com.xaut.blog.util.IpUtils;
import com.xaut.blog.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.NewThreadAction;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author zxg
 * @date 2021/4/8
 * @description 文章
 */
@RestController
@RequestMapping("/article")
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private  IpUtils ipUtils;

    @Autowired
    private  RedisUtils redisUtils;

    /**
     * 发布文章
     *
     * @param article
     * @return
     */
    @PostMapping("/insert")
    public CommonResult<?> insert(@RequestBody Article article) {

        //设置初始数据
        article.setPageView(0);
        article.setCreateTime(new Date());
        article.setLastEditTime(new Date());
        System.out.println(article);

        try {
            int count = articleService.insert(article);
            if (count > 0) {
                return new CommonResult<String>(200, "插入成功");
            }
            return new CommonResult<String>(400, "插入失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult<String>(400, "插入失败");
        }
    }

    /**
     * 更新文章
     *
     * @param article
     * @return
     */
    @PostMapping("/update")
    public CommonResult<?> update(@RequestBody Article article) {
        int count = articleService.update(article);
        if (count > 0) {
            return new CommonResult<String>(200, "更新成功");
        }
        return new CommonResult<String>(400, "更新失败");
    }

    /**
     * 删除文章
     *
     * @param articleId
     * @return
     */
    @GetMapping("/delete/{id}")
    public CommonResult<?> delete(@PathVariable("id") Long articleId) {
        int count = articleService.delete(articleId);
        if (count > 0) {
            return new CommonResult<String>(200, "删除成功");
        }
        return new CommonResult<String>(400, "删除失败");
    }

    /**
     * 通过Id查询文章
     *
     * @param articleId
     * @return
     */
    @GetMapping("/getbyid/{id}")
    public CommonResult<?> getById(HttpServletRequest request, @PathVariable("id") Long articleId) {
        Article article = articleService.getById(articleId);
        if (article != null) {
            //设置标签
            String[] labels = article.getLabel().split(",");
            List<String> labelList = new ArrayList<>(Arrays.asList(labels));
            article.setLabelList(labelList);
            //查询评论数
            int count = commentService.count(articleId);
            article.setCommentNumber(Math.max(count, 0));
            //查询点赞数
            count = likeService.count(articleId);
            article.setLikeNumber(Math.max(count, 0));
            //查询收藏数
            count = collectionService.count(articleId);
            article.setCollectionNumber(Math.max(count, 0));

            //浏览量加1
            String ip = ipUtils.getIpAddr(request);
            article = pageView(article,ip);

            return new CommonResult<Article>(200, "查询成功", article);
        }
        return new CommonResult<>(400, "查询失败");
    }

    /**
     * 通过名字查询文章
     *
     * @param articleName
     * @return
     */
    @GetMapping("/getbyname/{name}")
    public CommonResult<?> getByName(@PathVariable("name") String articleName) {
        List<Article> list = articleService.getByName(articleName);
        if (list != null && list.size() != 0) {
            return new CommonResult<List<Article>>(200, "查询成功", list);
        }
        return new CommonResult<List<Article>>(400, "查询失败");
    }

    /**
     * 通过标签查询文章
     *
     * @param articleIdList
     * @return
     */
    @GetMapping("getbylabel")
    public CommonResult<?> getByLabel(@RequestBody List<String> articleIdList) {
        List<Article> list = articleService.getByLabel(articleIdList);
        if (list != null && list.size() != 0) {
            return new CommonResult<List<Article>>(200, "查询成功", list);
        }
        return new CommonResult<List<Article>>(400, "查询失败");
    }

    /**
     * 文章浏览量加1,防止刷访问量
     * @param article
     * @param ip
     * @return
     */
    public Article pageView(Article article,String ip) {
        String s = redisUtils.get(ip);
        if (s == null) {
            article.setPageView(article.getPageView() + 1);
            try {
                articleService.update(article);
                redisUtils.setEx(ip, "1", 2);
            } catch (Exception ignored) {
            }
        }

        return article;
    }


}
