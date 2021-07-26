package com.joyhealth.core.update.impl;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import com.blankj.utilcode.util.FileUtils;
import com.joyhealth.core.update.IApkInstallManager;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import io.reactivex.annotations.NonNull;


/**
 * Description :
 * Created by wangjin on 2019-08-30.
 * Job number：
 * Phone ：18301070822
 * Email： 120182051@qq.com
 * Person in charge : 汪渝栋
 * Leader：
 */
public class ApkInstallManager implements IApkInstallManager {

    //安装文件扩展名
    private static final String EXTENSION = "apk";

    private void ApkInstallManager() {
    }

    private static class ApkInstallManagerInstance {
        private static final ApkInstallManager apkInstallManager = new ApkInstallManager();
    }

    public static final ApkInstallManager getInstance() {
        return ApkInstallManagerInstance.apkInstallManager;
    }

    @Override
    public boolean silentInstall(@NonNull PackageManager packageManager, @NonNull String apkPath) {

        //判断文件是否存在及文件扩展名是否为apk
        if (!FileUtils.isFileExists(apkPath) && (!FileUtils.getFileExtension(apkPath).equals(EXTENSION))) {
            Logger.e("core->Install apk faild, File not find or file extensixion is not .apk!!");
            return false;
        }

        //静默安装apk
        Class<?> pmClz = packageManager.getClass();
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                Class<?> aClass = Class.forName("android.app.PackageInstallObserver");
                Constructor<?> constructor = aClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                Object installObserver = constructor.newInstance();
                Method method = pmClz.getDeclaredMethod("installPackage", Uri.class, aClass, int.class, String.class);
                method.setAccessible(true);
                method.invoke(packageManager, Uri.fromFile(new File(apkPath)), installObserver, 2, null);
            } else {
                Method method = pmClz.getDeclaredMethod("installPackage", Uri.class, Class.forName("android.content.pm.IPackageInstallObserver"), int.class, String.class);
                method.setAccessible(true);
                method.invoke(packageManager, Uri.fromFile(new File(apkPath)), null, 2, null);
            }

            if (FileUtils.isFileExists(apkPath)) {
                FileUtils.delete(apkPath);
            }
            Logger.d("core->Install apk success!!");
            return true;
        } catch (Exception e) {
            Logger.e("core->Install apk falild!!", e.getMessage());
        }
        return false;
    }

    @Override
    public int getApkVersionCode(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(
                    context.getPackageName(), 0);
            Logger.d("packageInfo.versionName is " + packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return (int)packageInfo.getLongVersionCode();
        }else {
            return packageInfo.versionCode;
        }
    }
}
