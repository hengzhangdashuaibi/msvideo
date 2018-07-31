package com.zhangheng.qingcloud.msvideo.service.Impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zhangheng.qingcloud.msvideo.mapper.UserMapper;
import com.zhangheng.qingcloud.msvideo.service.UserInfoService;
import com.zhangheng.qingcloud.msvideo.util.YHResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/7/24.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private static Logger log = Logger.getLogger(UserInfoServiceImpl.class);


    //注入mapper
    @Autowired
    private UserMapper userMapper;

    @Override
    @HystrixCommand
    public YHResult getUserInfoByUsername(Map<String, String> params) {

        Map<String, Object> userInfoByUsername = userMapper.getUserInfoByUsername(params.get("username"));
        return YHResult.ok(userInfoByUsername);
    }

    /**
     * 根据用户查询用户所拥有的角色信息
     * @param params
     * @return
     */
    @Override
    @HystrixCommand
    public YHResult getUserAndRoleInfoByUsername(Map<String, String> params) {

        List<Map<String,Object>> userAndRoleInfoByUsername = userMapper.getUserAndRoleInfoByUsername(params.get("username"));

        return YHResult.ok(userAndRoleInfoByUsername);
    }
}
