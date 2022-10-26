package com.lzxmusta.myblog.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lzxmusta.myblog.dao.pojo.SysUser;
import com.lzxmusta.myblog.service.LoginService;
import com.lzxmusta.myblog.service.SysUserService;
import com.lzxmusta.myblog.util.JWTUtils;
import com.lzxmusta.myblog.vo.ErrorCode;
import com.lzxmusta.myblog.vo.Result;
import com.lzxmusta.myblog.vo.params.LoginParams;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    //    加密盐slat
    private static final String slat = "mszlu!@#";

    @Override
    public Result login(LoginParams loginParams) {
        /**
         * 1.检查参数
         * 2.根据用户名密码查询是否存在用户
         * 没有  失败
         * 有    用jwt生成token 返回前端
         * 5.token 放入redis   token:user信息  设置过期时间（登录认证时 先认证token字符串是否合法，再去redis认证是否存在）
         */

        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
//            System.out.println("====================StringUtils.isBlank(account) || StringUtils.isBlank(password)==================================");

            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
//        防止彩虹表破解密码 添加加密盐slat
        password = DigestUtils.md5Hex(password + slat);
        SysUser sysUser = sysUserService.findUser(account, password);
        if (sysUser == null) {
//            System.out.println("====================sysUser == null=====================================");
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());

        }
        String token = JWTUtils.createToken((sysUser.getId()));
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }

    /**
     * token验证登录
     * @param token
     * @return
     */
    @Override
    public SysUser checkToken(String token) {
        /**
         * 1.token 合法校验
         *    是否为空  解析是否成功  redis是否存在
         * 2.失败   返回错误
         * 3.如果成功,返回结果  LoginUserVo
         */
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null) {
            return null;
        }
        String s = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(s)) {
            return null;
        }
        SysUser sysUser = JSON.parseObject(s, SysUser.class);

        return sysUser;
    }

    /**
     * 退出登录
     *
     * @param token
     * @return
     */
    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParams loginParams) {
        /**
         * 1.检查参数
         * 2.根据用户名查询是否存在用户
         * 有    返回已经被注册
         * 没有  注册用户
         * 生成token
         * 5.token 放入redis   token:user信息  设置过期时间
         * 6.加上事务,出现问题  注册的用户回滚
         */

        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        String nickname = loginParams.getNickname();

        if (StringUtils.isBlank(account) || StringUtils.isBlank(password) || StringUtils.isBlank(nickname)) {
//            System.out.println("===================StringUtils.isBlank(account) || StringUtils.isBlank(password)||StringUtils.isBlank(nickname)=================================");

            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser = sysUserService.findUserByAccount(account);
        if (sysUser != null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_EXIST.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        //        防止彩虹表破解密码 添加加密盐slat
        sysUser.setPassword(DigestUtils.md5Hex(password + slat));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("http://rjud6wr5p.hn-bkt.clouddn.com/u=236085137,1979895699&fm=253&fmt=auto&app=138&f=JPEG.webp");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        this.sysUserService.saveUser(sysUser);
        //token
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }
}
