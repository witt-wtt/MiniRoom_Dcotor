package com.witt.doctor_miniroom.mvpmoudle.presenter;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.tencent.liteav.TXLiteAVCode;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;
import com.witt.doctor_miniroom.baseview.BasePresenter;
import com.witt.doctor_miniroom.mvpmoudle.contract.VideoRoomContract;
import com.witt.doctor_miniroom.mvpmoudle.model.VideoRoomModel;
import com.witt.doctor_miniroom.mvpmoudle.ui.VideoRoomActivity;
import com.witt.doctor_miniroom.utils.TRTCConfigUtils;

import java.lang.ref.WeakReference;

/**
 * @ClassName: VideoRoomPresenter
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/19 14:49
 * @Description:
 */
public class VideoRoomPresenter extends BasePresenter<VideoRoomContract.View> implements VideoRoomContract.Presenter {
    VideoRoomContract.Model model;

    public VideoRoomPresenter() {
        model = new VideoRoomModel();
    }

    @Override
    public void enterRoom(TRTCCloud mTRTCCloud, TXCloudVideoView mTXCVVLocalPreviewView, TXCloudVideoView mTXCVVRemotePreviewView, boolean mIsFrontCamera) {
        mTRTCCloud.setListener(new TRTCCloudImplListener((VideoRoomActivity) mView, mTXCVVRemotePreviewView, mTRTCCloud));

        TRTCCloudDef.TRTCParams trtcParams = new TRTCCloudDef.TRTCParams();
        trtcParams.sdkAppId = TRTCConfigUtils.APP_ID;
        trtcParams.userId = TRTCConfigUtils.USER_ID;
        trtcParams.roomId = TRTCConfigUtils.ROOM_ID;
        trtcParams.userSig = TRTCConfigUtils.USER_SIG;

        mTRTCCloud.startLocalPreview(mIsFrontCamera, mTXCVVLocalPreviewView);
        mTRTCCloud.startLocalAudio(TRTCCloudDef.TRTC_AUDIO_QUALITY_SPEECH);
        mTRTCCloud.enterRoom(trtcParams, TRTCCloudDef.TRTC_APP_SCENE_VIDEOCALL);

    }

    @Override
    public void exitRoom(TRTCCloud mTRTCCloud) {
        if (mTRTCCloud != null) {
            mTRTCCloud.stopLocalAudio();
            mTRTCCloud.stopLocalPreview();
            mTRTCCloud.exitRoom();
            mTRTCCloud.setListener(null);
        }
        TRTCCloud.destroySharedInstance();
    }

    private class TRTCCloudImplListener extends TRTCCloudListener {

        private WeakReference<VideoRoomActivity> mContext;
        private TRTCCloud mTRTCCloud;
        private TXCloudVideoView mTXCVVRemotePreviewView;
        private String remoteUserId = null;

        public TRTCCloudImplListener(VideoRoomActivity activity, TXCloudVideoView mTXCVVRemotePreviewView, TRTCCloud mTRTCCloud) {
            super();
            this.mTRTCCloud = mTRTCCloud;
            this.mTXCVVRemotePreviewView = mTXCVVRemotePreviewView;
            mContext = new WeakReference<>(activity);
        }

        @Override
        public void onUserVideoAvailable(String userId, boolean available) {
            if (available) {
                remoteUserId = userId;
            } else {
                mTRTCCloud.stopRemoteView(userId);
                mTXCVVRemotePreviewView.setVisibility(View.GONE);
            }
            refreshRemoteVideoViews();
        }

        private void refreshRemoteVideoViews() {
            if (remoteUserId != null) {
                mTXCVVRemotePreviewView.setVisibility(View.VISIBLE);
                TRTCCloudDef.TRTCRenderParams renderParams = new TRTCCloudDef.TRTCRenderParams();
                renderParams.fillMode = TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FILL;
                renderParams.rotation = TRTCCloudDef.TRTC_VIDEO_ROTATION_270;
                mTRTCCloud.setRemoteRenderParams(remoteUserId, TRTCCloudDef.TRTC_VIDEO_STREAM_TYPE_SMALL, renderParams);
                mTRTCCloud.startRemoteView(remoteUserId, TRTCCloudDef.TRTC_VIDEO_STREAM_TYPE_SMALL, mTXCVVRemotePreviewView);
            } else {
                mTXCVVRemotePreviewView.setVisibility(View.GONE);
            }
        }

        @Override
        public void onError(int errCode, String errMsg, Bundle extraInfo) {
            VideoRoomActivity activity = mContext.get();
            if (activity != null) {
                Toast.makeText(activity, "onError: " + errMsg + "[" + errCode + "]", Toast.LENGTH_SHORT).show();
                if (errCode == TXLiteAVCode.ERR_ROOM_ENTER_FAIL) {
                    exitRoom(mTRTCCloud);
                }
            }
        }

        @Override
        public void onEnterRoom(long l) {
            super.onEnterRoom(l);
            LogUtils.e("TRTC", "已加入房间的回调");
        }

        @Override
        public void onExitRoom(int i) {
            super.onExitRoom(i);
            LogUtils.e("TRTC", "离开房间的回调");
        }

        @Override
        public void onRemoteUserEnterRoom(String s) {
            super.onRemoteUserEnterRoom(s);
            mView.startTime();
            LogUtils.e("TRTC", "有人加入了房间+" + s);
        }

        @Override
        public void onRemoteUserLeaveRoom(String s, int i) {
            super.onRemoteUserLeaveRoom(s, i);
            LogUtils.e("TRTC", "有人离开了房间+" + s);
        }
    }


}
