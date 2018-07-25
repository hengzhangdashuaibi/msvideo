package com.zhangheng.springboot.custom;


import com.zhangheng.springboot.feign.UserInfoFeign;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/7/20.
 */
public class CustomUserDetailsService

        /*
	//实现UserDetailsService接口，实现loadUserByUsername方法
	implements UserDetailsService {
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("当前的用户名是："+username);
		//这里我为了方便，就直接返回一个用户信息，实际当中这里修改为查询数据库或者调用服务什么的来获取用户信息
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername("admin");
		userInfo.setName("admin");
		Set<AuthorityInfo> authorities = new HashSet<AuthorityInfo>();
		AuthorityInfo authorityInfo = new AuthorityInfo("TEST");
		authorities.add(authorityInfo);
		userInfo.setAuthorities(authorities);
		return userInfo;
	}*/



        //实现AuthenticationUserDetailsService，实现loadUserDetails方法
        implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {


    //注入feign
    @Autowired
    private UserInfoFeign userInfoFeign;

    private final static Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Override
    public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {
        logger.info("当前的用户名是："+token.getName());

        String userInfoByUsername = userInfoFeign.getUserInfoByUsername(token.getName());
        //用户的角色权限
        String rule=null;
        //转json
        JSONObject jsonObject = JSONObject.parseObject(userInfoByUsername);

        if(null!=jsonObject){

            if(jsonObject.getString("status").equals("200")){
                JSONObject data = jsonObject.getJSONObject("data");
                if(data!=null){
                    rule=data.getString("role");
                    logger.info("用户的角色权限:"+rule);
                }

            }

        }

        logger.info("接口返回："+userInfoByUsername);

		/*这里我为了方便，就直接返回一个用户信息，实际当中这里修改为查询数据库或者调用服务什么的来获取用户信息*/
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(token.getName());
        userInfo.setName(token.getName());
        Set<AuthorityInfo> authorities = new HashSet<AuthorityInfo>();
        AuthorityInfo authorityInfo = new AuthorityInfo(rule);
        authorities.add(authorityInfo);
        userInfo.setAuthorities(authorities);
        userInfo.setAccountNonLocked(true);
        userInfo.setAccountNonExpired(true);
        userInfo.setCredentialsNonExpired(true);
        return userInfo;
    }

}
