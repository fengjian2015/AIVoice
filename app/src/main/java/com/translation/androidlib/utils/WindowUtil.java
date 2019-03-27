package com.translation.androidlib.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import java.util.regex.Pattern;

/**
 * Created by dyj on 2017/3/13.
 */

public class WindowUtil {

    /**
     * 用*号隐藏手机号码
     */
    public static String hidePhoneNum(String phoneNum){
        String centerFourNum = phoneNum.substring(3, 7);
        return phoneNum.replace(centerFourNum, "****");
    }

    /**
     * 获取名字的大写首字母
     */
    /*public static String getFirstC(String name) {
        HanziToPinyin                  hanziToPinyin = HanziToPinyin.getInstance();
        ArrayList<HanziToPinyin.Token> tokens        = hanziToPinyin.get(name);
        return tokens.get(0).target.substring(0, 1).toUpperCase();
    }*/


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * （DisplayMetrics类中属性scaledDensity）
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * （DisplayMetrics类中属性scaledDensity）
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 得到的屏幕的宽度
     */
    public static int getWidthPx(Activity activity) {
        // DisplayMetrics 一个描述普通显示信息的结构，例如显示大小、密度、字体尺寸
        DisplayMetrics displaysMetrics = new DisplayMetrics();// 初始化一个结构
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaysMetrics);// 对该结构赋值
        return displaysMetrics.widthPixels;
    }

    /**
     * 得到的屏幕的高度
     */
    public static int getHeightPx(Activity activity) {
        // DisplayMetrics 一个描述普通显示信息的结构，例如显示大小、密度、字体尺寸
        DisplayMetrics displaysMetrics = new DisplayMetrics();// 初始化一个结构
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaysMetrics);// 对该结构赋值
        return displaysMetrics.heightPixels;
    }

    /**
     * 得到屏幕的dpi
     */
    public static int getDensityDpi(Activity activity) {
        // DisplayMetrics 一个描述普通显示信息的结构，例如显示大小、密度、字体尺寸
        DisplayMetrics displaysMetrics = new DisplayMetrics();// 初始化一个结构
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaysMetrics);// 对该结构赋值
        return displaysMetrics.densityDpi;
    }

    /**
     * 返回状态栏/通知栏的高度
     */
    public static int getStatusHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        return statusBarHeight;
    }

    public static boolean matches(String code) {
        if (null != code && code.length() > 0) {
            Pattern pattern = Pattern.compile("[0-9]+");
            return pattern.matcher(code).matches();
        }
        return false;
    }
}
