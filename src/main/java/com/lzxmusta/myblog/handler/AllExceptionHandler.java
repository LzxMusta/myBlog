package com.lzxmusta.myblog.handler;

import com.lzxmusta.myblog.vo.ErrorCode;
import com.lzxmusta.myblog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/*
*对@Controller中方法进行拦截
* 统一异常处理 AOP的实现
* */
@ControllerAdvice
public class AllExceptionHandler {
//    处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result doException(Exception ex){
        ex.printStackTrace();
        return Result.fail(ErrorCode.MYBLOG_SYSTERM_ERROR.getCode(),ErrorCode.MYBLOG_SYSTERM_ERROR.getMsg());
    }
}
