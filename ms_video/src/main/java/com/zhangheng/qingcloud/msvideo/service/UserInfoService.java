package com.zhangheng.qingcloud.msvideo.service;

import com.zhangheng.qingcloud.msvideo.util.YHResult;

import java.util.Map;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/7/24.
 */
public interface UserInfoService {

    /**
     * 根据用户名查询用户信息
     */
    YHResult getUserInfoByUsername(Map<String, String> params);

}
