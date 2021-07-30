package com.witt.doctor_miniroom.mvpmoudle.parameter;

/**
 * @ClassName: Login
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/27 15:08
 * @Description:
 */
public class LoginParameter {
    private String phone;
    private String password;
    private String code;
    private String RegistrationID;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRegistrationID() {
        return RegistrationID;
    }

    public void setRegistrationID(String registrationID) {
        RegistrationID = registrationID;
    }
}
