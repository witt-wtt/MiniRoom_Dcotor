package com.witt.doctor_miniroom.mvpmoudle.entitys;

import java.util.List;

/**
 * @ClassName: ConsultationEntity
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/29 13:23
 * @Description:微诊室咨询列表
 */
public class ConsultationEntity {
    private String code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String header_img;
        private String username;
        private String sex;
        private int age;
        private String video_time;
        private String home_id;
        private String app_reservation_id;
        private int judge;
        private String room_id;
        private String device_id;
        private String device_name;

        public String getHeader_img() {
            return header_img;
        }

        public void setHeader_img(String header_img) {
            this.header_img = header_img;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getVideo_time() {
            return video_time;
        }

        public void setVideo_time(String video_time) {
            this.video_time = video_time;
        }

        public String getHome_id() {
            return home_id;
        }

        public void setHome_id(String home_id) {
            this.home_id = home_id;
        }

        public String getApp_reservation_id() {
            return app_reservation_id;
        }

        public void setApp_reservation_id(String app_reservation_id) {
            this.app_reservation_id = app_reservation_id;
        }

        public int getJudge() {
            return judge;
        }

        public void setJudge(int judge) {
            this.judge = judge;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
        }
    }
}
