package com.zhangheng.springboot.handler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/7/25.
 *
 * 自定义权限不够的实现类
 */

@Component
public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler{

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        PrintWriter out = httpServletResponse.getWriter();
        out.write(getJson());
//        out.write("data:{\"status\":\"error\",\"msg\":\"权限不足，请联系管理员!\"}");
        out.flush();
        out.close();
    }

    @ResponseBody
    public String getJson(){
//        JSONArray jsonArray = new JSONArray();
        JSONObject jsonDate = new JSONObject();
//        for(int i = 0 ; i < list.size() ; i++){
//            Object object = list.get(i);
//            jsonArray.put(JsonMapper.toJsonString(object));
//        }
        jsonDate.put("status", "error");
        jsonDate.put("msg","权限不足，请联系管理员!");
        return jsonDate.toString();
    }
}
