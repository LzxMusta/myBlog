package com.lzxmusta.myblog.common.cache;

import java.lang.annotation.*;

/**
 * @ClassName Cache
 * @Description TODO 通过AOP实现的缓存注解
 * @Author lzxmusta
 * @Date 2022/10/17 20:34
 * @Version 1.0
 **/
//type 代表可以放在类上   method代表可以放方法上
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {


    long expire() default 1 * 60 * 1000;

    String name() default "";

}
