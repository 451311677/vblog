package com.xaut.blog.controller;

import com.xaut.blog.entity.Article;
import com.xaut.blog.entity.CommonResult;
import com.xaut.blog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 发布文章
     * @param article
     * @return
     */
    @PostMapping("/insert")
    public CommonResult<?> insert(Article article){
        int count = articleService.insert(article);
        if(count>0){
            return new CommonResult<String>(200,"插入成功");
        }
        return new CommonResult<String>(400,"插入失败");
    }

    /**
     * 更新文章
     * @param article
     * @return
     */
    @PostMapping("/update")
    public CommonResult<?> update(Article article){
        int count = articleService.update(article);
        if(count>0){
            return new CommonResult<String>(200,"更新成功");
        }
        return new CommonResult<String>(400,"更新失败");
    }

    /**
     * 删除文章
     * @param articleId
     * @return
     */
    @GetMapping("/delete/{id}")
    public CommonResult<?> delete(@PathVariable("id") Long articleId){
        int count = articleService.delete(articleId);
        if(count>0){
            return new CommonResult<String>(200,"删除成功");
        }
        return new CommonResult<String>(400,"删除失败");
    }

    /**
     * 通过Id查询文章
     * @param articleId
     * @return
     */
    @GetMapping("/getbyid/{id}")
    public CommonResult<?> getById(@PathVariable("id") Long articleId){
        Article article = articleService.getById(articleId);
        if(article!=null){
            return new CommonResult<Article>(200,"查询成功",article);
        }
        return new CommonResult<>(400,"查询失败");
    }

    /**
     * 通过名字查询文章
     * @param articleName
     * @return
     */
    @GetMapping("/getbyname/{name}")
    public CommonResult<?> getByName(@PathVariable("name") String articleName){
        List<Article> list = articleService.getByName(articleName);
        if(list!=null && list.size()!=0){
            return new CommonResult<List<Article>>(200,"查询成功",list);
        }
        return new CommonResult<List<Article>>(400,"查询失败");
    }

    /**
     * 通过标签查询文章
     * @param articleIdList
     * @return
     */
    @GetMapping("getbylabel")
    public CommonResult<?> getByLabel(@RequestBody List<String> articleIdList){
        List<Article> list = articleService.getByLabel(articleIdList);
        if(list!=null && list.size()!=0){
            return new CommonResult<List<Article>>(200,"查询成功",list);
        }
        return new CommonResult<List<Article>>(400,"查询失败");
    }


}
