package com.zhangheng.springboot.custom;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/7/26.
 * 用户角色表
 */
public class Role {

    private Long id;

    //角色名称
    private String name;

    //角色中文名称
    private String nameZh;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

}
