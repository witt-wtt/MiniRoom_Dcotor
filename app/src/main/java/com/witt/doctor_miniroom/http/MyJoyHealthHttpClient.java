package com.witt.doctor_miniroom.http;




import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.joyhealth.core.network.JoyHealthHttpClient;
import com.witt.doctor_miniroom.utils.DataUtils;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * @author zhangjinqi
 * @explain 网络请求封装
 * @since 2019-10-15
 */
public class MyJoyHealthHttpClient {
    /**
    *post 请求，页面消失网络被清理，传递对象
    *@author zhangjinqi
    *create at 2020/10/23 11:20 AM
    */
    public static void postJson(HttpInterface httpInterface, String url, Object object, MYOKHttpCallback myokHttpCallback){
        postJson(httpInterface,url, DataUtils.getObjAttrToMap(object),false,myokHttpCallback);
    }

    /**
     *post 请求，页面消失网络被清理，传递Map
     *@author zhangjinqi
     *create at 2020/10/23 11:20 AM
     */
    public static void postJson(HttpInterface httpInterface, String url, HashMap<String,Object> map, MYOKHttpCallback myokHttpCallback){
            if(null==map){
                map=new HashMap<String,Object>();
            }
        postJson(httpInterface,url,map,false,myokHttpCallback);
    }

    /**
    *普通post请求，传递对象
    *@author zhangjinqi
    *create at 2020/10/23 11:21 AM
    */
    public static void postJson(String url, Object object, MYOKHttpCallback myokHttpCallback){
        postJson(null,url,DataUtils.getObjAttrToMap(object),false,myokHttpCallback);
    }


    /**
    *普通post请求，传递map
    *@author zhangjinqi
    *create at 2020/10/23 11:21 AM
    */
    public static void postJson(String url, HashMap<String,Object> map, MYOKHttpCallback myokHttpCallback){
        if(null==map){
            map=new HashMap<String,Object>();
        }
        postJson(null,url,map,false,myokHttpCallback);
    }

