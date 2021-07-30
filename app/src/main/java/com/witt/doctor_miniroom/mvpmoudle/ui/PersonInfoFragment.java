package com.witt.doctor_miniroom.mvpmoudle.ui;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static com.joyhealth.core.JoyHealth.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.LogUtils;
import com.witt.doctor_miniroom.R;
import com.witt.doctor_miniroom.application.MyApplication;
import com.witt.doctor_miniroom.baseview.BaseFragment;
import com.witt.doctor_miniroom.http.MYOKHttpCallback;
import com.witt.doctor_miniroom.http.MyJoyHealthHttpClient;
import com.witt.doctor_miniroom.http.UrlInterface;
import com.witt.doctor_miniroom.utils.AppConfigUtils;
import com.witt.doctor_miniroom.utils.DataCleanManager;
import com.witt.doctor_miniroom.view.CenterDialog;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;

/**
 * @ClassName:
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/28 09:32
 * @Description: 个人中心
 */

public class PersonInfoFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.clear_alldata)
    RelativeLayout clear_alldata;
    @BindView(R.id.exitlog_btn)
    Button exitlog_btn;
    @BindView(R.id.cach_size)
    TextView cach_size;

    private CenterDialog ds;
    private final Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                   dismissProgressDialog();
                    Toast.makeText(getContext(), "清理完成", Toast.LENGTH_SHORT).show();
                    try {
                        cach_size.setText(DataCleanManager.getTotalCacheSize(getContext()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }

        }
    };

    public static PersonInfoFragment initFragment() {
        PersonInfoFragment fragment = new PersonInfoFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_person_info;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        showContentView();
        ClickUtils.applySingleDebouncing(clear_alldata, this);
        ClickUtils.applySingleDebouncing(exitlog_btn, this);
        try {
            String size = DataCleanManager.getTotalCacheSize(getApplicationContext());
            cach_size.setText(size);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_alldata:
                showProgressDialog();
                new Thread(new ClearCache()).start();
                break;
            case R.id.exitlog_btn:
                loginOut();
                break;
        }
    }

    //退出登录
    private void loginOut() {
        ds = new CenterDialog(getContext(), "Center", getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels, R.layout.exit_layout, R.style.customDialogStyle);
        Button tv_canel = ds.findViewById(R.id.tv_canel);
        Button tv_ok = ds.findViewById(R.id.tv_ok);
        TextView count = ds.findViewById(R.id.count);
        count.setText("确定退出登录？");
        ds.show();
        tv_ok.setOnClickListener(v -> {
            if (AppConfigUtils.getUserId() != null) {
                loginOutPort();
            }
        });
        tv_canel.setOnClickListener(view -> ds.dismiss());
    }
    private void loginOutPort() {
        HashMap<String,Object>map=new HashMap<>();
        map.put("status",2);
        map.put("user_id",AppConfigUtils.getUserId());
        MyJoyHealthHttpClient.postJson(UrlInterface.loginOut(), map, new MYOKHttpCallback() {
            @Override
            public void onSuccess(String result) {
                AppConfigUtils.clearData();
                ds.dismiss();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Objects.requireNonNull(getActivity()).finish();
                LogUtils.d( "退出登录走完了");
            }

            @Override
            public void onError(int code, String message) {
            }

            @Override
            public void onHttpError(Throwable e) {
            }

            @Override
            public void onFinished() {

            }
        });
    }
    class ClearCache implements Runnable {
        @Override
        public void run() {
            try {
                DataCleanManager.clearAllCache(Objects.requireNonNull(getContext()));
                Thread.sleep(3000);
                if (DataCleanManager.getTotalCacheSize(getContext()).startsWith("0")) {
                    handler.sendEmptyMessage(0);
                }
            } catch (Exception e) {
                return;
            }
        }
    }
}