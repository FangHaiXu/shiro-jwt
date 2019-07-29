package com.fhx.web.controller.common;

import com.fhx.jwt.JWTToken;
import com.fhx.jwt.JWTUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 页面路由
 * @author fhx
 * @date 2019/7/22 14:27
 */
@Controller
public class MappingController {

    /**
     * 登录
     * @return 页面
     */
    @GetMapping("login")
    public String login(){
        return "login";
    }

    /**
     * 首页
     * @return 页面
     */
    @GetMapping("index")
    public String index(HttpServletRequest request,HttpServletResponse response){
        return "index";
    }

    @GetMapping("tables")
    public String tables(){
        return "tables";
    }

    /**
     * 用户管理
     * @return 页面
     */
    @GetMapping("system/user")
    public String systemUser(HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        System.err.println(subject.getPrincipal());
        String authorization = request.getHeader("Authorization");
        String user = JWTUtil.getUsername(authorization);
        System.err.println("shiro:"+user);
        return "system/user";
    }

    /**
     * 注册
     * @return 页面
     */
    @GetMapping("registered")
    public String registered(){
        return "registered";
    }
}
