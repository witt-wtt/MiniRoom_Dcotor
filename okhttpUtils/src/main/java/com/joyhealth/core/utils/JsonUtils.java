package com.joyhealth.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Description :
 * Created by wangjin on 2019-10-08.
 * Job number：
 * Phone ：18301070822
 * Email： 120182051@qq.com
 * Person in charge : 汪渝栋
 * Leader：
 */
public final class JsonUtils {

    /**
     * json数据转换为实体对象
     *
     * @param json
     * @param bean
     * @param <T>
     * @return
     */
    public static final <T> T jsonToObject(String json, Class<T> bean) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        return JSON.toJavaObject(jsonObject, bean);
    }

    /**
     * json数组转换成实体数组
     * @param json
     * @param bean
     * @return
     */
    public static final List jsonToArray(String json, Class bean) {
        return JSON.parseArray(json, bean);
    }
}
