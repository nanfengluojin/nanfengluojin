package com.wzp.nflj.util;

import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Map;

/**
 * @Author: zp.wei
 * @DATE: 2020/10/21 13:08
 */
public class ObjUtil {


    /**
     * 检查参数是否为空
     *
     * @param str 待检查参数
     * @return
     */
    public static boolean isEmpty(@Nullable Object str) {
        return (str == null || "".equals(str));
    }


    /**
     * 检查参数是否为空 排除== "" 的验证
     *
     * @param str 待检查参数
     * @return
     */
    public static boolean isNull(@Nullable Object str) {
        return (str == null);
    }


    /**
     * 检查 list 是否为空
     *
     * @param collection 待检查参数
     * @return
     */
    public static boolean isEmptyList(@Nullable Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }


    /**
     * 检查 map 是否为空
     *
     * @param map 待检查参数
     * @return
     */
    public static boolean isEmptyMap(@Nullable Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }


    /**
     * 获取指定长度之后部分
     *
     * @param str        待分割字符串
     * @param beginIndex 起始位置
     * @return 返回分割后的字符串(包含起始位置对应的字符)
     */
    public static String subString(String str, int beginIndex) {
        if (str.length() - beginIndex > 0) {
            return str.substring(beginIndex);
        }
        return "";
    }


    /**
     * 获取指定部分
     *
     * @param str        待分割字符串
     * @param beginIndex 起始位置
     * @param endIndex   结束位置
     * @return
     */
    public static String subSpecString(String str, int beginIndex, int endIndex) {
        if (str.length() - beginIndex < 0 || str.length() - endIndex < 0 || endIndex > beginIndex) {
            return "";
        }
        return str.substring(beginIndex, endIndex);
    }


    /**
     * 获取指定分隔符之前部分
     *
     * @param str       需要分割的字符串
     * @param separator 分隔符，若字符串中有相同的分隔符，则以最后一个分隔符为准
     * @param index     偏移量>=0，偏移量为0时，不包含分隔符
     * @return
     */
    public static String strPrefix(String str, String separator, Integer index) {
        if (!str.contains(separator)) {
            return str;
        }
        if (index <= 0) {
            return str.substring(0, str.lastIndexOf(separator));
        }
        return str.substring(0, str.lastIndexOf(separator) + index);
    }


    /**
     * 获取指定分隔符之后部分
     *
     * @param str       需要分割的字符串
     * @param separator 分隔符，若字符串中有相同的分隔符，则以最后一个分隔符为准
     * @param index     偏移量>=0，偏移量为0时，返回值包含分隔符
     * @return
     */
    public static String strSuffix(String str, String separator, Integer index) {
        if (!str.contains(separator)) {
            return "";
        }
        if (index <= 0) {
            return str.substring(str.lastIndexOf(separator));
        }
        return str.substring(str.lastIndexOf(separator) + index);
    }


}
