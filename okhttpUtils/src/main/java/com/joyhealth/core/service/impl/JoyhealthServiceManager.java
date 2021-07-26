//package com.joyhealth.core.service.impl;
//
//import android.app.Application;
//import android.app.Service;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.os.IBinder;
//import android.os.RemoteException;
//import android.util.Log;
//
//import com.blankj.utilcode.util.LogUtils;
//import com.blankj.utilcode.util.ToastUtils;
//import com.joyhealth.core.service.IJoyhealthServiceManager;
//import com.joyhealth.service.IJoyhealthServerService;
//import com.orhanobut.logger.Logger;
//
//import java.net.ConnectException;
//
//
///**
// * Description :核心服务管理器
// * Created by wangjin on 2019-09-04.
// * Job number：
// * Phone ：18301070822
// * Email： 120182051@qq.com
// * Person in charge : 汪渝栋
// * Leader：
// */
//public class JoyhealthServiceManager implements IJoyhealthServiceManager {
//
//    private Context mContext;
//
//    private void JoyhealthServiceManager() {
//    }
//
//    static class JoyhealthServiceManagerInstance {
//        private static final JoyhealthServiceManager joyhealthServiceManager = new JoyhealthServiceManager();
//    }
//
//    public static JoyhealthServiceManager getInstance() {
//        return JoyhealthServiceManagerInstance.joyhealthServiceManager;
//    }
//
//    public JoyhealthServiceManager setContext(Context context) {
//        this.mContext = context;
//        return this;
//    }
//
//    final ServiceConnection connection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            Logger.d("onServiceConnected: RemoteService 链接成功");
////            mIsBound = true;
//            IJoyhealthServerService remoteService = IJoyhealthServerService.Stub.asInterface(service);
//            try {
//                ToastUtils.showLong("remoteService start");
//                remoteService.bindJoyhealthServerService();
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            ToastUtils.showLong("RemoteService 断开连接，重新启动");
//            Logger.d("onServiceDisconnected: RemoteService 断开连接，重新启动");
////            mIsBound = false;
//        }
//    };
//
//
//    @Override
//    public void bindJoyhealthServerService() {
//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent("com.joyhealth.service.wyd");
//                        intent.setPackage("com.joyhealth.service");
//                        boolean result = mContext.bindService(intent, connection, Service.BIND_AUTO_CREATE);
//                        if (!result) {
//                            Logger.e("绑定远程服务失败!!!");
//                        }
//                    }
//                }
//        ).start();
//    }
//}
