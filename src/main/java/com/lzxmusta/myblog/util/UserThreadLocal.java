package com.lzxmusta.myblog.util;

import com.lzxmusta.myblog.dao.pojo.SysUser;

public class UserThreadLocal {
    private UserThreadLocal(){}

    /**
     * 线程变量隔离  单例模式
     * 空间换时间
     */
    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }
    public static SysUser get(){
        return LOCAL.get();
    }
    public static void remove(){
        LOCAL.remove();
    }
}
