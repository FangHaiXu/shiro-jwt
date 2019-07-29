package com.fhx.jwt;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * 存放token 便于效验
 * @author fhx
 * @date 2019/7/25 15:06
 */
@Data
@Slf4j
public class JWTToken implements AuthenticationToken {
    //jwt的token密钥
    private String token;

    private String password;

    public String getToken() {
        return token;
    }
    public JWTToken(String token) {
        super();
        this.token = token;
    }

    public JWTToken(String token,String password) {
        super();
        this.token = token;
        this.password = password;
    }

    @Override
    public Object getCredentials() {
        log.info("获得token");
        // TODO Auto-generated method stub
        return token;
    }

    @Override
    public Object getPrincipal() {
        log.info("获得用户");
        // TODO Auto-generated method stub
        return token;
    }
}
