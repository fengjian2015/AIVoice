package com.translation.component.constant;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Darren on 2018/12/13.
 */
public class SpCons {

    private static final String FILE_NAME = "AIVoice";
    public static final String SP_KEY_LOGIN_USER = "SP_KEY_LOGIN_USER";

    public static void setLoginState(Context context, boolean state){
        if (context == null)
            return;
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("loginState", state);
        editor.apply();
    }

    public static boolean getLoginState(Context context){
        if (context == null)
            return false;
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, 0);
        return sp.getBoolean("loginState", false);
    }

    public static void setCurrentLanguage(Context context, String currentLanguage){
        if (context == null){
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("currentLanguage", currentLanguage);
        editor.apply();
    }

    public static String getCurrentLanguage(Context context){
        if (context == null){
            return "";
        }
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, 0);
        return sp.getString("currentLanguage", "");
    }

    public static void setTargetLanguage(Context context, String targetLanguage){
        if (context == null){
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("targetLanguage", targetLanguage);
        editor.apply();
    }

    public static String getTargetLanguage(Context context){
        if (context == null){
            return "";
        }
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, 0);
        return sp.getString("targetLanguage", "");
    }

}
