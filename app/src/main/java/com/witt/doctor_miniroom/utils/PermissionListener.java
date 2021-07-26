package com.witt.doctor_miniroom.utils;

import java.util.List;

/**
 * @ClassName: PermissionListener
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/16 13:21
 * @Description:权限回调接口
 */
public interface PermissionListener {
    void onGranted();

    void onDenied(List<String> deniedPermissions);
}
