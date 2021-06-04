package com.xaut.blog.controller;

import com.xaut.blog.entity.Article;
import com.xaut.blog.entity.Collection;
import com.xaut.blog.entity.CommonResult;
import com.xaut.blog.service.CollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zxg
 * @date 2021/4/14
 * @description 收藏
 */
@RestController
@RequestMapping("/collection")
@Slf4j
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @PostMapping("/insert")
    public CommonResult<?> insert(HttpServletRequest request,@RequestBody Collection collection){
        int count = collectionService.insert(collection);
        if(count>0){
            return new CommonResult<>(200,"插入成功");
        }
        return new CommonResult<>(400,"插入失败");
    }


    @GetMapping("/article/{id}")
    public CommonResult<?> getCollectionList(@PathVariable("id") Long ownerUserId){
        List<Article> list = collectionService.getCollectionList(ownerUserId);
        if(list!=null){
            return new CommonResult<List>(200,"查询成功",list);
        }
        return new CommonResult<>(400,"查询失败");
    }

    @GetMapping("/iscollection")
    public CommonResult<?> isCollection(HttpServletRequest request){
        Long ownerUserId = Long.valueOf(request.getParameter("ownerUserId"));
        Long ownerArticleId = Long.valueOf(request.getParameter("ownerArticleId"));
        Collection collection = collectionService.isCollection(ownerUserId, ownerArticleId);
        if(collection!=null){
            return new CommonResult<Collection>(200,"查询成功",collection);
        }
        return new CommonResult<Collection>(400,"查询失败");
    }


    /**
     * 通过ownerUserId与ownerArticleId取消收藏
     * @param request
     * @return
     */
    @GetMapping("/deletebyid")
    public CommonResult<?> deleteById(HttpServletRequest request){
        Long ownerUserId = Long.valueOf(request.getParameter("ownerUserId"));
        Long ownerArticleId = Long.valueOf(request.getParameter("ownerArticleId"));
        try {
            int count = collectionService.deleteById(ownerUserId, ownerArticleId);

            if (count > 0) {
                return new CommonResult<>(200, "取消收藏成功");
            }
            return new CommonResult<>(400, "取消收藏失败");
        } catch (Exception e) {
            return new CommonResult<>(400, "取消收藏失败");
        }
    }

    @GetMapping("/delete/{id}")
    public CommonResult<?> delete(HttpServletRequest request,@PathVariable("id") Long collectionId){
        try{
            int count = collectionService.delete(collectionId);
            if (count > 0) {
                return new CommonResult<>(200, "取消收藏成功");
            }
            return new CommonResult<>(400, "取消收藏失败");
        }catch (Exception e){
            return new CommonResult<>(400, "取消收藏失败");
        }
    }

    @GetMapping("/user/{id}")
    public CommonResult<?> getCollectionList(HttpServletRequest request,@PathVariable("id") Long ownerUserId){
        List<Article> list = collectionService.getCollectionList(ownerUserId);
        if(list!=null){
            return new CommonResult<List<Article>>(200,"查询成功",list);
        }
        return new CommonResult<>(400,"查询失败");
    }

}
