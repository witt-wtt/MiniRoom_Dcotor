package com.joyhealth.core.network;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.NetworkUtils;
import com.joyhealth.core.JoyHealth;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import static io.reactivex.Observable.create;

/**
 * Description :JoyHealth http请求客户端
 * Created by wangjin on 2019-10-08.
 * Job number：
 * Phone ：18301070822
 * Email： 120182051@qq.com
 * Person in charge : 汪渝栋
 * Leader：
 */
public final class JoyHealthHttpClient {


    //网络连接断开错误码
    public static final String NO_NETWORK = "100001";

    //请求参数
    private final WeakHashMap<String, Object> mParams;

    //请求地址
    private final String url;

    //put raw,post raw 请求体
    private final RequestBody body;

    //显示上下文具体到fragment或者activity
    private final Context context;

    //上传文件
    private final File uploadFile;

    JoyHealthHttpClient(WeakHashMap<String, Object> mParams,
                        String url, RequestBody body,
                        File uploadFile,
                        Context context) {
        this.mParams = mParams;
        this.url = url;
        this.body = body;
        this.uploadFile = uploadFile;
        this.context = context;

    }

    /**
     * 获取RxRestClientBuilder实例
     *
     * @return
     */
    private static JoyHealthHttpClientBuilder getJoyHealthHttpClientBuilder() {
        return new JoyHealthHttpClientBuilder();
    }

    /**
     * 发起get请求
     */
    private final Observable<String> get() {
        if (body == null) {
            return request(HttpMethod.GET);
        } else {
            if (!mParams.isEmpty()) {
                throw new RuntimeException("params is must be null!");
            }
            return request(HttpMethod.GET_RAW);
        }
    }


