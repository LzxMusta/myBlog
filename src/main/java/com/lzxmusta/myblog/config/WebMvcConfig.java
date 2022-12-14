package com.lzxmusta.myblog.config;

import com.lzxmusta.myblog.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        设置跨域
        registry.addMapping("/**").allowedOrigins("http://localhost:8080");


        //通过ip访问不能走443端口
        //如果配置了自定义拦截器，这种跨域配置会失效，所以采用第二种
        //跨域配置一
//      registry.addMapping("/**").allowedOrigins("http://81.71.87.241:8080","http://81.71.87.241:80","http://81.71.87.241:8888")
        //跨域配置二
        registry.addMapping("/**")
//                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
//++++++++++++++++++++++++++++这里需要记笔记++++++++++++++++++++++++++++++++
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //测试拦截test接口，添加拦截接口
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test")
                .addPathPatterns("/comments/create/change")
                .addPathPatterns("/articles/publish");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:dist/index.html");


    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //        addResourceHandler是指你想在url请求的路径
        // addResourceLocations是图片存放的真实路
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/dist/static/");
    }
}
