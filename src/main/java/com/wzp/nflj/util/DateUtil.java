package com.wzp.nflj.util;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author: zp.wei
 * @DATE: 2020/10/21 14:10
 */
public class DateUtil {


    /**
     * 获取当前系统时间
     *
     * @return 毫秒值的时间戳
     */
    public static long sysTime() {
        return System.currentTimeMillis();
    }


    /**
     * 获取当前系统时间并格式化
     *
     * @return 格式化后的时间 yyyy-MM-dd HH:mm:ss
     */
    public static String formatSysTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date(sysTime()));
    }


    /**
     * 时间戳按指定格式转化为日期
     *
     * @param timestamp 时间戳
     * @return 格式化后的时间 yyyy-MM-dd HH:mm:ss
     */
    public static String formatSysTime(Long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date(timestamp));
    }


    /**
     * 使用LocalDateTime获取现在的时间
     *
     * @return 时间
     */
    public static LocalDateTime nowTime() {
        return LocalDateTime.now();
    }


    /**
     * 使用LocalDateTime获取现在的时间并格式化
     *
     * @return 格式化后的时间 yyyy-MM-dd HH:mm:ss
     */
    public static String formatLocalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return nowTime().format(formatter);
    }


    /**
     * 将LocalDateTime格式的时间格式化
     * @param localDateTime 时间
     * @return 格式化后的时间 yyyy-MM-dd HH:mm:ss
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }


    /**
     * LocalDateTime转为时间戳
     *
     * @param localDateTime 时间
     * @return 毫秒值的时间戳
     */
    public static Long localDateTimeToTimestamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }


    /**
     * 时间戳转LocalDateTime
     *
     * @param timestamp 时间戳
     * @return LocalDateTime
     */
    public static LocalDateTime timestampToLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }


    /**
     * 使用LocalDate获取现在的时间 只能获取到年月日
     *
     * @return
     */
    public static LocalDate localDate() {
        return LocalDate.now();
    }


    /**
     * 使用LocalTime获取现在的时间 只能获取到时分秒毫秒
     *
     * @return
     */
    public static LocalTime localTime() {
        return LocalTime.now();
    }


    /**
     * 获取今日零点时间戳
     *
     * @return
     */
    public static Long firstDayZero() {
        return localDate().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
    }


    /**
     * 获取今日最后一秒的时间戳
     *
     * @return
     */
    public static Long lastDayZero() {
        return localDate().plusDays(1).atStartOfDay(ZoneOffset.ofHours(8)).minusSeconds(1L).toInstant().toEpochMilli();
    }


    /**
     * 获取本周第一天的零点时间戳
     *
     * @return
     */
    public static Long startDayTimeOfWeek() {
        return localDate().with(DayOfWeek.MONDAY).atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
    }


    /**
     * 获取本周最后一天最后一秒的时间戳
     *
     * @return
     */
    public static Long lastDayTimeOfWeek() {
        return localDate().plusWeeks(1).with(DayOfWeek.MONDAY).atStartOfDay(ZoneOffset.ofHours(8)).minusSeconds(1L).toInstant().toEpochMilli();
    }


    /**
     * 获取本月第一天的零点时间戳
     *
     * @return
     */
    public static Long startDayTimeOfMonth() {
        return localDate().withDayOfMonth(1).atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
    }


    /**
     * 获取本月最后一天最后一秒的时间戳
     *
     * @return
     */
    public static Long lastDayTimeOfMonth() {
        return localDate().plusMonths(1).withDayOfMonth(1).atStartOfDay(ZoneOffset.ofHours(8)).minusSeconds(1L).toInstant().toEpochMilli();
    }
    
    
     /**
     * 获取上月第一天的零点时间戳
     *
     * @return
     */
    public static Long startDayTimeOfLastMonth() {
        return localDate().minusMonths(1).withDayOfMonth(1).atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
    }


    /**
     * 获取本年第一天的时间戳
     *
     * @return
     */
    public static Long firstDayTimeOfYear() {
        return localDate().withDayOfYear(1).atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
    }


    /**
     * 获取本年最后一天最后一秒的时间戳
     *
     * @return
     */
    public static Long lastDayTimeOfYear() {
        return localDate().plusYears(1).withDayOfYear(1).atStartOfDay(ZoneOffset.ofHours(8)).minusSeconds(1L).toInstant().toEpochMilli();
    }


    /**
     * 获取今日属于星期几
     *
     * @return
     */
    public static Integer getDayOfWeek() {
        return localDate().getDayOfWeek().getValue();
    }


    /**
     * 获取对应日期属于星期几
     *
     * @param timeZero
     * @return
     */
    public static Integer getDayOfWeek(Long timeZero) {
        return Instant.ofEpochMilli(timeZero).atZone(ZoneOffset.ofHours(8)).toLocalDate().getDayOfWeek().getValue();
    }


    /**
     * 获取今日属于第几月
     *
     * @return
     */
    public static Integer getDayOfMonth() {
        return localDate().getMonthValue();
    }


    /**
     * 获取对应日期属于第几月
     *
     * @param
     * @return
     */
    public static Integer getDayOfMonth(Long timeZero) {
        return Instant.ofEpochMilli(timeZero).atZone(ZoneOffset.ofHours(8)).toLocalDate().getMonthValue();
    }


    /**
     * 获取今日属于哪一年
     *
     * @return
     */
    public static Integer getDayOfYear() {
        return localDate().getYear();
    }


    /**
     * 获取对应日期属于哪一年
     *
     * @param timeZero
     * @return
     */
    public static Integer getDayOfYear(Long timeZero) {
        return Instant.ofEpochMilli(timeZero).atZone(ZoneOffset.ofHours(8)).toLocalDate().getYear();
    }


}
