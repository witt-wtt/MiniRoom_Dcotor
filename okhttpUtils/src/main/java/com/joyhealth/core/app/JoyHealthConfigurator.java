package com.joyhealth.core.app;

import android.content.Context;

import com.joyhealth.core.base.AbstractHeards;
import com.joyhealth.core.base.AbstractHttpParameters;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.HashMap;

import javax.net.ssl.SSLContext;

import cat.ereza.customactivityoncrash.config.CaocConfig;

/**
 * Description :app全局通用配置类
 * Created by wangjin on 2019-09-29.
 * Job number：
 * Phone ：18301070822
 * Email： 120182051@qq.com
 * Person in charge : 汪渝栋
 * Leader：
 */
public class JoyHealthConfigurator {

    private static final String LOGGER_TAG = "JoyHealth";

    //配置项初始化是否成功
    public static final String CONFIG_IS_READY = "config_is_ready";

    //配置网络连接host地址
    public static final String API_HOST = "api_host";

    //配置App Context
    public static final String APP_CONTEXT = "app_context";
    //配置app网络请求heads
    public static final String APP_HEADS="app_heads";

    //配置app网络请求其他参数
    public static final String APP_PARAMETERS="app_parameters";
    public static final String APP_SSL = "app_SSL";
    public static SSLContext mSslContext;

    //配置项缓存
    private static final HashMap<Object, Object> GLOBAL_CONFIGS = new HashMap<>();

    protected JoyHealthConfigurator() {
        GLOBAL_CONFIGS.put(CONFIG_IS_READY, false);
    }

    private static class JoyHealthConfiguratorInstance {
        static final JoyHealthConfigurator INSTANCE = new JoyHealthConfigurator();
    }

    public static JoyHealthConfigurator getInstance() {
        return JoyHealthConfiguratorInstance.INSTANCE;
    }

    /**
     * 配置logger日志输出
     *
     * @param isDebug debug模式显示日志，否则不显示日志
     * @return
     */
    public final JoyHealthConfigurator withLogger(boolean isDebug) {
        if (isDebug) {
            FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                    .tag(LOGGER_TAG)
                    .showThreadInfo(true)
                    .methodOffset(0)
                    .build();
            Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        } else {
            FormatStrategy formatStrategy = CsvFormatStrategy.newBuilder()
                    .tag(LOGGER_TAG)
                    .build();
            Logger.addLogAdapter(new DiskLogAdapter(formatStrategy));
        }
        return this;
    }

    /**
     * 配置host
     *
     * @param host
     * @return
     */
    public final JoyHealthConfigurator withApiHost(String host) {
        GLOBAL_CONFIGS.put(API_HOST, host);
        return this;
    }

    /**
     * 配置application Context
     *
     * @param context
     * @return
     */
    public final JoyHealthConfigurator withAppContext(Context context) {
        GLOBAL_CONFIGS.put(APP_CONTEXT, context);

        return this;
    }
    /**
     * 配置application heards对象
     *
     * @param abstractHeards
     * @return
     */
    public final JoyHealthConfigurator withAppHeads(AbstractHeards abstractHeards) {
        GLOBAL_CONFIGS.put(APP_HEADS, abstractHeards);
        return this;
    }

    public final JoyHealthConfigurator withAppSSL(String json){
        GLOBAL_CONFIGS.put(APP_SSL, json);
        return this;
    }

    public final JoyHealthConfigurator withAppSSL_bks(SSLContext sslContext){
        mSslContext=sslContext;
        return this;
    }

    /**
     *配置application http其他参数
     *@author zhangjinqi
     *create at 2020/5/8 4:39 PM
     */
    public final JoyHealthConfigurator withAppParameters(AbstractHttpParameters abstractHttpParameters) {
        GLOBAL_CONFIGS.put(APP_PARAMETERS, abstractHttpParameters);
        return this;
    }
    /**
     * 配置activity崩溃监听
     *
     * @return
     */
    public final JoyHealthConfigurator withActivityCrash(boolean isDebug) {
        if (isDebug){
            CaocConfig.Builder.create().apply();
        }
        return this;
    }

    /**
     * 根据key值获取配置信息
     *
     * @param key
     * @param <T>
     * @return
     */
    public final <T> T getConfiguretion(Object key) {
        return (T) GLOBAL_CONFIGS.get(key);
    }


    /**
     * 校验配置是否完成
     */
//    public static final void checkConfigIsReady() {
//        final boolean isReady = (boolean) GLOBAL_CONFIGS.get(CONFIG_IS_READY);
//        if (!isReady) {
//            throw new RuntimeException("配置未完成!!!");
//        }
//    }

}
