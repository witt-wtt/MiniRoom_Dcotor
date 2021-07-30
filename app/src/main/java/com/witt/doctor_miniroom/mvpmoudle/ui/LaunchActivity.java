package com.witt.doctor_miniroom.mvpmoudle.ui;


import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.witt.doctor_miniroom.R;
import com.witt.doctor_miniroom.baseview.BaseActivity;
import com.witt.doctor_miniroom.main.MainActivity;
import com.witt.doctor_miniroom.utils.AppConfigUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @ClassName:
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/27 15:50
 * @Description:APP 启动页
 */

public class LaunchActivity extends BaseActivity {

    @Override
    protected int bindLayout() {
        return R.layout.activity_launch;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        isShowTitleBarLayout(false);
        showContentView();
        LogUtils.e(TAG, "是否登录===" + AppConfigUtils.getLoginStatus());
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (AppConfigUtils.getLoginStatus()) {
                    startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, 2000);
    }
}