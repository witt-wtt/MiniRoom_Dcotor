package com.witt.doctor_miniroom.baseview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ClickUtils;
import com.witt.doctor_miniroom.R;
import com.witt.doctor_miniroom.utils.ActivityUtils;
import com.witt.doctor_miniroom.utils.PermissionListener;
import com.witt.doctor_miniroom.utils.ProgressDialogUtils;
import com.witt.doctor_miniroom.view.NetworkStateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ClassName:BaseActivity
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/14 10:55
 * @Description: 所有Activity的基类
 */

public abstract class BaseActivity extends AppCompatActivity implements NetworkStateView.OnRefreshListener {
    private Unbinder unbinder;

    private ProgressDialogUtils progressDialog;

    private static PermissionListener mPermissionListener;
    private static final int CODE_REQUEST_PERMISSION = 1;

    private NetworkStateView networkStateView;
    private ImageButton baseBack_btn;//返回按钮
    private TextView baseTitle_txt;//标题文本

    private RelativeLayout titleBar_layout;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //固定手机只能竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(bindLayout());
        unbinder = ButterKnife.bind(this);
        ActivityUtils.addActivity(this);
        initTitleBar();
        initDialog();
        afterCreate(savedInstanceState);
    }

    @SuppressLint("InflateParams")
    @Override
    public void setContentView(int layoutResID) {
        View baseView = getLayoutInflater().inflate(R.layout.activity_base, null);
        //设置填充activity_base布局
        super.setContentView(baseView);
        baseView.setFitsSystemWindows(true);
        //加载子类Activity的布局
        initChildView(layoutResID);
    }

    /**
     * 初始化默认布局的View
     *
     * @param layoutResID
     */
    private void initChildView(int layoutResID) {
        networkStateView = findViewById(R.id.nsv_state_view);
        FrameLayout container = (FrameLayout) findViewById(R.id.fl_activity_child_container);
        View childView = LayoutInflater.from(this).inflate(layoutResID, null);
        container.addView(childView, 0);
    }

    private void initDialog() {
        progressDialog = new ProgressDialogUtils(this, R.style.dialog_transparent_style);
    }

    private void initTitleBar() {
        baseTitle_txt = findViewById(R.id.titleBar_title_txt);
        baseBack_btn = findViewById(R.id.titleBar_back_btn);
        titleBar_layout = findViewById(R.id.titleBar_layout);
    }

    /**
     * 绑定布局
     */
    protected abstract int bindLayout();

    /**
     * 初始化控件
     */
    protected abstract void afterCreate(Bundle savedInstanceState);

    /**
     * 获取当前类标签
     */
    protected final String TAG = this.getClass().getSimpleName();


    /**
     * 设置标题
     */
    public void setBaseTitle_txt(String title) {
        baseTitle_txt.setText(title);
    }

    /**
     * 返回按钮
     */
    public void setBackOnClickListener(View.OnClickListener onClickListener) {
        ClickUtils.applySingleDebouncing(baseBack_btn, 1000, onClickListener);
    }

    /**
     * 隐藏标题栏
     */
    public void isShowTitleBarLayout(boolean show) {
        titleBar_layout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * 隐藏返回按钮
     */
    public void isShowBackBtn(boolean show) {
        baseBack_btn.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * 显示加载中的布局
     */
    public void showLoadingView() {
        networkStateView.showLoading();
    }

    /**
     * 显示加载完成后的布局(即子类Activity的布局)
     */
    public void showContentView() {
        networkStateView.showSuccess();
    }

    /**
     * 显示没有网络的布局
     */
    public void showNoNetworkView() {
        networkStateView.showNoNetWork();
        networkStateView.setOnRefreshListener(this);
    }

    /**
     * 显示没有数据的布局
     */
    public void showEmptyView() {
        networkStateView.showEmpty();
        networkStateView.setOnRefreshListener(this);
    }

    /**
     * 显示数据错误，网络错误等布局
     */
    public void showErrorView() {
        networkStateView.showError();
        networkStateView.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        onNetworkViewRefresh();
    }

    /**
     * 重新请求网络
     */
    public void onNetworkViewRefresh() {
    }

    /**
     * 显示加载的ProgressDialog
     */
    public void showProgressDialog() {
        progressDialog.showProgressDialog();
    }

    /**
     * 显示有加载文字ProgressDialog，文字显示在ProgressDialog的下面
     *
     * @param text 需要显示的文字
     */
    public void showProgressDialogWithText(String text) {
        progressDialog.showProgressDialogWithText(text);
    }

    /**
     * 显示加载成功的ProgressDialog，文字显示在ProgressDialog的下面
     *
     * @param message 加载成功需要显示的文字
     * @param time    需要显示的时间长度(以毫秒为单位)
     */
    public void showProgressSuccess(String message, long time) {
        progressDialog.showProgressSuccess(message, time);
    }

    /**
     * 显示加载成功的ProgressDialog，文字显示在ProgressDialog的下面
     * ProgressDialog默认消失时间为1秒(1000毫秒)
     *
     * @param message 加载成功需要显示的文字
     */
    public void showProgressSuccess(String message) {
        progressDialog.showProgressSuccess(message);
    }

    /**
     * 显示加载失败的ProgressDialog，文字显示在ProgressDialog的下面
     *
     * @param message 加载失败需要显示的文字
     * @param time    需要显示的时间长度(以毫秒为单位)
     */
    public void showProgressFail(String message, long time) {
        progressDialog.showProgressFail(message, time);
    }

    /**
     * 显示加载失败的ProgressDialog，文字显示在ProgressDialog的下面
     * ProgressDialog默认消失时间为1秒(1000毫秒)
     *
     * @param message 加载成功需要显示的文字
     */
    public void showProgressFail(String message) {
        progressDialog.showProgressFail(message);
    }

    /**
     * 隐藏加载的ProgressDialog
     */
    public void dismissProgressDialog() {
        progressDialog.dismissProgressDialog();
    }

    /**
     * 申请权限
     *
     * @param permissions 需要申请的权限(数组)
     * @param listener    权限回调接口
     */
    public static void requestPermissions(String[] permissions, PermissionListener listener) {
        Activity activity = ActivityUtils.getTopActivity();
        if (null == activity) {
            return;
        }

        mPermissionListener = listener;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            //权限没有授权
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[permissionList.size()]), CODE_REQUEST_PERMISSION);
        } else {
            mPermissionListener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CODE_REQUEST_PERMISSION:
                if (grantResults.length > 0) {
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int result = grantResults[i];
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            String permission = permissions[i];
                            deniedPermissions.add(permission);
                        }
                    }

                    if (deniedPermissions.isEmpty()) {
                        mPermissionListener.onGranted();
                    } else {
                        mPermissionListener.onDenied(deniedPermissions);
                    }
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
