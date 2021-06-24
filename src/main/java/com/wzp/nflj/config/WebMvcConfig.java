package com.wzp.nflj.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: zp.wei
 * @DATE: 2020/9/8 15:34
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 全局配置，解决跨域问题
     * /** 表示本应用的所有方法都会去处理跨域请求,allowedMethods 表示允许通过的请求数,allowedHeaders 则表示允许的请求头
     * 经过这样的配置之后，就不必在每个方法上单独配置跨域
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

}
