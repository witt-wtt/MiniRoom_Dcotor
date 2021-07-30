package com.witt.doctor_miniroom.baseview;

/**
 * @ClassName: BaseView
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/19 14:28
 * @Description:
 */
public interface BaseView {
    void showLoading(String msg);

    void showLoadSuccess(String msg);

    void showLoadFail(String msg);
}
