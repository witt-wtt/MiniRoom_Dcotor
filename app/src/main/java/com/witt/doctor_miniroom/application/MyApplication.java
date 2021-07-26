package com.witt.doctor_miniroom.application;

import android.app.Application;

import com.tencent.trtc.TRTCCloud;


/**
 * @ClassName: MyAppliction
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/16 15:15
 * @Description:
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //腾讯云视频咨询组件
        TRTCCloud.sharedInstance(getApplicationContext());
    }
}
