package com.witt.doctor_miniroom.mvpmoudle.contract;


import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloud;
import com.witt.doctor_miniroom.baseview.BasePresenter;
import com.witt.doctor_miniroom.baseview.BaseView;
import com.witt.doctor_miniroom.mvpmoudle.ui.VideoRoomActivity;

/**
 * @ClassName: VdeoRoomContract
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/19 14:11
 * @Description:视频咨询契约
 */
public interface VideoRoomContract {
    interface View extends BaseView {
        void startTime();
    }

    interface Presenter{
        void enterRoom(TRTCCloud mTRTCCloud,int room_id, TXCloudVideoView mTXCVVLocalPreviewView,TXCloudVideoView mTXCVVRemotePreviewView,boolean mIsFrontCamera);
        void exitRoom(TRTCCloud mTRTCCloud);
    }

    interface Model {
        void loginTRTC();
    }
}
