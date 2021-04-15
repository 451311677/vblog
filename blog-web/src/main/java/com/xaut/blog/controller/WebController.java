package com.xaut.blog.controller;

import com.xaut.blog.entity.CommonResult;
import com.xaut.blog.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

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
        return "/view/index";
    }
    @GetMapping("/index")
    public String index2(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);
        return "/view/index";
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


    @GetMapping("/publish")
    public String publish(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        log.info("loginUser1: "+loginUser);
        if(loginUser!=null){
            model.addAttribute("loginUser",loginUser);
            return  "/view/admin/publish";
        }else{
            //跳转login界面登路操作
//            CommonResult<User> result = restTemplate.getForObject(BLOG_SERVER_URL + "/login", CommonResult.class);
//            ResponseEntity<CommonResult> entity = restTemplate.getForEntity(BLOG_SERVER_URL + "/login", CommonResult.class);
//            CommonResult<User> result = entity.getBody();
            ResponseEntity<CommonResult<User>> entity = restTemplate.exchange(BLOG_SERVER_URL + "/login", HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<User>>() {
            });
            CommonResult<User> result = entity.getBody();

            if(result!=null) {
                loginUser = result.getData();
                session.setAttribute("loginUser", loginUser);
                log.info("loginUser: " + loginUser);
                model.addAttribute("loginUser", loginUser);
                return "/view/index";
            }

        }
        return "/view/index";
    }
//    @GetMapping("/mymd")
//    public String index2(){
//        return "view/index";
//    }
}
