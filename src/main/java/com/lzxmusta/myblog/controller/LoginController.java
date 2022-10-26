package com.lzxmusta.myblog.controller;

import com.lzxmusta.myblog.service.LoginService;
import com.lzxmusta.myblog.service.SysUserService;
import com.lzxmusta.myblog.vo.Result;
import com.lzxmusta.myblog.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
//@CrossOrigin
public class LoginController {
    /*
     * 不选择用SysUserService直接做登录业务*/
//    @Autowired
//    private SysUserService sysUserService;
    /*
     * 创建登录的service*/
    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParams loginParams) {

        return loginService.login(loginParams);
    }
}
