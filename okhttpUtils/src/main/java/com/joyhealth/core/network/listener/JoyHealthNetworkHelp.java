package com.joyhealth.core.network.listener;

import android.content.Context;
import android.net.NetworkInfo;
import android.os.Looper;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.InternetObservingSettings;
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.strategy.SocketInternetObservingStrategy;
import com.joyhealth.core.utils.JoyHealthPreference;
import com.orhanobut.logger.Logger;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Description :此类主要对Joyhealth中的网络变化进行控制，目前包含
 * 1、网络连接变化，监听现在网络是否能连接到指定主机
 * 2、网络状态变化，监听现在网络的状态，如：是否连接上、wifi、4g、2g等
 * Created by wangjin on 2019-10-15.
 * Job number：
 * Phone ：18301070822
 * Email： 120182051@qq.com
 * Person in charge : 汪渝栋
 * Leader：
 */
public final class JoyHealthNetworkHelp {

    private static NetworkStatusListener networkStatusListener;

    public void setNetworkStatusListener(NetworkStatusListener networkStatusListener) {
        this.networkStatusListener = networkStatusListener;
    }

    /**
     * 网络连接变化，监听现在网络是否能连接到指定主机
     *
     * @return
     */
    public static final void internetConnectionListener() {
        final int initialInterval = 10000;
        final int interval = 10000;
        final String host = "www.joyhealth.net";
        InternetObservingSettings settings = InternetObservingSettings.builder()
                .initialInterval(initialInterval)
                .interval(interval)
                .host(host)
                .strategy(new SocketInternetObservingStrategy())
                .build();

        ReactiveNetwork
                .observeInternetConnectivity(settings)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Logger.d("network is ：" + aBoolean.toString());

                        Looper.prepare();
                        if (NetworkUtils.getWifiEnabled() && NetworkUtils.isWifiConnected() && !aBoolean)
                            ToastUtils.showLong("当前网络异常!!");
                        Looper.loop();

                        JoyHealthPreference.putInternet(aBoolean);

                        if (networkStatusListener != null)
                            networkStatusListener.networkStatus(aBoolean);
                    }
                });
    }


    /**
     * 网络状态变化，监听现在网络的状态，如：是否连接上、wifi、4g、2g等
     *
     * @param context
     * @return
     */
    public static final void networkState(Context context) {
        ReactiveNetwork.observeNetworkConnectivity(context)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Connectivity>() {
                    @Override
                    public void accept(Connectivity connectivity) throws Exception {
                        final NetworkInfo.State state = connectivity.state();
                        final String name = connectivity.typeName();
                        Logger.d("NetworkInfo.State: %s, typeName: %s", state, name);
//                        Logger.d(NetworkInfo.State.CONNECTED == state);
                        JoyHealthPreference.putNetworkState(NetworkInfo.State.CONNECTED == state);

                        if (networkStatusListener != null)
                            networkStatusListener.networkStatus(NetworkInfo.State.CONNECTED == state);
                    }
                });
    }


    /**
     * 网络连接后断开的监听器
     */
    public interface NetworkStatusListener {
        void networkStatus(boolean status);
    }

}
