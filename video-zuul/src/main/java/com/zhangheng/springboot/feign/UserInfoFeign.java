package com.zhangheng.springboot.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/7/24.
 *
 * 调用ms_video的微服务的api
 */
@FeignClient(value = "msvideo", url = "localhost:8763")
public interface UserInfoFeign {

    @RequestMapping(value = "/msvideo/user/getUserInfoByUsername",method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    String getUserInfoByUsername(@RequestParam("username") String username);
}
