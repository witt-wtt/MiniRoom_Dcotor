package com.witt.doctor_miniroom.mvpmoudle.ui;


import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.witt.doctor_miniroom.R;
import com.witt.doctor_miniroom.baseview.BaseActivity;
import com.witt.doctor_miniroom.baseview.BaseMVPActivity;
import com.witt.doctor_miniroom.main.MainActivity;
import com.witt.doctor_miniroom.mvpmoudle.contract.LoginContract;
import com.witt.doctor_miniroom.mvpmoudle.parameter.LoginParameter;
import com.witt.doctor_miniroom.mvpmoudle.presenter.LoginPresenter;
import com.witt.doctor_miniroom.utils.AppConfigUtils;
import com.witt.doctor_miniroom.view.MyCountDownTimer;
import com.witt.doctor_miniroom.view.ShakeEditText;

import butterknife.BindView;

/**
 * @ClassName:
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/27 09:44
 * @Description:登录页
 */

public class LoginActivity extends BaseMVPActivity<LoginPresenter> implements LoginContract.LoginView {
    @BindView(R.id.login_account)
    ShakeEditText loginAccount;
    @BindView(R.id.login_password)
    ShakeEditText loginPassword;
    @BindView(R.id.recode_ed)
    ShakeEditText recode;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.vistity_gone)
    CheckBox vistity_gone;
    @BindView(R.id.get_recode)
    TextView get_recode;
    MyCountDownTimer helper;

    @Override
    protected int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        isShowTitleBarLayout(false);
        showContentView();
        mPersenter = new LoginPresenter();
        mPersenter.attachView(this);
        loginButton.setOnClickListener(v -> {
            LoginParameter parameter=new LoginParameter();
            parameter.setPhone(mPersenter.getAccountStr(loginAccount));
            parameter.setPassword(mPersenter.getPasswordStr(loginPassword));
            parameter.setCode(mPersenter.getCodeStr(recode));
            parameter.setRegistrationID(AppConfigUtils.getRegistrationID());
            mPersenter.login(parameter);
        });
        get_recode.setOnClickListener(v -> {
            mPersenter.getCode(mPersenter.getAccountStr(loginAccount));
        });
        vistity_gone.setOnCheckedChangeListener((compoundButton,b)->{
            if (b) {
                //显示密码
                loginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                //隐藏密码
                loginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
    }

    @Override
    public void loginJumping() {
        if (helper!=null){
            helper.stop();
            helper=null;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void startDownTime() {
        helper = new MyCountDownTimer(get_recode, "重新获取", 60, 1, LoginActivity.this);
        helper.setOnFinishListener(() -> {
            get_recode.setTextColor(getResources().getColor(R.color.blue_90));
        });
        helper.startTextView();
    }

    @Override
    public void showLoading(String msg) {
        showProgressDialog();
    }

    @Override
    public void showLoadSuccess(String msg) {
        dismissProgressDialog();
    }

    @Override
    public void showLoadFail(String msg) {
        dismissProgressDialog();
    }
}