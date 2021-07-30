package com.witt.doctor_miniroom.http;

import android.view.View;

import io.reactivex.disposables.Disposable;

/**
 * @author zhangjinqi
 * @explain 网络配置接口
 * @since 2020/10/23
 *
 *
 */
public interface HttpInterface {

    public void addDisposable(Disposable disposable);//把所有网络的请求记录，方便关闭时清除

    public void clearDisPosable();  // 取消正在下载的网络

    /**
    *显示网络弹窗
    *@author zhangjinqi
    *create at 2020/10/28 10:23 AM
    */
    public void showHttpLoading();

    public void dissMissLoading();
    /**
    *无数据
    *@author zhangjinqi
    *create at 2020/11/13 5:17 PM
    */
    public void noData(View view);
}