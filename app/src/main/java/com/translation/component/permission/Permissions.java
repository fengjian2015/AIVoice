package com.translation.component.permission;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.yanzhenjie.permission.AndPermission;

/**
 * 权限申请封装类入口，从这里开始开始权限申请.
 * Permissions.from(activity, Permission.READ_EXTERNAL_STORAGE)
 * .start(PermissionCallback(){...});
 */
public class Permissions {

    private Permissions() {
    }

    public static RequestWrapper from(Activity activity, String... permissions) {
        return new RequestWrapper(AndPermission.with(activity).runtime().permission(permissions));
    }

    public static RequestWrapper from(Activity activity, String[]... permissions) {
        return new RequestWrapper(AndPermission.with(activity).runtime().permission(permissions));
    }

    public static RequestWrapper from(Fragment fragment, String... permissions) {
        return new RequestWrapper(AndPermission.with(fragment).runtime().permission(permissions));
    }

    public static RequestWrapper from(Fragment fragment, String[]... permissions) {
        return new RequestWrapper(AndPermission.with(fragment).runtime().permission(permissions));
    }

    public static RequestWrapper from(android.app.Fragment fragment, String... permissions) {
        return new RequestWrapper(AndPermission.with(fragment).runtime().permission(permissions));
    }

    public static RequestWrapper from(android.app.Fragment fragment, String[]... permissions) {
        return new RequestWrapper(AndPermission.with(fragment).runtime().permission(permissions));
    }

    /**
     * 判断是否有权限
     */
    public static boolean hasPermission(Context context, String... permissions) {
        return AndPermission.hasPermissions(context, permissions);
    }

    /**
     * 判断是否有权限
     */
    public static boolean hasPermission(Context context, String[]... permissions) {
        return AndPermission.hasPermissions(context, permissions);
    }
}

