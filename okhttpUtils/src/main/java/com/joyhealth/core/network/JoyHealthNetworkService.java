package com.joyhealth.core.network;


import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Description :JoyHealth基于rxjava，rxandroid定义的retrofit接口
 * Created by wangjin on 2019-10-08.
 * Job number：
 * Phone ：18301070822
 * Email： 120182051@qq.com
 * Person in charge : 汪渝栋
 * Leader：
 */
public interface JoyHealthNetworkService {
    /**
     * get请求
     *
     * @param url
     * @param params
     * @return
     */
    @GET
    Observable<String> get(@Url String url, @QueryMap Map<String, Object> params);


    /**
     * get body形式
     *
     * @param url
     * @param body
     * @return
     */
    @POST
    Observable<String> getRaw(@Url String url, @Body RequestBody body);

    /**
     * post请求
     *
     * @param url
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<String> post(@Url String url, @FieldMap WeakHashMap<String, Object> params);

    /**
     * post请求 body形式
     *
     * @param url
     * @param body
     * @return
     */
    @POST
    Observable<String> postRaw(@Url String url, @Body RequestBody body);

    /**
     * put请求
     *
     * @param url
     * @param params
     * @return
     */
    @FormUrlEncoded
    @PUT
    Observable<String> put(@Url String url, @FieldMap WeakHashMap<String, Object> params);

    /**
     * put请求 body形式
     *
     * @param url
     * @param body
     * @return
     */
    @PUT
    Observable<String> putRaw(@Url String url, @Body RequestBody body);

    /**
     * delete请求
     *
     * @param url
     * @param params
     * @return
     */
    @DELETE
    Observable<String> delete(@Url String url, @QueryMap WeakHashMap<String, Object> params);

    /**
     * 下载文件
     * @param url
     * @param params
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url, @QueryMap WeakHashMap<String,Object> params);

    /**
     * 上传文件
     * @param url
     * @param file
     * @return
     */
    @Multipart
    @POST
    Observable<String> upload(@Url String url, @Part MultipartBody.Part file);
}
