package com.wzp.nflj.config;

import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

/**
 * @Author: zp.wei
 * @DATE: 2020/12/7 11:43
 */
@Configuration
public class Knife4jConfig {


    @Bean
    public Docket commonDocket() {
        return new Docket(DocumentationType.OAS_30).pathMapping("/")
                // 将api的元信息设置为包含在json ResourceListing响应中。
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .groupName("通用接口")
                // 选择哪些接口作为swagger的doc发布
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/common/**"))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket backApi() {
        return new Docket(DocumentationType.OAS_30).pathMapping("/")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .groupName("后台接口")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/back/**"))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }


    /**
     * API 页面上半部分展示信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("南风落尽项目接口文档")
                .description("文档描述")
                .contact(new Contact("南风落尽", null, "wzp9215@qq.com"))
                .version("1.0")
                .build();
    }


    /**
     * 设置授权信息
     *
     * @return
     */
    private List<SecurityScheme> securitySchemes() {
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", In.HEADER.toValue());
        return Collections.singletonList(apiKey);
    }


    /**
     * 授权信息全局应用
     *
     * @return
     */
    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(
                SecurityContext
                        .builder()
                        .securityReferences(Collections.singletonList(
                                new SecurityReference("Authorization", new AuthorizationScope[]{
                                        new AuthorizationScope("global", "")
                                })
                        ))
                        .build()
        );
    }


    @SafeVarargs
    private final <T> Set<T> newHashSet(T... ts) {
        if (ts.length > 0) {
            return new LinkedHashSet<>(Arrays.asList(ts));
        }
        return null;
    }
}
