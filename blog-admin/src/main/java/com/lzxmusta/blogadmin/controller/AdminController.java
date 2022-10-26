package com.lzxmusta.blogadmin.controller;

import com.lzxmusta.blogadmin.pojo.Permission;
import com.lzxmusta.blogadmin.params.PageParam;
import com.lzxmusta.blogadmin.service.PermissionService;
import com.lzxmusta.blogadmin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName AdminController
 * @Description TODO 权限管理
 * @Author lzxmusta
 * @Date 2022/10/17 22:20
 * @Version 1.0
 **/
@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("permission/permissionList")
    public Result permissionList(@RequestBody PageParam pageParam){
        return permissionService.listPermission(pageParam);
    }

    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission){
        return permissionService.add(permission);
    }

    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }

    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }
}
