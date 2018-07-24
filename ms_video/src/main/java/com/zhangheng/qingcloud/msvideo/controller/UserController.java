package com.zhangheng.qingcloud.msvideo.controller;

import com.zhangheng.qingcloud.msvideo.service.UserInfoService;
import com.zhangheng.qingcloud.msvideo.util.YHResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/7/24.
 */

@RestController
@RequestMapping("/msvideo/user")//窄化请求地址
@Api(value = "msvideo", description = "用户信息")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    private static Logger log = Logger.getLogger(UserController.class);


    /**
     * 根据用户名查找用户信息
     */
    @ApiOperation(value = "CAS用户信息", response = String.class, notes = "用户信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", required = true, name = "username", dataType = "String", value = "用户名"),

    })
    @RequestMapping(value = "/getUserInfoByUsername", method = RequestMethod.POST)
    public YHResult getUserInfoByUsername(
            @RequestParam(value = "username", required = true) String username,
            HttpServletRequest request
    ){


        Map<String, String> param = new HashMap<String, String>();
        param.put("username",username);
        try {
            YHResult yhResult = userInfoService.getUserInfoByUsername(param);
            log.info("getUserInfoByUsername接口返回的信息:"+yhResult.toString());
            return yhResult;
        }catch (Exception e){
            log.info(e.getMessage());
            log.info("getUserInfoByUsername接口 异常!");
            return null;
        }
    }
}
