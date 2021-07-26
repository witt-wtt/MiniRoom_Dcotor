package com.witt.doctor_miniroom.main;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.witt.doctor_miniroom.R;
import com.witt.doctor_miniroom.baseview.BaseActivity;
import com.witt.doctor_miniroom.mvpmoudle.ui.VideoRoomActivity;
import com.witt.doctor_miniroom.utils.PermissionListener;

import java.util.List;

import butterknife.BindView;

/**
 * @ClassName:
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/14 10:47
 * @Description:
 */

public class MainActivity extends BaseActivity {
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE};
    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        isShowBackBtn(false);
        setBaseTitle_txt("首页");
        showContentView();

        findViewById(R.id.main_btn).setOnClickListener(v -> {
            requestPermissions(permissions, new PermissionListener() {
                @Override
                public void onGranted() {
                    startActivity(new Intent(MainActivity.this,VideoRoomActivity.class));
                }

                @Override
                public void onDenied(List<String> deniedPermissions) {

                }
            });
        });

    }

    @Override
    public void onRefresh() {

    }
}