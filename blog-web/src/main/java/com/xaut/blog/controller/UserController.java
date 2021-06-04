package com.xaut.blog.controller;

import com.xaut.blog.entity.Article;
import com.xaut.blog.entity.CommonResult;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author zxg
 * @date 2021/4/19
 * @description
 */
@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpUtils httpUtils;

    private static final String BLOG_SERVER_URL = "http://localhost:8080";

    /**
     * 跳转
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/setting")
    public String setting(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser != null) {

            //获取User详细信息
            CommonResult<User> result = restTemplate.exchange(BLOG_SERVER_URL + "/user/getbyid/" + loginUser.getUserId(), HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<User>>() {
            }).getBody();
            if (result != null) {
                if (result.getCode() == 200) {
                    loginUser = result.getData();
                    log.info(String.valueOf(loginUser));
                    model.addAttribute("loginUser", loginUser);
                    return "view/admin/setting";
                }
            }
        }
        return "view/admin/login";
    }

    @GetMapping("/profile/{admin}")
    public String profile(HttpServletRequest request, Model model, @PathVariable("admin") String admin) {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser != null) {
            model.addAttribute("loginUser", loginUser);
        }
        model.addAttribute("tab", "profile");

        //获取User详细信息
        CommonResult<User> result = restTemplate.exchange(BLOG_SERVER_URL + "/user/get/" + admin, HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<User>>() {
        }).getBody();
        if (result != null) {
            if (result.getCode() == 200) {
                log.info(String.valueOf(result.getData()));
                model.addAttribute("user", result.getData());
                return "view/admin/profile";
            }
        }

        return "view/error/user404";
    }

    /**
     * 跳转我的帖子
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/article/{admin}")
    public String articleManagement(HttpServletRequest request, Model model, @PathVariable("admin") String admin) {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser != null) {
            model.addAttribute("loginUser", loginUser);
        }
        model.addAttribute("tab", "mypost");
        //获取User详细信息
        CommonResult<User> result = restTemplate.exchange(BLOG_SERVER_URL + "/user/get/" + admin, HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<User>>() {
        }).getBody();
        if (result != null) {
            if (result.getCode() == 200) {
                log.info(String.valueOf(result.getData()));
                model.addAttribute("user", result.getData());
//                return "view/admin/profile";
            }
        }

        //获取帖子列表
        CommonResult<List<Article>> result2 = restTemplate.exchange(BLOG_SERVER_URL + "/article/getbyadmin/" + admin, HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<List<Article>>>() {
        }).getBody();
        if (result2 != null) {
            if (result2.getCode() == 200) {
                log.info(String.valueOf(result2.getData()));
                model.addAttribute("articles", result2.getData());
                return "view/admin/profile";
            }
        }

        return "view/admin/user404";
    }

    /**
     * 跳转我的收藏
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/collection/{admin}")
    public String articleCollection(HttpServletRequest request, Model model, @PathVariable("admin") String admin) {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser != null) {
            model.addAttribute("loginUser", loginUser);
        }
        model.addAttribute("tab", "collection");
        //获取User详细信息
        CommonResult<User> result = restTemplate.exchange(BLOG_SERVER_URL + "/user/get/" + admin, HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<User>>() {
        }).getBody();
        if (result != null) {
            if (result.getCode() == 200) {
                log.info(String.valueOf(result.getData()));
                model.addAttribute("user", result.getData());
//                return "view/admin/profile";
                //获取帖子列表
                CommonResult<List<Article>> result2 = restTemplate.exchange(BLOG_SERVER_URL + "/collection/article/" + result.getData().getUserId(), HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<List<Article>>>() {
                }).getBody();
                if (result2 != null) {
                    if (result2.getCode() == 200) {
                        log.info(String.valueOf(result2.getData()));
                        model.addAttribute("articles", result2.getData());
                        return "view/admin/profile";
                    }
                }
            }
        }

        return "view/admin/user404";
    }


    @GetMapping("/care/{admin}")
    public String care(HttpServletRequest request, Model model, @PathVariable("admin") String admin) {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser != null) {
            model.addAttribute("loginUser", loginUser);
        }
        model.addAttribute("tab", "care");
        //获取User详细信息
        CommonResult<User> result = restTemplate.exchange(BLOG_SERVER_URL + "/user/get/" + admin, HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<User>>() {
        }).getBody();
        if (result != null) {
            if (result.getCode() == 200) {
                log.info(String.valueOf(result.getData()));
                model.addAttribute("user", result.getData());
//                return "view/admin/profile";

                System.out.println("+++++++++++++++");
                CommonResult<List<User>> result2 = restTemplate.exchange(BLOG_SERVER_URL + "/care/user/" + result.getData().getUserId(), HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<List<User>>>() {
                }).getBody();
                System.out.println("^^^^^^^^^^^^^^^^^^^^");
                if (result2 != null) {
                    System.out.println("@@@@@@@@@@@@@@@@@@@@");
                    if (result2.getCode() == 200) {
                        System.out.println("=============");
                        log.info(String.valueOf(result2.getData()));
                        model.addAttribute("cares", result2.getData());
                        return "view/admin/profile";
                    }
                }
            }
        }

        return "view/admin/user404";
    }

    /**
     * 更新用户信息
     * @param request
     * @param model
     * @param user
     * @return
     */
    @PostMapping("/update")
    public String update(HttpServletRequest request, Model model, User user) {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "view/index";
        }


        //更新信息
        User newUser = (User) session.getAttribute("loginUser");
        newUser.setUserName(user.getUserName());
        newUser.setGender(user.getGender());

        RequestEntity<String> requestEntity = httpUtils.getRequestEntity(BLOG_SERVER_URL + "/user/update", newUser);


        CommonResult<String> updateResult = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<CommonResult<String>>() {
        }).getBody();

        if (updateResult != null) {
            if (updateResult.getCode() == 200) {
                //更新成功
            }else{
                //更新失败
                model.addAttribute("loginUser", loginUser);
                model.addAttribute("userNameErr","昵称已存在");
                return "view/admin/setting";
            }
        }


        //获取新信息
        //获取User详细信息
        CommonResult<User> result = restTemplate.exchange(BLOG_SERVER_URL + "/user/getbyid/" + loginUser.getUserId(), HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<User>>() {
        }).getBody();
        if (result != null) {
            if (result.getCode() == 200) {
                loginUser = result.getData();
                log.info(String.valueOf(loginUser));
                model.addAttribute("loginUser", loginUser);
                return "view/admin/setting";
            }
        }


        return "view/admin/setting";
    }

    @PostMapping("/setpassword")
    public String setPassword(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        if(loginUser.getPassword().equals(oldPassword)){
            model.addAttribute("loginUser", loginUser);
            model.addAttribute("oldPasswordError","原密码不对");
            return "view/admin/setting";
        }


        User loginUser1 = (User) session.getAttribute("loginUser");
        loginUser1.setPassword(newPassword);

        //更新用户信息
        RequestEntity<String> requestEntity = httpUtils.getRequestEntity(BLOG_SERVER_URL + "/user/update",loginUser1);

        CommonResult<User> result = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<CommonResult<User>>() {
        }).getBody();

        if(result!=null){
            if(result.getCode()==200){
                //更新成功
                model.addAttribute("loginUser",loginUser1);
            }else{
                //更新失敗
                model.addAttribute("loginUser",loginUser);

            }
        }

        return "view/admin/setting";
    }
}
