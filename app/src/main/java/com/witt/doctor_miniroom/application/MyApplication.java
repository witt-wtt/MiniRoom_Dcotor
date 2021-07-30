package com.witt.doctor_miniroom.application;

import android.app.Application;

import com.blankj.utilcode.util.SPUtils;
import com.joyhealth.core.JoyHealth;
import com.tencent.trtc.TRTCCloud;
import com.witt.doctor_miniroom.BuildConfig;
import com.witt.doctor_miniroom.http.HttpParamaters;
import com.witt.doctor_miniroom.utils.AppConfigUtils;
import com.witt.doctor_miniroom.utils.SSLUtls;

import cat.ereza.customactivityoncrash.config.CaocConfig;
import cn.jpush.android.api.JPushInterface;


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
        //App配置文件初始化
        AppConfigUtils.initSP();
        //腾讯云视频咨询组件
        TRTCCloud.sharedInstance(getApplicationContext());
        //初始化极光推送
        JPushInterface.init(this);
        JoyHealth.withAppContext(this)
                .withActivityCrash(BuildConfig.DEBUG)
                .withApiHost(BuildConfig.SERVICE_BASE_URL + "index.php/Api_v1/")
                .withLogger(BuildConfig.DEBUG)
                .withParamaters(new HttpParamaters())
                .withSSL(SSLUtls.getSSLString(this, SSLUtls.getSSLEnvironment(BuildConfig.DEBUG)+"/ssl.crt"))
                .withSSL_bks(SSLUtls.getSslSocket(this, SSLUtls.getSSLEnvironment(BuildConfig.DEBUG)+"/joyhealth.bks"))
                .build();
        CaocConfig.Builder.create().apply();//开启崩溃日志
    }
}
