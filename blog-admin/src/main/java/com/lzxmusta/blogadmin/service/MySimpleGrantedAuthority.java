package com.lzxmusta.blogadmin.service;

import org.springframework.security.core.GrantedAuthority;

/**
 * @ClassName MySimpleGrantedAuthority
 * @Description TODO
 * @Author lzxmusta
 * @Date 2022/10/17 22:57
 * @Version 1.0
 **/
public class MySimpleGrantedAuthority implements GrantedAuthority {
    private String authority;
    private String path;

    public MySimpleGrantedAuthority(){}

    public MySimpleGrantedAuthority(String authority){
        this.authority = authority;
    }

    public MySimpleGrantedAuthority(String authority,String path){
        this.authority = authority;
        this.path = path;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public String getPath() {
        return path;
    }
}
