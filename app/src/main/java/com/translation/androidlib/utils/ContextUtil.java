package com.translation.androidlib.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.List;

/**
 * Created by fengjian on 2018/12/28.
 */

public class ContextUtil {
    /**
     * 判断是否还存活
     * @param context
     * true 存在
     */
    public static boolean isExist(Context context) {
        return (!(context instanceof Activity) || !((Activity) context).isFinishing()) && (!(context instanceof Service) || isServiceRunning(context, context.getClass().getName())) && context != null;
    }

    private static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(150);

        if (!(serviceList.size() > 0)) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className)) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}
