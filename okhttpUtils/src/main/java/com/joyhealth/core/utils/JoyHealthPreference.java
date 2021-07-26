package com.joyhealth.core.utils;

import android.graphics.Bitmap;
import android.util.Log;

import com.blankj.utilcode.util.CacheDiskUtils;
import com.blankj.utilcode.util.SPUtils;

/**
 * Description :app全局通用缓存信息
 * Created by wangjin on 2019-09-29.
 * Job number：
 * Phone ：18301070822
 * Email： 120182051@qq.com
 * Person in charge : 汪渝栋
 * Leader：
 */
public final class JoyHealthPreference {

    private static final String SP_NAME = "JoyHealth_SP";

    public static final String SP_INTERNET = "sp_internet";

    public static final String SP_NETWORK_STATE = "sp_network_state";

    public static final String SP_INTERNET_STATE = "sp_internet_state";

    public static final String SP_TOKEN = "sp_token";

    public static final String SP_APK_SAVE_PATH = "sp_apk_save_path";

    public static final String SP_APK_TASK_ID = "sp_apk_task_id";

    public static final String SP_WX_APPID = "sp_wx_appid";

    public static final String SP_WX_SECRET = "sp_wx_secret";

    public static final String SP_WX_URL = "sp_wx_url";

    public static final String SP_WX_TICKET = "sp_wx_ticket";

    public static final String SP_WX_ACCESS_TOKEN = "sp_wx_access_token";

    public static final String SP_LOGIN_QRCODE = "sp_login_qrcode";

    private static final String SAVE_QRCODE_TIME = "save_qrcode_time";

    private static final String SAVE_TICKET_TIME = "save_ticket_time";

    private static final String SP_NETWORK_DISCONNECTED = "sp_network_disconnected";


    private static SPUtils spUtils = SPUtils.getInstance(SP_NAME);


    /**
     * 网络是否可以对外通讯 true 可以 /false 不可以
     *
     * @return
     */
    public static boolean getInternet() {
        return spUtils.getBoolean(SP_INTERNET);
    }

    /**
     * 添加网络是否可以对外通讯标记
     *
     * @param value
     */
    public static void putInternet(boolean value) {
        spUtils.put(SP_INTERNET, value,true);
    }

    /**
     * 网络是否连接对外标记 true 已连接/ false 未连接
     *
     * @return
     */
    public static boolean getNetWorkState() {
        return spUtils.getBoolean(SP_NETWORK_STATE);
    }

    /**
     * 添加网络状态对外标记
     *
     * @param value
     */
    public static void putNetworkState(boolean value) {
        spUtils.put(SP_NETWORK_STATE, value,true);
    }


    /**
     * 添加网络连接对外标记
     *
     * @param value
     */
    public static void putInternetState(boolean value) {
        spUtils.put(SP_INTERNET_STATE, value,true);
    }

    /**
     * 获取token信息
     *
     * @return
     */
    public static String getToken() {
        return spUtils.getString(SP_TOKEN);
    }

    /**
     * 新增token信息
     *
     * @param value
     */
    public static void putToken(String value) {
        spUtils.put(SP_TOKEN, value,true);
    }


    /**
     * 获取apk下载后保存的路径
     *
     * @return
     */
    public static String getApkSavePath() {
        return spUtils.getString(SP_APK_SAVE_PATH);
    }

    /**
     * 存放下载apk的路径
     *
     * @param value
     */
    public static void putApkSavePath(String value) {
        spUtils.put(SP_APK_SAVE_PATH, value,true);
    }

    /**
     * 获取下载apk的任务id
     */
    public static long getApkTaskId(){
        return spUtils.getLong(SP_APK_TASK_ID);
    }

    /**
     * 存放下载apk的任务id
     * @param id
     */
    public static void putApkTaskId(long id){
        spUtils.put(SP_APK_TASK_ID, id,true);
    }

    public static void clearApkTaskId(){
        spUtils.put(SP_APK_TASK_ID,-1L);
    }

    /**
     * 获取微信ticket
     *
     * @return
     */
    public static String getWxTicket() {
        return spUtils.getString(SP_WX_TICKET);
    }

    /**
     * 存放微信ticket
     *
     * @param value
     */
    public static void putWxTicket(String value) {
        spUtils.put(SP_WX_TICKET, value,true);
    }

    /**
     * 清空Ticket
     */
    public static void clearTicket(){
        spUtils.put(SP_WX_TICKET, "");
    }


    /**
     * 获取微信access token
     *
     * @return
     */
    public static String getWxAccessToken() {
        return spUtils.getString(SP_WX_ACCESS_TOKEN);
    }

    /**
     * 存放微信access token
     *
     * @param value
     */
    public static void putWxAccessToken(String value) {
        spUtils.put(SP_WX_ACCESS_TOKEN, value,true);
    }

    /**
     * 清空access token
     */
    public static void clearWxAccessToken(){
        spUtils.put(SP_WX_ACCESS_TOKEN, "");
    }


    /**
     * 保存微信登录二维码
     *
     * @return
     */
    public static void saveLoginQrcode(byte[] bitmap) {
        CacheDiskUtils.getInstance().put(SP_LOGIN_QRCODE, bitmap);
    }

    /**
     * 获取微信登录二维码
     * @return
     */
    public static byte[] getLoginQrcode(){
       return CacheDiskUtils.getInstance().getBytes(SP_LOGIN_QRCODE);
    }

    /**
     * 清除登录qrcode
     */
    public static boolean clearLoginQrcode() {
       return CacheDiskUtils.getInstance().remove(SP_LOGIN_QRCODE);
    }


    /**
     * 保存qrcode time信息
     *
     * @param saveTime
     */
    public static void saveQrcodeTime(long saveTime) {
        spUtils.put(SAVE_QRCODE_TIME,saveTime);
    }

    /**
     * 获取qrcode time信息
     *
     * @return
     */
    public static long getQrcodeTime() {
        return spUtils.getLong(SAVE_QRCODE_TIME);
    }


    /**
     * 保存ticket time信息
     *
     * @param saveTime
     */
    public static void saveTicketTime(long saveTime) {
        spUtils.put(SAVE_TICKET_TIME,saveTime);
    }

    /**
     * 获取ticket time信息
     *
     * @return
     */
    public static long getTicketTime() {
        return spUtils.getLong(SAVE_TICKET_TIME);
    }


    /**
     * 保存网络是否断开过连接
     *
     * @param status
     */
    public static void saveDisconnectedStatus(boolean status) {
        spUtils.put(SP_NETWORK_DISCONNECTED,status);
    }

    /**
     * 获取ticket time信息
     *
     * @return
     */
    public static boolean getDisconnectedStatus() {
        return spUtils.getBoolean(SP_NETWORK_DISCONNECTED);
    }

}
