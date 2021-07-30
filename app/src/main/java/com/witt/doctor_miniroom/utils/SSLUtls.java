package com.witt.doctor_miniroom.utils;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.witt.doctor_miniroom.http.SSLBean;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * @author zhangjinqi
 * @explain new_branch
 * @since 2020/10/20
 */
public class SSLUtls {

    private static final String CLIENT_TRUST_PASSWORD = "123456";//信任证书密码，该证书默认密码是changeit
    private static final String CLIENT_AGREEMENT = "TLS";//使用协议
    private static final String CLIENT_TRUST_MANAGER = "X509";
    private static final String CLIENT_TRUST_KEYSTORE = "BKS";
   private static SSLContext sslContext = null;
    public static String getSSLString(Context context,String sslname){
        //获取本地证书中的信息
        String clientEncoded = "";
        String clientSubject = "";
        String clientIssUser = "";
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            InputStream inputStream = context.getAssets().open(sslname);

            X509Certificate clientCertificate = (X509Certificate) certificateFactory.generateCertificate(inputStream);
            clientEncoded = new BigInteger(1, clientCertificate.getPublicKey().getEncoded()).toString(16);
            clientSubject = clientCertificate.getSubjectDN().getName();
            clientIssUser = clientCertificate.getIssuerDN().getName();
        } catch (IOException | CertificateException e) {
            e.printStackTrace();
        }

        SSLBean sslBean = new SSLBean();
        sslBean.setClientEncoded(clientEncoded);
        sslBean.setClientIssUser(clientIssUser);
        sslBean.setClientSubject(clientSubject);
        return JSON.toJSONString(sslBean);
    }


    public static SSLContext getSslSocket(Context context,String name) {
        try {
//取得SSL的SSLContext实例
            sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
//取得TrustManagerFactory的X509密钥管理器实例
            TrustManagerFactory trustManager = TrustManagerFactory.getInstance(CLIENT_TRUST_MANAGER);
//取得BKS密库实例
            KeyStore tks = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);
            InputStream inputStream = context.getAssets().open(name);
            try {
                tks.load(inputStream, CLIENT_TRUST_PASSWORD.toCharArray());
            } finally {
                inputStream.close();
            }
//初始化密钥管理器
            trustManager.init(tks);
//初始化SSLContext
            sslContext.init(null, trustManager.getTrustManagers(), null);
        } catch (Exception e) {
            LogUtils.e("SslContextFactory", e.getMessage());
        }
        return sslContext;    }


        public static String getSSLEnvironment(boolean isDebug){
            if(isDebug){
                return "debug";
            }else {
                return "relase";
            }
        }
}