package com.lzxmusta.myblog.service;

import com.lzxmusta.myblog.dao.pojo.SysUser;
import com.lzxmusta.myblog.vo.Result;
import com.lzxmusta.myblog.vo.params.LoginParams;

public interface LoginService {


    /**
     * 登录功能
     * @param loginParams
     * @return
     */
    Result login(LoginParams loginParams);

    SysUser checkToken(String token);
//退出登录
    Result logout(String token);

    /**
     * 注册
     * @param loginParams
     * @return
     */
    Result register(LoginParams loginParams);
}
