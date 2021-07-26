package com.joyhealth.core;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.joyhealth.core.app.IJoyHealthHandlerListener;
import com.joyhealth.core.app.JoyHealthConfigurator;
import com.joyhealth.core.base.AbstractHeards;
import com.joyhealth.core.base.AbstractHttpParameters;
import com.joyhealth.core.network.listener.JoyHealthNetworkHelp;
import com.joyhealth.core.utils.JoyHealthPreference;

import javax.net.ssl.SSLContext;

/**
 * Description :核心库全局配置控制类
 * Created by wangjin on 2019-09-29.
 * Job number：
 * Phone ：18301070822
 * Email： 120182051@qq.com
 * Person in charge : 汪渝栋
 * Leader：
 */
public final class JoyHealth {

    private static IJoyHealthHandlerListener joyHealthHandlerListener;

    //全局Handler
    private static final Handler HANDLER = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (joyHealthHandlerListener == null) {
                throw new IllegalArgumentException("Context must be register IJoyHealthHandlerListener！！");
            }

            joyHealthHandlerListener.handleMessage(msg);
        }
    };


    private static Builder builder() {
        return new JoyHealth.Builder();
    }


    public JoyHealth(Builder builder) {
    }


    /**
     * 获取全局配置
     *
     * @return
     */
    public static JoyHealthConfigurator getJoyHealthConfigurator() {
        return JoyHealthConfigurator.getInstance();
    }

    /**
     * 获取应用上下文
     *
     * @return
     */
    public static Context getApplicationContext() {
        return getConfigurationValue(JoyHealthConfigurator.APP_CONTEXT);
    }

    /**
     * 获取api host
     *
     * @return
     */
    public static String getApiHost() {
        return getConfigurationValue(JoyHealthConfigurator.API_HOST);
    }

    /**
     * 获取api heads 配置类
     *
     * @return
     */
    public static AbstractHeards getAppHeads() {
        return getConfigurationValue(JoyHealthConfigurator.APP_HEADS);
    }


    /**
     * 获取api 网络请求其他参数 配置类
     *
     * @return
     */
    public static AbstractHttpParameters getHttpParameters() {
        return getConfigurationValue(JoyHealthConfigurator.APP_PARAMETERS);
    }
    /**
     * 获取api ssl
     *
     * @return
     */
    public static String getSSL() {
        return getConfigurationValue(JoyHealthConfigurator.APP_SSL);
    }


    /**
     * 获取api ssl_bks
     *
     * @return
     */
    public static SSLContext getSSL_bks() {
        return JoyHealthConfigurator.mSslContext;
    }


    /**
     * 检查网络是否连接及可用
     *
     * @return
     */
    public static boolean checkNetworkConnection() {
//        if (JoyHealthPreference.getInternet() && JoyHealthPreference.getNetWorkState()) {
        if (JoyHealthPreference.getNetWorkState()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 监听网络异常服务初始化，在指定范围时间类动态ping服务器
     */
    public static void startInternetService(){
        JoyHealthNetworkHelp.internetConnectionListener();
//        JoyHealthNetworkHelp.networkState(JoyHealth.getApplicationContext());
    }

    /**
     * 根据key获取配置信息
     *
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getConfigurationValue(Object key) {
        return getJoyHealthConfigurator().getConfiguretion(key);
    }

    /**
     * 获取app全局Handler
     * （如果主界面需要使用Handler进行消息回传，这必须实现IJoyHealthHandlerListener监听，并进行设置）
     *
     * @return
     */
    public static Handler getHandler(IJoyHealthHandlerListener listener) {
        joyHealthHandlerListener = listener;
        return HANDLER;
    }


    /**
     * 设置全局Handler消息监听
     *
     *
     * @param listener
     */
//    public void setJoyHealthHandlerListener(IJoyHealthHandlerListener listener) {
//        joyHealthHandlerListener = listener;
//    }

    /**
     * 设置日志
     *
     * @param isDebug
     * @return
     */
    public static Builder withLogger(boolean isDebug) {
        return builder().withLogger(isDebug);
    }

    /**
     * 设置api host
     *
     * @param host
     * @return
     */
    public static Builder withApiHost(String host) {
        return builder().withApiHost(host);
    }


    /**
     * 配置activity崩溃监听
     *
     * @return
     */
    public Builder withActivityCrash(boolean isDebug) {
        return builder().withActivityCrash(isDebug);
    }

    /**
     * 配置application Context
     *
     * @param context
     * @return
     */
    public static Builder withAppContext(Context context) {
        return builder().withAppContext(context);
    }

    //JoyHealth 建造器
    public final static class Builder {

        public Builder withLogger(boolean isDebug) {
            getJoyHealthConfigurator().withLogger(isDebug);
            return this;
        }

        public Builder withApiHost(String host) {
            getJoyHealthConfigurator().withApiHost(host);
            return this;
        }

        public Builder withAppContext(Context context) {
            getJoyHealthConfigurator().withAppContext(context);
            return this;
        }

        public Builder withActivityCrash(boolean isDebug) {
            getJoyHealthConfigurator().withActivityCrash(isDebug);
            return this;
        }
        public Builder withHttpHeads(AbstractHeards abstractHeards) {
            getJoyHealthConfigurator().withAppHeads(abstractHeards);
            return this;
        }

        public Builder withParamaters(AbstractHttpParameters abstractHttpParameters) {
            getJoyHealthConfigurator().withAppParameters(abstractHttpParameters);
            return this;
        }

        public Builder withSSL(String json){
            getJoyHealthConfigurator().withAppSSL(json);
            return this;
        }

        public Builder withSSL_bks(SSLContext sslContexts){
            getJoyHealthConfigurator().withAppSSL_bks(sslContexts);
            return this;
        }


        public JoyHealth build() {
            return new JoyHealth(this);
        }
    }




}
