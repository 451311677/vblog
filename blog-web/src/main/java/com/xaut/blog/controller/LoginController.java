package com.xaut.blog.controller;

import com.alibaba.fastjson.JSON;
import com.xaut.blog.entity.CommonResult;
import com.xaut.blog.entity.User;
import com.xaut.blog.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;

/**
 * @author xiaogang.zhang
 * @date 2021/3/2
 * @description
 */
@Controller
@Slf4j
public class LoginController {

    private static final String BLOG_SERVER_URL = "http://localhost:8080";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private HttpUtils httpUtils;

    @PostMapping("/login")
    public String login(HttpServletRequest request, Model model, User user) {
        HttpSession session = request.getSession();

//        URI uri = UriComponentsBuilder.fromUriString(BLOG_SERVER_URL + "/login").build().toUri();
//        String s = JSON.toJSONString(user);
//        RequestEntity<String> requestEntity = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).header("Content-Type", "application/json").body(s);

        RequestEntity<String> requestEntity = httpUtils.getRequestEntity(BLOG_SERVER_URL + "/login",user);

        CommonResult<User> result = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<CommonResult<User>>() {
        }).getBody();

        if (result != null) {
            if (result.getCode() == 200) {
                log.info(String.valueOf(result.getData()));
                session.setAttribute("loginUser", (User) result.getData());
                model.addAttribute("loginUser", result.getData());
                return "/view/index";
            }else{
                if("密码错误".equals(result.getMessage())){
                    model.addAttribute("passwordMsg","密码错误");
                }
                if("用户名不存在".equals(result.getMessage())){
                    model.addAttribute("adminMsg","用户名不存在");
                }
            }

        }
        return "/view/admin/login";
    }

    @PostMapping("/register")
    public String register(HttpServletRequest request,Model model,User user){

        HttpSession session = request.getSession();

        RequestEntity<String> requestEntity = httpUtils.getRequestEntity(BLOG_SERVER_URL + "/user/register",user);

        CommonResult<User> result = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<CommonResult<User>>() {
        }).getBody();
        if(result!=null){
            if (result.getCode()==200){
                session.setAttribute("loginUser",result.getData());
                model.addAttribute("loginUser",result.getData());
                return "/view/index";
            }
        }
        model.addAttribute("loginUser",user);
        model.addAttribute("adminMsg","用户名已存在");
        return "/view/admin/register";
    }

    @PostMapping("/forget")
    public String forget(HttpServletRequest request,Model model){
        //邮箱
        String email = request.getParameter("email");
        String captcha = request.getParameter("captcha");
        String password = request.getParameter("password");
        model.addAttribute("email",email);
        model.addAttribute("captcha",captcha);
        model.addAttribute("password",password);

        //验证码
        if(captcha==null|| captcha.length()==0){
            model.addAttribute("captchaMsg","请输入验证码");
            return "view/admin/forget";
        }
        //新密码
        if(password==null||password.length()<6){
            model.addAttribute("passwordMsg","密码长度需大于6");
            return "view/admin/forget";
        }

        CommonResult<User> result = restTemplate.exchange(BLOG_SERVER_URL+"/updatepassword?email="+email+"&captcha="+captcha+"&password="+password,HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<User>>() {
        }).getBody();
        if(result!=null){
            log.info(String.valueOf(result));
            if(result.getCode()==200){
                //密码更新成功
                model.addAttribute("success",true);
            }else{
                if ("验证码不正确".equals(result.getMessage())){
                    //验证码不正确
                    model.addAttribute("captchaMsg","验证码不正确");
                }else if("邮箱未注册".equals(result.getMessage())){
                    //邮箱未注册
                    model.addAttribute("emailMsg","邮箱未注册");
                }else if("密码重置失败".equals(result.getMessage())){
                    //密码重置失败
                    model.addAttribute("passwordMsg","密码重置失败，请稍后再试");
                }
            }
        }

        return "view/admin/forget";
    }

    @GetMapping("/quit")
    public String quit(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("loginUser");

        CommonResult<User> result = restTemplate.exchange(BLOG_SERVER_URL+"/quit",HttpMethod.GET, null, new ParameterizedTypeReference<CommonResult<User>>() {
        }).getBody();
        if(result!=null){
            if(result.getCode()==200){
                //退出成功
                return "view/index";
            }
        }
        return "view/index";

    }




}
