package com.lzxmusta.myblog.controller;

import com.lzxmusta.myblog.dao.pojo.SysUser;
import com.lzxmusta.myblog.util.UserThreadLocal;
import com.lzxmusta.myblog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @RequestMapping
    public Result getest() {
        //        SysUser
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}
