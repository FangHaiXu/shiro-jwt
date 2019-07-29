package com.fhx.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

/**
 * 生成token和token效验
 * @author fhx
 * @date 2019/7/25 15:02
 */
@Slf4j
public class JWTUtil {
    //密钥值
    private static final String JWK_SOLT = "yellowcong";

    //超时时间为 8h
    public static final long TIME_LIMIT = 8 * 6000 * 1000 ;

    //密钥，base64加密
    public static final byte [] SECRET_KEY  =  getBase64Str(JWK_SOLT);

    /**
     * 添加签名
     * @param username 用户的名称
     * @return   返回加密后的token 信息
     */
    public static String sign(String username) {
        Map<String,Object> map = new HashMap<>();
        map.put("username","房海旭");
        map.put("userId","3L");
        //获取到builder
        JwtBuilder builder = Jwts.builder()
                .setIssuer("zhongjianxufeng_nicia_haha")        //签发的人
                .setSubject(username)    //所接收的人
                .setIssuedAt(new Date()) //签发时间
                .setExpiration(new Date(System.currentTimeMillis() + TIME_LIMIT)) //过期时间
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY);     //签名

        return builder.compact();
    }

    /**
     * 验证token是否有效，并获取内容
     * @param token 需要校验的token
     * @return map<>
     */
    public static Map<String,Object> validate(String token){
        try {
            //解析token信息
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)  //密钥
                    .parseClaimsJws(token)     //解密的字符串
                    .getBody();
            String username = claims.getSubject();
            Map<String,Object> objs = new HashMap<String,Object>(1);
            objs.put("username", username);
            objs.put("expiration",claims.getExpiration());
            objs.put("id",claims.getId());
            return objs;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 验证token是否有效，并获取内容
     * @param token 需要校验的token
     * @return map<>
     */
    public static String getUsername(String token){
        try {
            //解析token信息
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)  //密钥
                    .parseClaimsJws(token)     //解密的字符串
                    .getBody();
            //获取订阅者
            String username = claims.getSubject();
            return username;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取64编码的字符串
     * @param str
     * @return
     */
    private static byte []  getBase64Str(String str) {
        try {
            //通过base 64进行编码
            return Base64.getEncoder().encode(str.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
