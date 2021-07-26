package com.joyhealth.core.base;

/**
 * @author zhangjinqi
 * @explain new_branch
 * @since 2020/10/20
 */
public class SSLBean {
    private String clientEncoded;
    private String clientSubject;

    public String getClientEncoded() {
        return clientEncoded;
    }

    public void setClientEncoded(String clientEncoded) {
        this.clientEncoded = clientEncoded;
    }

    public String getClientSubject() {
        return clientSubject;
    }

    public void setClientSubject(String clientSubject) {
        this.clientSubject = clientSubject;
    }

    public String getClientIssUser() {
        return clientIssUser;
    }

    public void setClientIssUser(String clientIssUser) {
        this.clientIssUser = clientIssUser;
    }

    private String clientIssUser;
}