    /**
     * 发起post/post raw请求
     *
     * @param url
     * @param params
     * @return
     */
    public static final Observable<String> post(String url, HashMap<String, ? extends Object> params) {
        return builderJoyHealthHttpClient(url, params).post()
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return msgToMessage(s);
                    }
                }).map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return formatResponseJson(s);
                    }
                });
    }

    /**
     * 发起get请求
     *
     * @param url
     * @param params
     * @return
     */
    public static final Observable<String> get(String url, HashMap<String, ? extends Object> params) {
        return builderJoyHealthHttpClient(url, params).get()
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return msgToMessage(s);
                    }
                }).map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return formatResponseJson(s);
                    }
                });
    }

    public static final Observable<String> upload(String url,String path){
        return builderJoyHealthHttpClient(url,path).upload().map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                return msgToMessage(s);
            }
        }).map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                return formatResponseJson(s);
            }
        });
    }


    /**
     * 发起post json请求
     *
     * @param url
     * @param params
     * @return
     */
    public static final Observable<String> postJson(String url, HashMap<String, ? extends Object> params) {
        JSONObject requestData = mapToJSONObject(params);
        Logger.d("postJson->requestData is " + requestData.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        return builderJoyHealthHttpClient(url, requestBody).post()
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return msgToMessage(s);
                    }
                }).map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return formatResponseJson(s);
                    }
                });
    }


    /**
     * 发起get json请求
     *
     * @param url
     * @param params
     * @return
     */
    public static final Observable<String> getJson(String url, HashMap<String, ? extends Object> params) {
        JSONObject requestData = mapToJSONObject(params);
        Logger.d("getJson->requestData is " + requestData.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        return builderJoyHealthHttpClient(url, requestBody).get()
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return msgToMessage(s);
                    }
                }).map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return formatResponseJson(s);
                    }
                });
    }


    private final Observable<String> post() {
        if (body == null) {
            return request(HttpMethod.POST);
        } else {
            if (!mParams.isEmpty()) {
                throw new RuntimeException("params is must be null!");
            }
            return request(HttpMethod.POST_RAW);
        }
    }

    /**
     * 创建JoyHealthHttpClient对象
     *
     * @param url
     * @param params
     * @return
     */
    private final static JoyHealthHttpClient builderJoyHealthHttpClient(String url, HashMap params) {
        return JoyHealthHttpClient.getJoyHealthHttpClientBuilder()
                .withUrl(url)
                .withParams(params)
                .build();
    }

    /**
     * 创建JoyHealthHttpClient对象
     *
     * @param url
     * @param path
     * @return
     */
    private final static JoyHealthHttpClient builderJoyHealthHttpClient(String url, String path) {
        return JoyHealthHttpClient.getJoyHealthHttpClientBuilder()
                .withUrl(url)
                .setUploadFile(path)
                .build();
    }

    /**
     * 创建JoyHealthHttpClient对象
     *
     * @param url
     * @param body
     * @return
     */
    private final static JoyHealthHttpClient builderJoyHealthHttpClient(String url, RequestBody body) {
        return JoyHealthHttpClient.getJoyHealthHttpClientBuilder()
                .withUrl(url)
                .withBody(body)
                .build();
    }

    /**
     * 发起put/put raw请求
     */
    public final Observable<String> put() {
        if (body == null) {
            return request(HttpMethod.PUT);
        } else {
            if (!mParams.isEmpty()) {
                throw new RuntimeException("params is must be null!");
            }
            return request(HttpMethod.PUT_RAW);
        }
    }

    /**
     * 发起delete请求
     */
    public final Observable<String> delete() {
        return request(HttpMethod.DELETE);
    }

    /**
     * 发起下载文件请求
     */
    public final Observable<ResponseBody> download() {
        return JoyHealthServiceCreator.getJoyHealthNetworkService().download(url, mParams);
    }

    /**
     * 发起上传文件请求
     */
    public final Observable<String> upload() {
        return request(HttpMethod.UPLOAD);
    }

    /**
     * 发起网络请求返回Observable被观察者对象
     *
     * @param method
     * @return
     */
    private Observable<String> request(HttpMethod method) {

        Observable<String> observable = null;

        //校验网络
        if (!NetworkUtils.isConnected()) {
            observable = Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                    emitter.onError(new Throwable(NO_NETWORK));
                }
            });
            return observable;
        }

        final JoyHealthNetworkService restService = JoyHealthServiceCreator.getJoyHealthNetworkService();

        switch (method) {
            case GET:
                observable = restService.get(url, mParams);
                break;
            case GET_RAW:
                observable = restService.getRaw(url, body);
                break;
            case POST:
                observable = restService.post(url, mParams);
                break;
            case POST_RAW:
                observable = restService.postRaw(url, body);
                break;
            case PUT:
                observable = restService.put(url, mParams);
                break;
            case PUT_RAW:
                observable = restService.putRaw(url, body);
                break;
            case DELETE:
                observable = restService.delete(url, mParams);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.
                        create(MediaType.parse(MultipartBody.FORM.toString()), uploadFile);
                final MultipartBody.Part body = MultipartBody.Part
                        .createFormData("file", uploadFile.getName(), requestBody);
                observable = restService.upload(url, body);
                break;
            default:
//                传递参数
//                final RequestBody requestBody1 = new MultipartBody.Builder()
//                        .setType(MultipartBody.FORM)
//                        .addFormDataPart("name","wangyudong")
//                        .addFormDataPart("file",uploadFile.getName(),RequestBody.create(MediaType.parse("image/*"),uploadFile)).build();

                break;
        }

        return observable;

    }


    /**
     * 将HashMap参数值转换成json对象
     *
     * @param params
     * @return
     */
    private static final JSONObject mapToJSONObject(HashMap<String, ? extends Object> params) {
        JSONObject jsonObject = new JSONObject();
        if (params != null) {
            for (String key : params.keySet()) {
                try {
                    jsonObject.put(key, params.get(key));
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return jsonObject;
    }


    /**
     * 把网络返回json中的msg替换成message
     *
     * @param json 服务器返回的json数据
     * @return
     */
    private static final String msgToMessage(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        Logger.d("onNext is111 : " + json);
        //适配msg,message两种返回形式，统一用message当适配信息
        if (jsonObject.containsKey("msg")) {
            Object message = jsonObject.get("msg");
            if (message instanceof String) {
                jsonObject.put("message", message);
                jsonObject.remove("msg");
            }
        }

        return jsonObject.toJSONString();

    }


    /**
     * 适配服务器返回json中[]和{}
     *
     * @param json
     * @return
     */
    private static final String formatResponseJson(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        Object data = jsonObject.get("data");


        //顶级结点data数据为[]
        if (data instanceof JSONArray) {
            JSONArray array = (JSONArray) data;
            if (array.size() == 0) {
                jsonObject.put("data", null);
            }
        }
        //顶级结点data数据为null
        else if (data instanceof JSONObject && data == null) {
            jsonObject.put("data", null);
        }
        //顶级结点data后续结点数据为[]
        else if (data instanceof JSONObject) {
            JSONObject subNode = (JSONObject) data;
            Set<Map.Entry<String, Object>> nodes = subNode.entrySet();
            //遍历data结点中的数据
            for (Map.Entry<String, Object> node : nodes) {
                Object nodeValue = node.getValue();
                //TODO wyd此部分代码还需要进一步抽离，与顶级data结点数据操作一致
                //如果结点类型为数组并且数组长度等于0，则代表返回值是[],与处理顶级data结点逻辑一致
                if (nodeValue instanceof JSONArray) {
                    JSONArray array = (JSONArray) nodeValue;
                    if (array.size() == 0) {
                        subNode.put(node.getKey(), null);
                    }
                }
            }
        }

        return jsonObject.toJSONString();
    }
}
