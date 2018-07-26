package com.zhangheng.springboot.custom;


import com.alibaba.fastjson.JSONArray;
import com.zhangheng.springboot.feign.UserInfoFeign;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/7/20.
 */
public class CustomUserDetailsService
        implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {
      //实现AuthenticationUserDetailsService，实现loadUserDetails方法//实现UserDetailsService接口，实现loadUserByUsername方法

    //注入feign
    @Autowired
    private UserInfoFeign userInfoFeign;

    private final static Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        logger.info("当前的用户名是:"+username);
//
//        String userInfoByUsername = userInfoFeign.getUserInfoByUsername(username);
//        UserInfo userInfo = new UserInfo();
//        //转json
//        JSONObject jsonObject = JSONObject.parseObject(userInfoByUsername);
//        if(null!=jsonObject){
//            JSONObject data = jsonObject.getJSONObject("data");
//            if(data==null){
//                throw new UsernameNotFoundException("用户名不对!");
//            }else{
//                //调用服务来实现验证用户名是否存在
//                userInfo.setUsername(data.getString("username"));
//                //根据用户名获取用户下面的所有权限
//
//                //当前用户所具有的权限
////                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//                String userAndRoleInfoByUsername = userInfoFeign.getUserAndRoleInfoByUsername(data.getString("username"));
//                JSONObject parseObject = JSONObject.parseObject(userAndRoleInfoByUsername);
//                if(parseObject!=null){
//                    JSONArray jsonArray = parseObject.getJSONArray("data");
//                    if(jsonArray.size()>0&&jsonArray!=null){
//                        JSONObject object;
//                        Set<AuthorityInfo> authorities = new HashSet<AuthorityInfo>();
//
//                        for (int i = 0; i <jsonArray.size() ; i++) {
//                            object = jsonArray.getJSONObject(i);
//                            AuthorityInfo authorityInfo = new AuthorityInfo(object.getString("name"));
//                            authorities.add(authorityInfo);
//                        }
//                        userInfo.setAuthorities(authorities);
//                        userInfo.setAccountNonLocked(true);
//                        userInfo.setAccountNonExpired(true);
//                        userInfo.setCredentialsNonExpired(true);
//                    }
//                }
//            }
//        }
//
//        return userInfo;
//    }

    @Override
    public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {
        logger.info("当前的用户名是："+token.getName());
        String userInfoByUsername = userInfoFeign.getUserInfoByUsername(token.getName());
        UserInfo userInfo = new UserInfo();
        //转json
        JSONObject jsonObject = JSONObject.parseObject(userInfoByUsername);
        if(null!=jsonObject){
            JSONObject data = jsonObject.getJSONObject("data");
            if(data==null){
                throw new UsernameNotFoundException("用户名不对!");
            }else{
                //调用服务来实现验证用户名是否存在
                userInfo.setUsername(data.getString("username"));
                //根据用户名获取用户下面的所有权限

                //当前用户所具有的权限
//                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                String userAndRoleInfoByUsername = userInfoFeign.getUserAndRoleInfoByUsername(data.getString("username"));
                JSONObject parseObject = JSONObject.parseObject(userAndRoleInfoByUsername);
                if(parseObject!=null){
                    JSONArray jsonArray = parseObject.getJSONArray("data");
                    if(jsonArray.size()>0 && jsonArray!=null){
                        JSONObject object;
                        Set<AuthorityInfo> authorities = new HashSet<AuthorityInfo>();

                        for (int i = 0; i <jsonArray.size() ; i++) {
                            object = jsonArray.getJSONObject(i);
                            AuthorityInfo authorityInfo = new AuthorityInfo(object.getString("name"));
                            authorities.add(authorityInfo);
                        }
                        userInfo.setAuthorities(authorities);
                        userInfo.setAccountNonLocked(true);
                        userInfo.setAccountNonExpired(true);
                        userInfo.setCredentialsNonExpired(true);
                    }
                }
            }
        }

        return userInfo;
    }

}
