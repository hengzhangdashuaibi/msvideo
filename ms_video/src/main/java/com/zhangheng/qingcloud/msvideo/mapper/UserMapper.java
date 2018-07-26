package com.zhangheng.qingcloud.msvideo.mapper;

import com.zhangheng.qingcloud.msvideo.entity.User;
import com.zhangheng.qingcloud.msvideo.util.YHMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/7/24.
 */

@Repository
@Mapper
public interface UserMapper extends YHMapper<User>{

    //根据用户名查询用户信息(包括角色)
    Map<String,Object> getUserInfoByUsername(@Param("username") String username);

   //根据用户名查询用户所拥有的角色
    List<Map<String,Object>> getUserAndRoleInfoByUsername(@Param("username") String username);

    //根据用户名查询用户信息
//     @Select("SELECT * FROM User WHERE username = #{username}")

}