    private static void postJson(HttpInterface httpInterface, String url, HashMap<String, ? extends Object> params, boolean isIoOut, MYOKHttpCallback myokHttpCallback){
        if(httpInterface!=null){
            httpInterface.showHttpLoading();
        }
        Scheduler scheduler;
        if(isIoOut){
            scheduler= Schedulers.io();
        }else {
            scheduler= AndroidSchedulers.mainThread();
        }
        LogUtils.d("PostUrl:___",url + params.toString());
        JoyHealthHttpClient.postJson(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if(httpInterface!=null){
                            httpInterface.addDisposable(d);
                        }
                    }

                    @Override
                    public void onNext(String s) {
                        if(httpInterface!=null){
                            httpInterface.dissMissLoading();
                        }
                        LogUtils.d("onNext",url+s);

                        if(myokHttpCallback!=null){
                            OnNomalData(s,myokHttpCallback);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(httpInterface!=null){
                            httpInterface.dissMissLoading();
                        }
                        LogUtils.d("onNext is :___",url + e.toString());
                        if(myokHttpCallback!=null){
                            myokHttpCallback.onFinished();
                        }
                        if(null!=e){
                            LogUtils.e("",e.getMessage());
                            if(myokHttpCallback!=null){
                                myokHttpCallback.onHttpError(e);
                            }
                        }


                    }

                    @Override
                    public void onComplete() {
                        if(myokHttpCallback!=null){
                            myokHttpCallback.onFinished();
                        }
                    }
                });

    }


    private static void  getJson(HttpInterface httpInterface, String url, HashMap<String, ? extends Object> params, boolean isIoOut, MYOKHttpCallback myokHttpCallback){

        if(httpInterface!=null){
            httpInterface.showHttpLoading();
        }
        Scheduler scheduler;
        if(isIoOut){
            scheduler=Schedulers.io();
        }else {
            scheduler=AndroidSchedulers.mainThread();
        }
        LogUtils.d("getUrl:",url+params.toString());
        JoyHealthHttpClient.get(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if(httpInterface!=null){
                            httpInterface.addDisposable(d);
                        }

                    }

                    @Override
                    public void onNext(String s) {
                        if(myokHttpCallback!=null){
                            OnNomalData(s,myokHttpCallback);
                        }
                        if(httpInterface!=null){
                            httpInterface.dissMissLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(httpInterface!=null){
                            httpInterface.dissMissLoading();
                        }
                        if(myokHttpCallback!=null){
                            myokHttpCallback.onFinished();
                        }
                        if(null!=null){
                            LogUtils.e("http", e.getMessage());
                            if(myokHttpCallback!=null){
                                myokHttpCallback.onHttpError(e);
                                myokHttpCallback.onError(80000,"");
                            }
                        }

                    }

                    @Override
                    public void onComplete() {
                        if(myokHttpCallback!=null){
                            myokHttpCallback.onFinished();
                        }
                    }
                });
    }

    /**
    *get方式 传递对象
    *@author zhangjinqi
    *create at 2020/10/23 2:33 PM
    */
    public static void  getJsonForBean(String url, Object object,MYOKHttpCallback myokHttpCallback){
        getJson(null,url,DataUtils.getObjAttrToMap(object),false,myokHttpCallback);
    }

    /**
    *get方式传递map
    *@author zhangjinqi
    *create at 2020/10/23 2:33 PM
    */
    public static void  getJson(String url, HashMap<String,? extends Object> map,MYOKHttpCallback myokHttpCallback){
        getJson(null,url,map,false,myokHttpCallback);
    }

    /**
     *get方式 传递对象 页面消失网络被清理
     *@author zhangjinqi
     *create at 2020/10/23 2:33 PM
     */
    public static void  getJsonForBean(HttpInterface httpInterface, String url, Object object, MYOKHttpCallback myokHttpCallback){
        getJson(httpInterface,url,DataUtils.getObjAttrToMap(object),false,myokHttpCallback);
    }

    /**
     *get方式传递map 页面消失网络被清理
     *@author zhangjinqi
     *create at 2020/10/23 2:33 PM
     */
    public static void  getJson(HttpInterface httpInterface, String url, HashMap<String,? extends Object> map, MYOKHttpCallback myokHttpCallback){
        getJson(httpInterface,url,map,false,myokHttpCallback);
    }




    public static void  get(HttpInterface httpInterface, String url, HashMap<String, ? extends Object> params, MYOKHttpCallback myokHttpCallback){
        if(httpInterface!=null){
            httpInterface.showHttpLoading();
        }
        JoyHealthHttpClient.get(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String s) {
                        if(httpInterface!=null){
                            httpInterface.dissMissLoading();
                        }
                        myokHttpCallback.onSuccess(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(httpInterface!=null){
                            httpInterface.dissMissLoading();
                        }
                        if(myokHttpCallback!=null){
                            myokHttpCallback.onFinished();
                        }
                        if(myokHttpCallback!=null){
                            if(null!=null){
                                LogUtils.e("http", e.getMessage());
                                myokHttpCallback.onHttpError(e);
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        if(myokHttpCallback!=null){
                            myokHttpCallback.onFinished();
                        }
                    }
                });
    }

    private static void OnNomalData(String s,MYOKHttpCallback myokHttpCallback){
//        Logger.d("onNext is " + s);
        try {
           BaseHttpBean baseHttpBean = JSON.parseObject(s, BaseHttpBean.class);
            if(baseHttpBean.getCode()==10000){
                if(myokHttpCallback!=null){
                    myokHttpCallback.onSuccess(s);
                }
            }else {
                //
                if(baseHttpBean.getCode()==CANDISPLAY){
                    ToastUtils.showLong(baseHttpBean.getMessage()+"");
                    //跳转登陆
                }
                if(myokHttpCallback!=null){
                    myokHttpCallback.onError(baseHttpBean.getCode(),baseHttpBean.getMessage());
                }
            }


        } catch (Exception e) {
            if(myokHttpCallback!=null){
                myokHttpCallback.onError(300,"");
            }
        }
    }

    public static void  upload(String url,String path,MYOKHttpCallback myokHttpCallback){
        JoyHealthHttpClient.upload(url,path).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String s) {
                        myokHttpCallback.onSuccess(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(myokHttpCallback!=null){
                            myokHttpCallback.onFinished();
                        }
                        if(null!=e){
                            LogUtils.e("http", e.getMessage());
                            if(myokHttpCallback!=null){
                                myokHttpCallback.onHttpError(e);
                            }
                        }

                    }

                    @Override
                    public void onComplete() {
                        if(myokHttpCallback!=null){
                            myokHttpCallback.onFinished();
                        }
                    }
                });
    }

    public static final int CANDISPLAY=402;  //可显示接口message
    public static final int TOKENNOTHINGNESS=403;  //token 不存在
    public static final int TOKENERROR=302;   //token错误
}
