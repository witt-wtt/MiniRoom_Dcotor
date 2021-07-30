package com.witt.doctor_miniroom.mvpmoudle.ui;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloud;
import com.witt.doctor_miniroom.R;
import com.witt.doctor_miniroom.adapters.StringAdapter;
import com.witt.doctor_miniroom.baseview.BaseMVPActivity;
import com.witt.doctor_miniroom.mvpmoudle.contract.VideoRoomContract;
import com.witt.doctor_miniroom.mvpmoudle.presenter.VideoRoomPresenter;
import com.witt.doctor_miniroom.utils.AppConfigUtils;
import com.witt.doctor_miniroom.utils.TRTCConfigUtils;
import com.witt.doctor_miniroom.view.TimeCountDownTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @ClassName: VideoRoomActivity
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/19 14:02
 * @Description: 视频聊天页面
 */

public class VideoRoomActivity extends BaseMVPActivity<VideoRoomPresenter> implements VideoRoomContract.View, View.OnClickListener {
    private TRTCCloud mTrtcCloud;
    @BindView(R.id.location_videoView)
    TXCloudVideoView mTXCVVLocalPreviewView;//本地画面
    @BindView(R.id.remote_videoView)
    TXCloudVideoView mTXCVVRemotePreviewView;//远端画面
    //左侧功能区
    @BindView(R.id.mouth_test)
    TextView mouthTest;
    @BindView(R.id.auscultation_test)
    TextView auscultationTest;
    //右侧功能区
    @BindView(R.id.drug_test)
    TextView drugTest;
    @BindView(R.id.detection_test)
    TextView detectionTest;
    @BindView(R.id.patient_info)
    TextView patientInfo;
    //挂断
    @BindView(R.id.over_video_btn)
    TextView hangUp;
    //时间
    @BindView(R.id.video_time)
    TimeCountDownTextView videoTime;

    @BindView(R.id.bottomDrawer_layout)
    LinearLayout bottomDrawer_layout;

    BottomSheetBehavior<LinearLayout> bottomSheetBehavior;
    private int room_id;

    private String[] titles = {"身高体重", "体温", "体脂", "血压", "血氧", "心电", "口腔", "听诊"};
    private boolean isClick = false;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (videoTime != null) {
                        videoTime.stopTime();
                        videoTime = null;
                    }
                    finish();
                    break;
            }
        }
    };

    @Override
    protected int bindLayout() {
        return R.layout.activity_video_room;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        isShowTitleBarLayout(false);
        showContentView();
        if (getIntent() != null) {
            room_id = Integer.parseInt(getIntent().getStringExtra(AppConfigUtils.ROOM_ID));
            mPersenter = new VideoRoomPresenter();
            mPersenter.attachView(this);//p层持有view和model
            mTrtcCloud = TRTCCloud.sharedInstance(getApplicationContext());
            mPersenter.enterRoom(mTrtcCloud, room_id, mTXCVVLocalPreviewView, mTXCVVRemotePreviewView, true);
        }

        ClickUtils.applySingleDebouncing(mouthTest, this);
        ClickUtils.applySingleDebouncing(auscultationTest, this);
        ClickUtils.applySingleDebouncing(drugTest, this);
        ClickUtils.applySingleDebouncing(detectionTest, this);
        ClickUtils.applySingleDebouncing(patientInfo, this);
        ClickUtils.applySingleDebouncing(hangUp, this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPersenter.exitRoom(mTrtcCloud);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mouth_test://口腔
                ToastUtils.showShort("口腔");
                break;
            case R.id.auscultation_test://听诊
                ToastUtils.showShort("听诊");
                break;
            case R.id.drug_test://药品
                ToastUtils.showShort("药品");
                break;
            case R.id.detection_test://检测
                initDetectionTestLayout();
                break;
            case R.id.patient_info://患者信息
                initPatientInfoLayout();
                break;
            case R.id.over_video_btn://挂断
                finish();
                break;
        }
    }

    @Override
    public void startTime() {
        videoTime.setCountDownTimes(TRTCConfigUtils.MAX_COUNTDOWN_TIME, getString(R.string.count_down_default_format));
        videoTime.start();
        videoTime.setOnCountDownFinishListener(() -> {
            handler.sendEmptyMessage(1);
        });
    }

    //检测项目布局
    private void initDetectionTestLayout() {
        bottomDrawer_layout.removeAllViews();
        View view = getLayoutInflater().inflate(R.layout.view_detection_test_layout, null);
        bottomDrawer_layout.addView(view);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomDrawer_layout);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                LogUtils.e("当前页面状态=====" + newState);
                if (newState == BottomSheetBehavior.STATE_COLLAPSED || newState == BottomSheetBehavior.STATE_HIDDEN) {
                    isClick = false;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        ImageView imageView = view.findViewById(R.id.down_btn);
        imageView.setOnClickListener(v -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN));
        isShowView();

        RecyclerView recyclerView = view.findViewById(R.id.detection_RecycleView);
        List<String> list = new ArrayList<>(Arrays.asList(titles));
        StringAdapter adapter = new StringAdapter(VideoRoomActivity.this, list);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(VideoRoomActivity.this, 2);
        linearLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    //患者信息
    private void initPatientInfoLayout() {
        bottomDrawer_layout.removeAllViews();
        View view = getLayoutInflater().inflate(R.layout.view_patientinfo_layout, null);
        bottomDrawer_layout.addView(view);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomDrawer_layout);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                LogUtils.e("当前页面状态=====" + newState);
                if (newState == BottomSheetBehavior.STATE_COLLAPSED || newState == BottomSheetBehavior.STATE_HIDDEN) {
                    isClick = false;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        ImageView imageView = view.findViewById(R.id.down_btn);
        imageView.setOnClickListener(v -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN));
        isShowView();
    }

    //布局的显示和隐藏
    private void isShowView() {
        if (!isClick) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            isClick = true;
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            isClick = false;
        }
    }

    @Override
    public void showLoading(String msg) {
        showProgressDialog();
    }

    @Override
    public void showLoadSuccess(String msg) {
        showProgressSuccess(msg);
    }

    @Override
    public void showLoadFail(String msg) {
        showProgressFail(msg);
    }
}