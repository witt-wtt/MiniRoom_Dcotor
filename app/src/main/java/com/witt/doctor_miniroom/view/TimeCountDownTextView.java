package com.witt.doctor_miniroom.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.witt.doctor_miniroom.R;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @ClassName: TimeCountDownTextView
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/21 14:20
 * @Description:自定义倒计时控件
 */
@SuppressLint("ViewConstructor")
public class TimeCountDownTextView extends AppCompatTextView {
    private CountDownTimer mTimer = null;
    private String mCss;
    private long mCountDownTime;

    private onCountDownFinishListener mOnCountDownFinishListener;
    private onCountDownTickListeren onCountDownTickListeren;

    public TimeCountDownTextView(@NonNull Context context) {
        super(context);
    }

    public TimeCountDownTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initCountDownTime(context, attrs);
    }

    public TimeCountDownTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCountDownTime(context, attrs);
    }

    @SuppressLint({"Recycle", "CustomViewStyleable"})
    private void initCountDownTime(Context context, AttributeSet attrs) {
        TypedArray attribute = context.obtainStyledAttributes(attrs, R.styleable.TimeCountDownView);
        mCountDownTime = (long) attribute.getFloat(R.styleable.TimeCountDownView_countDownTime, 0);
        mCss = attribute.getString(R.styleable.TimeCountDownView_count_down_format);
        if (TextUtils.isEmpty(mCss)) {
            mCss = getContext().getString(R.string.count_down_default_format);
        }
    }

    public void setCountDownTimes(long countDownTime, String cssResId) {
        if (!TextUtils.isEmpty(cssResId)) {
            this.mCss = cssResId;
        }
        mCountDownTime = countDownTime;
    }

    public void setCountDownTimes(long countDownTime) {
        mCountDownTime = countDownTime;
    }

    @SuppressLint("SimpleDateFormat")
    public void start() {
        if (mCountDownTime < 0) {
            mCountDownTime = 0;
        }
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            int countDownInterval = 1000;
            mTimer = new CountDownTimer(mCountDownTime, countDownInterval) {
                @Override
                public void onTick(long millisUntilFinished) {
//                    mMinute = millisUntilFinished / (1000 * 60);
//                    mSecond = (millisUntilFinished % (1000 * 60)) / 1000;
//                    TimeCountDownTextView.this.setText(Html.fromHtml(String.format(mCss, mMinute, mSecond)));
                    Date date = new Date(millisUntilFinished);
                    SimpleDateFormat format = new SimpleDateFormat(mCss);
                    TimeCountDownTextView.this.setText(format.format(date));
                    if (onCountDownTickListeren != null) {
                        onCountDownTickListeren.onMTick(millisUntilFinished);
                    }
                }

                @Override
                public void onFinish() {
                    if (mOnCountDownFinishListener != null) {
                        mOnCountDownFinishListener.onFinish();
                    }
                }
            };
        }
        mTimer.start();
    }

    public void stopTime() {
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    public void setOnCountDownFinishListener(onCountDownFinishListener onCountDownFinishListener) {
        this.mOnCountDownFinishListener = onCountDownFinishListener;
    }

    public void setOnCountDownTickListeren(onCountDownTickListeren onCountDownTickListeren) {
        this.onCountDownTickListeren = onCountDownTickListeren;
    }

    public interface onCountDownFinishListener {
        void onFinish();
    }

    public interface onCountDownTickListeren {
        void onMTick(long time);
    }

}
