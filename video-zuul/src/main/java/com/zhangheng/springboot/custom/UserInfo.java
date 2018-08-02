package com.zhangheng.springboot.custom;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/7/20.
 */
public class UserInfo implements UserDetails {
    private static final long serialVersionUID = -1041327031937199938L;

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名称
     */
//    private String name;

    /**
     * 登录名称
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     *用户头像
     */
    private String userface;

    /**
     * 用户所拥有的角色
     */
    private List<Role> roles;

    private boolean isAccountNonExpired = true;

    private boolean isAccountNonLocked = true;

    private boolean isCredentialsNonExpired = true;

    private boolean isEnabled = true;

//    private Set<AuthorityInfo> authorities = new HashSet<>();
   List<GrantedAuthority> authorities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

//    public Set<AuthorityInfo> getAuthorities() {
//        return authorities;
//    }
//
//    public void setAuthorities(Set<AuthorityInfo> authorities) {
//        this.authorities = authorities;
//    }

    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    /**
     * 实现单用户多角色
     * @return
     */
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (Role role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//        return authorities;
//    }

}

