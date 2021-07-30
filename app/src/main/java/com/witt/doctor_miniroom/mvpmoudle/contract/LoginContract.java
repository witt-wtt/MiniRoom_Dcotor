package com.witt.doctor_miniroom.mvpmoudle.contract;

import android.content.Context;
import android.widget.Button;

import com.witt.doctor_miniroom.baseview.BaseView;
import com.witt.doctor_miniroom.mvpmoudle.parameter.LoginParameter;
import com.witt.doctor_miniroom.view.ShakeEditText;

/**
 * @ClassName: LoginContract
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/26 17:46
 * @Description:
 */
public interface LoginContract {
    interface LoginView extends BaseView {
        void loginJumping();

        void startDownTime();
    }

    interface LoginPresenter {
        String getAccountStr(ShakeEditText editText);

        String getPasswordStr(ShakeEditText editText);

        String getCodeStr(ShakeEditText editText);
        void login(LoginParameter parameter);

        void getCode(String phone);
        void getFocusableLoginBtn(Context mContext, ShakeEditText editText, Button loginBtn);

    }

    interface LoginModel {



    }
}
