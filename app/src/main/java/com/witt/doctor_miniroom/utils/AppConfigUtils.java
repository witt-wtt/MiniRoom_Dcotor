package com.witt.doctor_miniroom.utils;

import com.blankj.utilcode.util.SPUtils;
import com.witt.doctor_miniroom.mvpmoudle.entitys.UserEntity;

/**
 * @ClassName: AppConfigUtils
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/27 15:23
 * @Description: app配置信息
 */
public class AppConfigUtils {
    private static final String SP_USER_INFO = "UserInfo";
    private static final String USER_ID = "user_id";
    private static final String USER_PHONE = "user_phone";
    private static final String LOGIN_STATUS = "login_status";
    public static final String LAST_FRAGMENT = "lastVisibleFragment";
    public static final String ROOM_ID = "room_id";
    public static final String JP_RID = "RegistrationID";
    private static SPUtils spUtils;

    public static void initSP() {
        spUtils = SPUtils.getInstance(SP_USER_INFO);
    }

    public static void saveUserInfo(UserEntity entity) {
        spUtils.put(USER_ID, entity.getData().getId(), true);
        spUtils.put(USER_PHONE, entity.getData().getPhone(), true);
    }

    public static void saveRegistrationID(String registrationID) {
        spUtils.put(JP_RID, registrationID, true);
    }

    public static String getRegistrationID() {
        return spUtils.getString(JP_RID);
    }

    public static void clearData(){
        spUtils.clear();
    }

    public static void isLogin(boolean isLogin) {
        spUtils.put(LOGIN_STATUS, isLogin, true);
    }

    public static boolean getLoginStatus() {
        return spUtils.getBoolean(LOGIN_STATUS);
    }

    public static String getUserId() {
        return spUtils.getString(USER_ID);
    }

    public static String getCountTimeByLong(long finishTime) {
        int totalTime = (int) (finishTime / 1000);//秒
        int hour = 0, minute = 0, second = 0;

        if (3600 <= totalTime) {
            hour = totalTime / 3600;
            totalTime = totalTime - 3600 * hour;
        }
        if (60 <= totalTime) {
            minute = totalTime / 60;
            totalTime = totalTime - 60 * minute;
        }
        if (0 <= totalTime) {
            second = totalTime;
        }
        StringBuilder sb = new StringBuilder();

        if (hour < 10) {
            sb.append("0").append(hour).append(":");
        } else {
            sb.append(hour).append(":");
        }
        if (minute < 10) {
            sb.append("0").append(minute).append(":");
        } else {
            sb.append(minute).append(":");
        }
        if (second < 10) {
            sb.append("0").append(second);
        } else {
            sb.append(second);
        }
        return sb.toString();

    }
}
