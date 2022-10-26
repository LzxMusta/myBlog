package com.lzxmusta.myblog.handler;

import com.alibaba.fastjson.JSON;
import com.lzxmusta.myblog.dao.pojo.SysUser;
import com.lzxmusta.myblog.service.LoginService;
import com.lzxmusta.myblog.util.UserThreadLocal;
import com.lzxmusta.myblog.vo.ErrorCode;
import com.lzxmusta.myblog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;

    /**
     * 在执行Controller方法handler前执行
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("-------------------------------preHandle------------------------------------------");
        /**
         * 1.判断请求的接口路径  是不是HandlerMethod(Controller方法)
         * 2.判断token是否为  null  如果为null则未登录
         * 3.若token不为null  通过token登录验证   LoginService===》》checkToken
         * 4.认证成功  放行
         */
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = request.getHeader("Authorization");
        /**
         * lombok 做日志 @Slf4j
         */
        log.info("=========登录--拦截日志========request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}", requestURI);
        log.info("request method:{}", request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        if (token == null) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            //            让浏览器识别传的是json类型

            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            //            让浏览器识别传的是json类型
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }

        /**
         * 在controller中 直接获取用户的信息
         */
        UserThreadLocal.put(sysUser);
        //是登录状态，放行
        return true;
    }

    /**
     * 所有方法执行完后=====》》》
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        /**不删除  有内存泄漏风险
         * 删除UserThreadLocal  防内存泄漏
         */
        UserThreadLocal.remove();
    }

}
