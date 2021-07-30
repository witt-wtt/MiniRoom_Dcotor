package com.witt.doctor_miniroom.http;

import com.blankj.utilcode.util.LogUtils;
import com.witt.doctor_miniroom.baseview.BaseFragment;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * fragment 网络部分实现
 * @author zhangjinqi
 * @since 2019-10-28
 */
public  abstract class AbstractHttpDisposeAndFragment extends BaseFragment implements HttpInterface {

    public CompositeDisposable compositeDisposable=new CompositeDisposable();



    @Override
    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }


    @Override
    public void showHttpLoading() {
        LogUtils.e("test___1", "showHttpLoading");
        showLoadingView();
    }

    @Override
    public void dissMissLoading() {
        dismissProgressDialog();
    }

    @Override
    public void clearDisPosable() {
        compositeDisposable.clear();
        compositeDisposable=null;
        dismissProgressDialog();

    }
}
