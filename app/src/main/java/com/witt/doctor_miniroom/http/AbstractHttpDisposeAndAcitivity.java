package com.witt.doctor_miniroom.http;



import com.blankj.utilcode.util.LogUtils;
import com.witt.doctor_miniroom.baseview.BaseActivity;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * activity 网络部分实现
 * @author zhangjinqi
 * @since 2019-10-28
 */
public  abstract class AbstractHttpDisposeAndAcitivity extends BaseActivity implements HttpInterface {

    public CompositeDisposable compositeDisposable=new CompositeDisposable();


    @Override
    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }


    @Override
    public void showHttpLoading() {
       LogUtils.e("test___1", "showLoading");

        showLoadingView();
    }

    @Override
    public void dissMissLoading() {
        LogUtils.e("test___1", "dissMissLoading");
        dissMissLoading();
    }

    @Override
    public void clearDisPosable() {
        compositeDisposable.clear();
        compositeDisposable=null;
        dissMissLoading();

    }
}
