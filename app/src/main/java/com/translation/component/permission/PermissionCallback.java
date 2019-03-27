package com.translation.component.permission;

import java.util.List;

/**
 * 权限申请回调接口
 */
public interface PermissionCallback {
    /**
     * 权限通过回调
     */
    void yes(List<String> data);

    /**
     * 权限拒绝回调
     */
    void no(List<String> data);
}
