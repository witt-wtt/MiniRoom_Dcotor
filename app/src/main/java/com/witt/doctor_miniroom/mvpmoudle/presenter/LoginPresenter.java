package com.witt.doctor_miniroom.mvpmoudle.presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.witt.doctor_miniroom.R;
import com.witt.doctor_miniroom.baseview.BasePresenter;
import com.witt.doctor_miniroom.http.MYOKHttpCallback;
import com.witt.doctor_miniroom.http.MyJoyHealthHttpClient;
import com.witt.doctor_miniroom.http.UrlInterface;
import com.witt.doctor_miniroom.mvpmoudle.contract.LoginContract;
import com.witt.doctor_miniroom.mvpmoudle.entitys.UserEntity;
import com.witt.doctor_miniroom.mvpmoudle.model.LoginModel;
import com.witt.doctor_miniroom.mvpmoudle.parameter.LoginParameter;
import com.witt.doctor_miniroom.utils.AppConfigUtils;
import com.witt.doctor_miniroom.view.ShakeEditText;

import java.util.HashMap;

/**
 * @ClassName: LoginPresenter
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/26 17:47
 * @Description:
 */
public class LoginPresenter extends BasePresenter<LoginContract.LoginView> implements LoginContract.LoginPresenter {
    LoginModel model;
    private int mEditTextHaveInputCount = 0;//EditText有内容的数量
    private final int EDITTEXT_AMOUNT = 3;//EditText总个数

    public LoginPresenter() {
        model = new LoginModel();
    }


    @Override
    public String getAccountStr(ShakeEditText editText) {
        if (StringUtils.isEmpty(editText.getText().toString().trim())) {
            editText.startAnimation();
            ToastUtils.showShort("账号不能为空");
        } else if (!RegexUtils.isMobileExact(editText.getText().toString().trim())) {
            editText.startAnimation();
            ToastUtils.showShort("手机号格式不正确");
        } else {
            return editText.getText().toString().trim();
        }
        return "";
    }

    @Override
    public String getPasswordStr(ShakeEditText editText) {
        if (StringUtils.isEmpty(editText.getText().toString().trim())) {
            editText.startAnimation();
            ToastUtils.showShort("请填写密码");
        } else {
            return editText.getText().toString().trim();
        }
        return "";
    }

    @Override
    public String getCodeStr(ShakeEditText editText) {
        if (StringUtils.isEmpty(editText.getText().toString().trim())) {
            editText.startAnimation();
            ToastUtils.showShort("请填写验证码");
        } else {
            return editText.getText().toString().trim();
        }
        return "";
    }

    @Override
    public void login(LoginParameter parameter) {
        mView.showLoading("加载中...");
        MyJoyHealthHttpClient.postJson(UrlInterface.doctorLogin(), parameter, new MYOKHttpCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    UserEntity entity = JSON.parseObject(result, UserEntity.class);
                    AppConfigUtils.saveUserInfo(entity);
                    //保存登录状态
                    AppConfigUtils.isLogin(true);
                    mView.showLoadSuccess("加载完成");
                    mView.loginJumping();
                } catch (Exception e) {

                }
            }

            @Override
            public void onError(int code, String message) {
                mView.showLoadFail("加载失败");
            }

            @Override
            public void onHttpError(Throwable e) {

            }

            @Override
            public void onFinished() {
            }
        });
    }

    @Override
    public void getCode(String phone) {
        HashMap<String,Object>map=new HashMap<>();
        map.put("phone",phone);
        MyJoyHealthHttpClient.postJson(UrlInterface.getLoginCodePort(), map, new MYOKHttpCallback() {
            @Override
            public void onSuccess(String result) {
                mView.startDownTime();
            }

            @Override
            public void onError(int code, String message) {

            }

            @Override
            public void onHttpError(Throwable e) {

            }

            @Override
            public void onFinished() {


            }
        });

    }

    @Override
    public void getFocusableLoginBtn(Context mContext, ShakeEditText editText, Button loginBtn) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /** EditText最初内容为空 改变EditText内容时 个数加一*/
                if (TextUtils.isEmpty(charSequence)) {
                    mEditTextHaveInputCount++;
                    Log.e("info", "mEditTextHaveInputCount=================" + mEditTextHaveInputCount);
                    /** 判断个数是否到达要求*/
                    if (mEditTextHaveInputCount == EDITTEXT_AMOUNT) {
                        loginBtn.setEnabled(true);
                        loginBtn.setTextColor(mContext.getResources().getColor(R.color.white));
                        loginBtn.setBackgroundResource(R.drawable.shape_rectangle_blue90);
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /** EditText内容改变之后 内容为空时 个数减一 按钮改为不可以的背景*/
                if (TextUtils.isEmpty(charSequence)) {
                    mEditTextHaveInputCount--;
                    loginBtn.setEnabled(false);
                    loginBtn.setTextColor(mContext.getResources().getColor(R.color.gray_99));
                    loginBtn.setBackgroundResource(R.drawable.shape_rectangle_blue90);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
