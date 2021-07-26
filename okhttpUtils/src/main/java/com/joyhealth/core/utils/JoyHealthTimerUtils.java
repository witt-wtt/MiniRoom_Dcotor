package com.joyhealth.core.utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Description :定时器工具类，使用类中需要实现TimerCallback
 * Created by wangjin on 2019-12-19.
 * Job number：
 * Phone ：18301070822
 * Email： 120182051@qq.com
 * Person in charge : 汪渝栋
 * Leader：
 */
public class JoyHealthTimerUtils {

    public TimerCallback mTimerCallback;

    //定时器回调
    public interface TimerCallback{
        void timerTask();
    }

    public JoyHealthTimerUtils(TimerCallback mTimerCallback) {
        this.mTimerCallback = mTimerCallback;
    }

    public JoyHealthTimerUtils setTimerCallback(TimerCallback mTimerCallback) {
        this.mTimerCallback = mTimerCallback;
        return this;
    }

    /**
     * 延时执行回调
     * @param delay 延时间隔
     * @param unit 延时间隔的单位：时、分、秒
     */
    public void executeTimer(int delay, TimeUnit unit){
        //延时5秒根据获取的状态来显示界面
        Observable.timer(delay, unit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mTimerCallback.timerTask();
                    }
                });
    }

}
