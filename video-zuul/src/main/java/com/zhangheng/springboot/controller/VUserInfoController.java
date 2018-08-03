package com.zhangheng.springboot.controller;

import com.zhangheng.springboot.feign.UserInfoFeign;
import com.zhangheng.springboot.handler.UrlAccessDecisionManager;
import com.zhangheng.springboot.utils.YHResult;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/8/3.
 */


@RestController
@RefreshScope//刷新配置
@RequestMapping("/videoZuul/user")//窄化请求地址
@Api(value = "video-zuul", description = "用户信息")
public class VUserInfoController {

    /**
     * 注入用户相关
     *
     */
    @Autowired
    private UserInfoFeign userInfoFeign;


    //日志
    private final static Logger logger = LoggerFactory.getLogger(VUserInfoController.class);

    /**
     *前端用户登录接口
     */
      @PostMapping("/appLogin")
    public YHResult appLogin(
              @RequestParam(value = "username", required = true) String username,
              @RequestParam(value = "password", required = true) String password,
            HttpServletRequest request
      ){
          try {
              YHResult appLogin = userInfoFeign.appLogin(username, password);
              return appLogin;
          }catch (Exception e){
              logger.error(e.getMessage());
              logger.error("appLogin 异常!");
              return YHResult.build(500,"接口异常!");
          }
      }
}
