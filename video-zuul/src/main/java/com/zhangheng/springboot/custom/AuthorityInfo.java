package com.zhangheng.springboot.custom;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/7/20.
 */
public class AuthorityInfo implements GrantedAuthority {
    private static final long serialVersionUID = -175781100474818800L;

    /**
     * 权限CODE
     */
    private String authority;

    public AuthorityInfo(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}
