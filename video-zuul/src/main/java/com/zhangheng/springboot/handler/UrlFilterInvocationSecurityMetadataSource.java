package com.zhangheng.springboot.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhangheng.springboot.custom.CustomUserDetailsService;
import com.zhangheng.springboot.feign.UserInfoFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserInfoFeign userInfoFeign;

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
        //后台api接口和角色的关系
        //获取后台所有的api
        String allApi = userInfoFeign.getAllApi();
        //转json
        JSONObject parseObject = JSONObject.parseObject(allApi);
        JSONArray jsonArray = parseObject.getJSONArray("data");
        if(jsonArray.size()>0 && jsonArray!=null){
            JSONObject jsonObject;
            //遍历菜单所拥有的角色
            for (int i = 0; i <jsonArray.size() ; i++) {
                jsonObject = jsonArray.getJSONObject(i);
                //apiid
                jsonObject.getInteger("id");
                //apipath
                jsonObject.getString("apipath");
                //apiname
                jsonObject.getString("apiname");
                //正则匹配url
                if(antPathMatcher.match(jsonObject.getString("apipath"),requestUrl)){
                    //再通过apiid去查询此接口拥有多少访问的角色
                    String apiRoleByApiId = userInfoFeign.getApiRoleByApiId(jsonObject.getInteger("id"));
                    JSONObject apiRoleByApiIdObject = JSONObject.parseObject(apiRoleByApiId);
                    JSONArray apiRoleByApiIdJSONArray = apiRoleByApiIdObject.getJSONArray("data");
                    //获取角色
                    String[] values = new String[apiRoleByApiIdJSONArray.size()];
                    if(apiRoleByApiIdJSONArray.size()>0 && apiRoleByApiIdJSONArray!=null){
//                        JSONObject apiRoleByApiIdJSONObject;
                        for (int j = 0; j <apiRoleByApiIdJSONArray.size() ; j++) {
//                            apiRoleByApiIdJSONObject = apiRoleByApiIdJSONArray.getJSONObject(j);
                            values[j]=apiRoleByApiIdJSONArray.getJSONObject(j).getString("name");

                        }
                    }
                    //将所拥有的访问接口角色返回
                    return SecurityConfig.createList(values);
                }
            }
        }
        //没有匹配都需要进行登录验证
//        return SecurityConfig.createList("ROLE_LOGIN");
        //没有匹配上的不进行匹配验证
        return null;
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
