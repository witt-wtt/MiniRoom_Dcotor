package com.joyhealth.core.network;

import android.content.Context;

import java.io.File;
import java.util.HashMap;
import java.util.WeakHashMap;

import okhttp3.RequestBody;

/**
 * Description :
 * Created by wangjin on 2019-10-08.
 * Job number：
 * Phone ：18301070822
 * Email： 120182051@qq.com
 * Person in charge : 汪渝栋
 * Leader：
 */
public final class JoyHealthHttpClientBuilder {

    //请求参数
    private WeakHashMap<String, Object> mParams = new WeakHashMap<>();

    //请求地址
    private String url = null;

    //put raw,post raw请求体
    private RequestBody body = null;

    //显示上下文具体到fragment或者activity
    private Context context = null;

    //上传文件
    private File uploadFile = null;

    protected JoyHealthHttpClientBuilder() {
    }

    public final JoyHealthHttpClientBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public final JoyHealthHttpClientBuilder withParams(HashMap params) {
        if (params!=null&& params.size()>0){
            this.mParams.putAll(params);
        }
        return this;
    }

    public final JoyHealthHttpClientBuilder setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
        return this;
    }

    public final JoyHealthHttpClientBuilder setUploadFile(String uploadFilePath) {
        this.uploadFile = new File(uploadFilePath);
        return this;
    }

    public final JoyHealthHttpClientBuilder withBody(RequestBody requestBody) {
        this.body = requestBody;
        return this;
    }

    public final JoyHealthHttpClient build() {
        return new JoyHealthHttpClient(mParams, url, body, uploadFile, context);
    }
}
