package com.witt.doctor_miniroom.baseview;

/**
 * @ClassName: BaseMVPActivity
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/19 17:38
 * @Description:
 */
public abstract class BaseMVPActivity<T extends BasePresenter> extends BaseActivity implements BaseView {
    protected T mPersenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPersenter != null) {
            mPersenter.detachView();
        }
    }
}
