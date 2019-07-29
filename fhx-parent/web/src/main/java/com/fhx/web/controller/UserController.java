package com.fhx.web.controller;

import com.auth0.jwt.JWT;
import com.fhx.common.bean.po.User;
import com.fhx.common.bean.ro.RoUser;
import com.fhx.common.bean.vo.VoUser;
import com.fhx.common.response.RestResponse;
import com.fhx.jwt.JWTToken;
import com.fhx.jwt.JWTUtil;
import com.fhx.userApi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Random;

/**
 * @author fhx
 * @date 2019/7/19 17:02
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("saveUser")
    public RestResponse saveUser(@Valid VoUser voUser) {
        Integer salt = new Random().nextInt(10000);
        //SimpleHash md5 = new SimpleHash("MD5", voUser.getPassword(), Integer.toString(salt), 1);
        User user = new User();
        user.setSalt(salt.toString());
        BeanUtils.copyProperties(voUser, user);
        userService.save(user);
        return RestResponse.success();
    }

    @PostMapping("login")
    @CrossOrigin
    public RestResponse login(@NotBlank(message = "请输入用户名") String username,
                              @NotBlank(message = "请输入密码") String password,
                              Boolean rememberMe, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        /*UsernamePasswordToken passwordToken = new UsernamePasswordToken(username,password);*/

        //passwordToken.setRememberMe(rememberMe);
        //检查用户信息
        String tokenStr = JWTUtil.sign(username);

        //用户登录
        JWTToken token = new JWTToken(tokenStr, password);

        //登录用户
        subject.login(token);

        return RestResponse.success(token.getToken());
    }

    @PostMapping("findTest")
    public RestResponse findTest() throws JSONException {
        return RestResponse.success("我是权限测试通过");
    }

}
