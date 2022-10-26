package com.lzxmusta.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzxmusta.myblog.dao.mapper.SysUserMapper;
import com.lzxmusta.myblog.dao.pojo.SysUser;
import com.lzxmusta.myblog.service.LoginService;
import com.lzxmusta.myblog.service.SysUserService;
import com.lzxmusta.myblog.vo.ErrorCode;
import com.lzxmusta.myblog.vo.LoginUserVo;
import com.lzxmusta.myblog.vo.Result;
import com.lzxmusta.myblog.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private LoginService loginService;
    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null) {
            SysUser sysUser1 = new SysUser();
            sysUser1.setNickname("疑惑星球");
            return sysUser1;
        }
        return sysUser;
    }

    /**
     * 用户登录
     * @param account
     * @param password
     * @return
     */
    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1 ");
//        System.out.println(sysUserMapper.selectOne(queryWrapper)+"---------------------------------findUser------------------------------");

        return sysUserMapper.selectOne(queryWrapper);

    }

    /**
     * 通过token获取用户信息
     * @param token
     * @return
     */
    @Override
    public Result findUserByToken(String token) {
        /**
         * 1、token合法性校验
         * 是否为空 ，解析是否成功，redis是否存在
         * 2、如果校验失败，返回错误
         *3、如果成功，返回对应结果 LoginUserVo
         */
        //去loginservice中去校验token
        SysUser sysUser=loginService.checkToken(token);
        if (sysUser == null){
            return Result.fail(ErrorCode.TOKEN_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setNickname(sysUser.getNickname());
        return Result.success(loginUserVo);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void saveUser(SysUser sysUser) {
        //默认生成的id 是分布式id 采用了雪花算法
        this.sysUserMapper.insert(sysUser);

    }

    /**
     * 通过id查用户信息
     * @param id
     * @return
     */
    @Override
    public UserVo findUserVoById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("http://rjud6wr5p.hn-bkt.clouddn.com/u=236085137,1979895699&fm=253&fmt=auto&app=138&f=JPEG.webp");
            sysUser.setNickname("星球人");
        }
        UserVo userVo = new UserVo();
        userVo.setAvatar(sysUser.getAvatar());
        userVo.setNickname(sysUser.getNickname());
        userVo.setId(sysUser.getId());
        return userVo;
    }


}
