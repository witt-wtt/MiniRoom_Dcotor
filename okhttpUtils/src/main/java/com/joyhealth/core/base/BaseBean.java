package com.joyhealth.core.base;

import java.io.Serializable;

/**
 * Description :
 * Created by wangjin on 2019-10-10.
 * Job number：
 * Phone ：18301070822
 * Email： 120182051@qq.com
 * Person in charge : 汪渝栋
 * Leader：
 */
public class BaseBean{


    /**
     * code : 200
     * message : 成功
     * data : {"init_type":"1"}
     */

    private String code;
    private String message;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
