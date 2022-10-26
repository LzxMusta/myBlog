package com.lzxmusta.myblog.common.aop;

import java.lang.annotation.*;

/**
 * @Author: Lzxmusta
 * @Date: 2022-10-16-1:28
 * @Description:通过aop实现日志打印
 */
//type 代表可以放在类上   method代表可以放方法上
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";

    String operator() default "";
}
