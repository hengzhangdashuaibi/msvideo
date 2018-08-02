package com.zhangheng.springboot.handler;

import com.zhangheng.springboot.custom.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/7/26.
 *
 * 路径过滤和角色权限控制
 */

@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{


    AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final static Logger logger = LoggerFactory.getLogger(UrlFilterInvocationSecurityMetadataSource.class);

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        //对项目自带的路径不惊醒过滤拦截
        if(requestUrl.equals("/actuator/health")||("/info").equals(requestUrl)){
            return null;
        }

        logger.info("获取请求地址:"+requestUrl);

        //与数据库的路径进行匹配
        //没有匹配都需要进行登录验证


        return SecurityConfig.createList("TES");

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
