package com.witt.doctor_miniroom.baseview;

/**
 * @ClassName: BasePresenter
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/19 14:29
 * @Description:
 */
public abstract class BasePresenter<V extends BaseView> {
    protected V mView;

    /**
     * 绑定view，一般在初始化中调用该方法
     *
     * @param view view
     */
    public void attachView(V view) {
        this.mView = view;
    }

    /**
     * 解除绑定view，一般在onDestroy中调用
     */

    public void detachView() {
        this.mView = null;
    }

    /**
     * View是否绑定
     *
     * @return
     */
    public boolean isViewAttached() {
        return mView != null;
    }
}
