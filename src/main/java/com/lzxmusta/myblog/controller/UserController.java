package com.lzxmusta.myblog.controller;

import com.lzxmusta.myblog.service.SysUserService;
import com.lzxmusta.myblog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
//@CrossOrigin
public class UserController {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return  sysUserService.findUserByToken(token);
    }
}
