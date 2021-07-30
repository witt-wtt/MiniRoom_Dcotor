package com.witt.doctor_miniroom.mvpmoudle.entitys;

/**
 * @ClassName: UserEntity
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/27 15:31
 * @Description:
 */
public class UserEntity {
    private String code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String phone;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
