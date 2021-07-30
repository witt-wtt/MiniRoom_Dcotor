package com.witt.doctor_miniroom.http;

/**
 * @ClassName: UrlInterface
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/19 09:56
 * @Description:项目接口类
 */
public class UrlInterface {
    /**
     * 登录
     *
     * @return
     */
    public static String doctorLogin() {
        return "Doclogin/login";
    }
    /**
     * 获取验证码 登陆
     */
    public static String getLoginCodePort() {
        return "Doclogin/send_login_code";
    }

    /**
     * 验证手机号和密码
     */
    public static String verifyCode() {
        return "Doclogin/verify_code";
    }
    /**
     * 微诊室咨询列表
     */
    public static String ConsultationList(){
        return "Clinic/wait_advisory_list";
    }
    /**
     * 退出登录
     */
    public static String loginOut() {
        return "Smartdoctor/dropOut";
    }

}
