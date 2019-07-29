package com.fhx.jwt;

import com.fhx.common.response.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * jwt过滤器
 *
 * @author fhx
 * @date 2019/7/25 15:09
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {
    /**
     * 登录标识
     */
    private static String authorization = "Authorization";

    public JWTFilter() {
        log.info("进入jwt过滤器");
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        log.info("jwt过滤器-->1. 进入跨域(preHandle)");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 请求是否已经登录(携带token)
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        log.info("jwt过滤器-->1. 进入判断用户是否登录(isLoginAttempt)");
        HttpServletRequest req = (HttpServletRequest) request;
        String header = req.getHeader(authorization);
        return header != null;
    }


    /**
     * 是否允许访问
     *
     * @param request     请求
     * @param response    响应
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        log.info("jwt过滤器-->2. 进入判断用户是否允许访问资源(isAccessAllowed)");
        if (isLoginAttempt(request,response)){
            // 等于true 说明header中有token,判断token的 正确性
            HttpServletRequest req = (HttpServletRequest)request;
            String username = JWTUtil.getUsername(req.getHeader(authorization));
            Map<String, Object> validate = JWTUtil.validate(authorization);
            System.err.println(validate.toString());
            if (username != null){
                // 不等于null 说明token是正确的
                return true;
            }else {
                // token等于null说明不正确
                response401(request,response,"token无效请重新登录");
                return false;
            }
        }else {
            // 无token
            response401(request,response,"请登录后在进行操作");
            return false;
        }
    }

    /**
     * 401非法请求
     * @param req
     * @param resp
     */
    private void response401(ServletRequest req, ServletResponse resp,String msg) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = httpServletResponse.getWriter();
            RestResponse error = RestResponse.error(msg, null, 100);
            out.append(JSONObject.quote(error.toString()));
        } catch (IOException e) {
            log.error("返回Response信息出现IOException异常:" + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
