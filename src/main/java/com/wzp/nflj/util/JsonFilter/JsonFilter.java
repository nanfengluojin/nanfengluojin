package com.wzp.nflj.util.JsonFilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * json过滤
 *
 * @Author: zp.wei
 * @DATE: 2020/9/1 15:28
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonFilter {

    /**
     * 对象class
     *
     * @return
     */
    Class<?> target() default Object.class;

    /**
     * 要过滤的字段字符串数组
     *
     * @return
     */
    String[] excludes() default {};

    /**
     * 要保留的字段字符串数组，如果excludes和includes都有值，优先includes
     *
     * @return
     */
    String[] includes() default {};
}
