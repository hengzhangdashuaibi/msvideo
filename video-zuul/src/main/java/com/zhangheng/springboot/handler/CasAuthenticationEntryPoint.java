package com.zhangheng.springboot.handler;

import com.alibaba.fastjson.JSONObject;
import com.zhangheng.springboot.properties.CasProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/8/7.
 *
 * 自定义登录跳转逻辑
 */
@Component
public class CasAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private CasProperties casProperties;

    @Override
    public  void commence(HttpServletRequest servletRequest, HttpServletResponse response, org.springframework.security.core.AuthenticationException authenticationException) throws IOException, ServletException {
        if(isAjaxRequest(servletRequest)){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authenticationException.getMessage());
        }else{
//            servletRequest.
//            response.sendRedirect("/login");
            PrintWriter out = response.getWriter();
            out.write(getJson());
//            out.write("{code:301,redirect:"+casProperties.getCasServerLoginUrl()+"}");
            out.flush();
            out.close();
        }
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
    }


    @ResponseBody
    public String getJson(){
//        JSONArray jsonArray = new JSONArray();
        JSONObject jsonDate = new JSONObject();
//        for(int i = 0 ; i < list.size() ; i++){
//            Object object = list.get(i);
//            jsonArray.put(JsonMapper.toJsonString(object));
//        }
        jsonDate.put("code", 301);
        jsonDate.put("redirect", casProperties.getCasServerLoginUrl());
        return jsonDate.toString();
    }

}
