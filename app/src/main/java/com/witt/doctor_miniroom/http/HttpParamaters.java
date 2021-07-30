package com.witt.doctor_miniroom.http;

import com.blankj.utilcode.util.AppUtils;
import com.joyhealth.core.base.AbstractHttpParameters;
import com.witt.doctor_miniroom.utils.TokenUtils;

import java.util.HashMap;

/**
 * @author zhangjinqi
 * @explain 网络请求，附带参数bean
 * @since 2020/5/8
 */
public class HttpParamaters extends AbstractHttpParameters {
    @Override
    public HashMap<String, String> setHashParameters() {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token", TokenUtils.getToken());
        stringStringHashMap.put("version", AppUtils.getAppVersionName());
        stringStringHashMap.put("os_type","an");
        return stringStringHashMap;
    }
}
