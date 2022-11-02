package com.lzxmusta.myblog.controller;

import com.lzxmusta.myblog.service.LoginService;
import com.lzxmusta.myblog.vo.Result;
import com.lzxmusta.myblog.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@CrossOrigin
public class RegisterController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result registerUser(@RequestBody LoginParams loginParams) {
//        sso 单点登录   便于后期将登录注册功能提出(做单独服务时,可以独立提供接口服务)
        return loginService.register(loginParams);
    }
}
