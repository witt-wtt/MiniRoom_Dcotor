package com.joyhealth.core.update;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Description :apk安装类
 * Created by wangjin on 2019-08-30.
 * Job number：
 * Phone ：18301070822
 * Email： 120182051@qq.com
 * Person in charge : 汪渝栋
 * Leader：
 */
public interface IApkInstallManager {

    /**
     * root静默安装
     * @param packageManager
     * @param apkPath apk路径
     * @return
     */
    boolean silentInstall(PackageManager packageManager, String apkPath);

    /**
     * 获取安装包版本号
     * @param context
     * @param apkPath
     * @return
     */
    int getApkVersionCode(Context context,String apkPath);
}
