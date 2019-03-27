package com.translation.component.permission;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.runtime.PermissionRequest;

import java.util.List;

/**
 * 权限申请包装器
 */
public class RequestWrapper {
    private PermissionRequest permission;

    public RequestWrapper(PermissionRequest permission) {
        this.permission = permission;
    }

    /**
     * 发起权限申请
     */
    public void start(final PermissionCallback c) {
        permission.onGranted(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
                c.yes(data);
            }
        }).onDenied(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
                c.no(data);
            }
        }).start();
    }
}
