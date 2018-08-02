package com.zhangheng.springboot.handler;

import com.zhangheng.springboot.custom.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/7/26.
 */

@Component
public class UrlAccessDecisionManager implements AccessDecisionManager {

    private final static Logger logger = LoggerFactory.getLogger(UrlAccessDecisionManager.class);
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        Iterator<ConfigAttribute> iterator = collection.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute ca = iterator.next();
            //当前请求需要的权限
            String needRole = ca.getAttribute();
            logger.info("所需要的权限:"+needRole);
            if ("ROLE_LOGIN".equals(needRole)) {
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new BadCredentialsException("未登录");
                }
                else return;
            } //当前用户所具有的权限
//            UserInfo userInfo = new UserInfo();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            logger.info("当前权限:"+authentication.getAuthorities());
            logger.info("输出："+authentication.getPrincipal());
            for (GrantedAuthority authority : authorities) {
                logger.info("当前权限:"+authority.getAuthority());
                if (authority.getAuthority().equals(needRole)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
