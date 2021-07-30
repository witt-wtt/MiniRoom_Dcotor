package com.witt.doctor_miniroom.receiver;

import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.LogUtils;
import com.witt.doctor_miniroom.main.MainActivity;
import com.witt.doctor_miniroom.utils.AppConfigUtils;

import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * @ClassName: MyReceiver
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/29 15:36
 * @Description:极光推送广播
 */
public class MyReceiver extends JPushMessageReceiver {

    @Override
    public void onRegister(Context context, String registrationId) {
        super.onRegister(context, registrationId);
        AppConfigUtils.saveRegistrationID(registrationId);
        LogUtils.e("MyReceiver", "s=====" + registrationId);
    }


    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageArrived(context, notificationMessage);
        LogUtils.e("MyReceiver", "s=====" + notificationMessage);
        Intent intent =new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
