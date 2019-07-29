package com.fhx.shiro;

import javax.servlet.Filter;

import com.fhx.jwt.JWTFilter;
import com.fhx.jwt.JWTRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author fhx
 * @date 2019/7/25 15:19
 */
@Slf4j
@Configuration
public class ShiroConfig {
    public ShiroConfig() {
        log.info("进入shiroConfig");
    }

    /**
     * 未登录地址
     */
    private String loginUrl = "/login";
    /**
     * 登录成功后地址
     */
    private String successUrl = "/index";
    /**
     * 无权限地址
     */
    private String unauthorizedUrl = "/401";

    /**
     * springboot 过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        log.info("进入shiroConfig--springboot 过滤器->{filterRegistrationBean()}");
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }

    /**
     * shiro 生命周期
     *
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        log.info("进入shiroConfig--shiro 生命周期->{getLifecycleBeanPostProcessor()}");
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    /**
     * jwt 主体
     *
     * @return
     */
    @Bean
    public JWTRealm getSampleRealm() {
        log.info("进入shiroConfig--进入自定义JWTRealm主体->{getSampleRealm()}");
        return new JWTRealm();
    }

    /**
     * shiro核心管理器
     *
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {
        log.info("进入shiroConfig--进入shiro核心管理器->{getDefaultWebSecurityManager()}");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设定realm 用于权限的认证
        securityManager.setRealm(getSampleRealm());
        //用于securityManager 的缓存
        securityManager.setCacheManager(new MemoryConstrainedCacheManager());

        //关闭shiro自带的session
        //参考文档
        //http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator evaluator = new DefaultSessionStorageEvaluator();
        evaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(evaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     * 注解访问授权动态拦截，不然不会执行doGetAuthenticationInfo
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        log.info("进入shiroConfig--授权动态拦截->{authorizationAttributeSourceAdvisor()}");
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * shiro的过滤器
     *
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        log.info("进入shiroConfig--shiro过滤器->{getShiroFilterFactoryBean()}");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设定认证管理的Manager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<>(1);
        filterMap.put("jwt", new JWTFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        //添加授权
        Map<String, String> chainMap = new LinkedHashMap<>();
        // 将请求交给shiro处理
        chainMap.put("/user/login","anon");
        // 将所有请求交给jwt处理
        chainMap.put("/**", "jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(chainMap);
        return shiroFilterFactoryBean;
    }
}
