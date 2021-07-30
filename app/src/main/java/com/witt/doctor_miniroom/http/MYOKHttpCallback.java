package com.witt.doctor_miniroom.http;


/**
 * 公共的请求回调类
 * 
 * xiexucheng
 */
public interface MYOKHttpCallback {

	/**
	 * 通讯成功，返回正常的数据时回调的方法
	 * 
	 * @param result 返回信息
	 */
	void onSuccess(String result);

	/**
	 * 请求失败、拦截到错误等，回调的方法
	 * 
	 * @param message 提示信息
	 * * @param code 返回码
	 */
	void onError(int code, String message);


	void onHttpError(Throwable e);

	/**
	 * 请求结束回调的方法
	 */
	void onFinished();

}
