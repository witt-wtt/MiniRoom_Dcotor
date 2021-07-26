package com.joyhealth.core.network;

import com.joyhealth.core.JoyHealth;
import com.joyhealth.core.base.AbstractHeards;
import com.joyhealth.core.base.AbstractHttpParameters;
import com.joyhealth.core.utils.JoyHealthPreference;
import com.joyhealth.core.utils.RxUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Description : 初始化Retrofit service 相关对象
 * Created by wangjin on 2019-10-08.
 * Job number：
 * Phone ：18301070822
 * Email： 120182051@qq.com
 * Person in charge : 汪渝栋
 * Leader：
 */
public final class JoyHealthServiceCreator {

    /**
     * 创建OKHttp
     */
    private static final class OKHttpHolder {
        private static final int TIME_OUT = 60;
        private static final OkHttpClient OK_HTTP_CLIENT  = new OkHttpClient.Builder().sslSocketFactory(JoyHealth.getSSL_bks().getSocketFactory(),new RxUtils.X509TrustManagers()) .addInterceptor(
                new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        return chain.proceed(addParameter(addHeads(chain)));
                    }
                }
        )
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

//        private static final OkHttpClient OK_HTTP_CLIENT  = new OkHttpClient.Builder().sslSocketFactory(RxUtils.createSSLSocketFactory()).hostnameVerifier(new RxUtils.TrustAllHostnameVerifier()) .addInterceptor(
//                new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//
//                        return chain.proceed(addParameter(addHeads(chain)));
//                    }
//                }
//        )
//                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
//                .build();

    }


    /**
     * 创建Retrofit
     */
    private static final class RetrofitHolder {
        private static final String BASE_URL = JoyHealth.getApiHost();
        private static final Retrofit RETROFIT =
                new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(OKHttpHolder.OK_HTTP_CLIENT)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
    }

    /**
     * 创建service
     */
    public static final class JoyHealthNetworkServiceHolder {
        private static final JoyHealthNetworkService REST_SERVICE =
                RetrofitHolder.RETROFIT.create(JoyHealthNetworkService.class);
    }

    /**
     * 获取service
     */
    public static JoyHealthNetworkService getJoyHealthNetworkService() {
        return JoyHealthNetworkServiceHolder.REST_SERVICE;
    }
    //添加heads
    private static Request addHeads(Interceptor.Chain chain){
        AbstractHeards appHeads = JoyHealth.getAppHeads();
        Request request;
        if(null!=appHeads){
            request = chain.request().newBuilder().headers(Headers.of(appHeads.setHashMapHeads())).build();
        }else {
            request = chain.request().newBuilder().build();
        }
        return request;
    }

    /**
     *添加http请求参数
     *@author zhangjinqi
     *create at 2020/5/8 4:51 PM
     */
    private static Request addParameter(Request request){
        AbstractHttpParameters httpParameters = JoyHealth.getHttpParameters();

        if(httpParameters!=null){
            //添加新参数
            HttpUrl.Builder builder = request.url().newBuilder()
                    .scheme(request.url().scheme())
                    .host(request.url().host());
            HashMap<String, String> httpParametersMap = httpParameters.setHashParameters();
            Iterator<Map.Entry<String, String>> entries = httpParametersMap.entrySet().iterator();
            while(entries.hasNext()){
                Map.Entry<String, String> entry = entries.next();
                builder.addQueryParameter(entry.getKey(),entry.getValue());
            }
            //新请求
            Request newRequest = request.newBuilder()
                    .method(request.method(), request.body())
                    .url(builder.build())
                    .build();
            return newRequest;
        }else {
            return request;
        }

    }

}
