package com.translation.androidlib.utils;

import android.os.SystemClock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * Created by bob on 2015/2/28.
 */
public class TimeUtil {

    private static long appLaunchSystemTimestamp;
    private static long appLaunchServiceTimestamp;

    public static void initTime(long serviceTimestamp){
        appLaunchSystemTimestamp = SystemClock.elapsedRealtime();
        appLaunchServiceTimestamp = serviceTimestamp;
    }

    /**
     * 得到当前时间
     * @param dateFormat 时间格式
     * @return 转换后的时间格式
     */
    public static String getCurrentTime(String dateFormat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(currentTime);
    }

    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd HH:mm:ss");
    }

    public static long getCurrentTimestamp(){
        return System.currentTimeMillis();
    }

    /**
     * 获取服务器时间戳
     */
    public static long getServiceTimestamp(){
        long currentSystemTimestamp = SystemClock.elapsedRealtime();
//        LogUtil.i("appLaunchSystemTimestamp", appLaunchSystemTimestamp);
//        LogUtil.i("currentSystemTimestamp", currentSystemTimestamp);
//        LogUtil.i("diffTimestamp", currentSystemTimestamp - appLaunchSystemTimestamp);
//        LogUtil.i("appLaunchServiceTime", timestampToStr(appLaunchServiceTimestamp));
//        LogUtil.i("currentTime", timestampToStr(appLaunchServiceTimestamp + currentSystemTimestamp - appLaunchSystemTimestamp));
        return appLaunchServiceTimestamp + currentSystemTimestamp - appLaunchSystemTimestamp;
    }

    /**
     * 时间戳转字符串日期
     */
    public static String timestampToStr(long timeStamp, String timeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        return format.format(new Date(timeStamp));
    }

    public static String timestampToStr(long timeStamp) {
        return timestampToStr(timeStamp, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Date转字符串日期
     */
    public static String dateToStr(Date date, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }

    public static String dateToStr(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    /**
     * 字符串日期转Date
     * @param dateStr    字符串日期
     * @param dateFormat 日期格式
     */
    public static Date strToDate(String dateStr, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date strToDate(String dateStr) {
        return strToDate(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date timestampToDate(long timestamp){
        return strToDate(timestampToStr(timestamp));
    }

    /**
     * yyyy-MM-dd HH:mm:ss字符串日期转新格式字符串日期
     */
    public static String strToStr(String dateStr, String newDateFormat){
        Date date = strToDate(dateStr);
        if (date != null){
            return dateToStr(date, newDateFormat);
        }else {
            return null;
        }
    }

    /**
     * 两个时间点的间隔时长（天数）
     * 得到的差值是微秒级别
     *
     * @param before 开始时间
     * @param after  结束时间
     * @return 两个时间点的间隔时长（天数）
     */
    public static long calDaysDiff(Date before, Date after) {
        if (before == null || after == null) {
            return 0;
        }
        long dif = 0;
        if (after.getTime() >= before.getTime()) {
            dif = after.getTime() - before.getTime();
        } else if (after.getTime() < before.getTime()) {
            dif = after.getTime() + 86400000 - before.getTime();
        }
        dif = Math.abs(dif);
        return dif / (1000 * 60 * 60 * 24);
    }

    public static long calDaysDiff(long beforeTimestamp, long afterTimestamp){
        return calDaysDiff(timestampToDate(beforeTimestamp), timestampToDate(afterTimestamp));
    }

    /**
     * 获取指定时间间隔分钟后的时间
     *
     * @param date 指定的时间
     * @param min  间隔分钟数
     * @return 间隔分钟数后的时间
     */
    public static Date addMinutes(Date date, int min) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, min);
        return calendar.getTime();
    }

    public static String formatTimeStr(long durationSecond) {
        int minute = (int) durationSecond / 60;
        int second = (int) durationSecond - (minute * 60);
        String minuteStr;
        String secondStr;
        if (minute < 10) {
            minuteStr = "0" + minute;
        } else {
            minuteStr = String.valueOf(minute);
        }
        if (second < 10) {
            secondStr = "0" + second;
        } else {
            secondStr = String.valueOf(second);
        }
        return minuteStr + ":" + secondStr;
    }

}
