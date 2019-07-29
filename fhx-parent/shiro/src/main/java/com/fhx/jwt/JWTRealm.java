package com.fhx.jwt;

import com.fhx.common.bean.po.User;
import com.fhx.userApi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.authenticator.Constants;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashSet;
import java.util.Set;

/**
 * @author fhx
 * @date 2019/7/25 17:11
 */
@Slf4j
public class JWTRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    /**
     * 必须重写此方法，不然shiro会报错
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        log.info("进入jwt自定义主体--shiro supports方法");
        return token instanceof JWTToken;
    }

    /**
     * 用户授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection paramPrincipalCollection) {
        log.info("进入用户授权");
        //获取jwt的用户名和密码信息
        String username = JWTUtil.getUsername(paramPrincipalCollection.toString());
        System.out.println(username);

        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
        // 根据用户ID查询角色（role），放入到Authorization里。
        Set<String> roles = new HashSet<String>(); // 添加用户角色
        roles.add("administrator");
        info.setRoles(roles);
        // 根据用户ID查询权限（permission），放入到Authorization里。
        Set<String> permissions = new HashSet<String>(); // 添加权限
        permissions.add("/role/**");
        info.setStringPermissions(permissions);
        return info;
    }

    /**
     * 认证，用户登录
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken  paramAuthenticationToken) throws AuthenticationException {
        log.info("进入用户登录认证");
        JWTToken jwtToken = (JWTToken) paramAuthenticationToken;
        String token = jwtToken.getToken();
        //获取token 信息
        String username = JWTUtil.getUsername(token);
        if(username == null || "".equals(username.trim())) {
            throw new AuthenticationException("用户名为空");
        }
        //判断用户 是否存在的情况
        User user = this.userService.findByUserName(username);

        // 用户不会空
        if (user != null) {
            if (!user.getPassword().equals(jwtToken.getPassword())){
                throw new IncorrectCredentialsException("密码错误");
            }
            return new SimpleAuthenticationInfo(token,token,"realm");
        } else {
            throw new UnknownAccountException("用户不存在");
        }
    }
}
