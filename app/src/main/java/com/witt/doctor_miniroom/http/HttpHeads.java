package com.witt.doctor_miniroom.http;

import com.blankj.utilcode.util.AppUtils;
import com.joyhealth.core.base.AbstractHeards;
import com.witt.doctor_miniroom.utils.TokenUtils;

import java.util.HashMap;

/**
 * @author zhangjinqi
 * @explain 初始化
 * @since 2020/5/8
 */
public class HttpHeads extends AbstractHeards {
    @Override
    public HashMap<String, String> setHashMapHeads() {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", TokenUtils.getToken());
        stringStringHashMap.put("version", AppUtils.getAppVersionName());
        stringStringHashMap.put("os_type","an");
        return stringStringHashMap;
    }
}
