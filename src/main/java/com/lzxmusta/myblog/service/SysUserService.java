package com.lzxmusta.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzxmusta.myblog.dao.mapper.SysUserMapper;
import com.lzxmusta.myblog.dao.pojo.SysUser;
import com.lzxmusta.myblog.vo.Result;
import com.lzxmusta.myblog.vo.UserVo;

public interface SysUserService extends IService<SysUser> {
    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    /**
     * 根据token 查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据用户名查询用户
     * @param account
     * @return
     */

    SysUser findUserByAccount(String account);

    /**
     * 保存用户
     * @param sysUser
     */
    void saveUser(SysUser sysUser);

    UserVo findUserVoById(Long authorId);

}
