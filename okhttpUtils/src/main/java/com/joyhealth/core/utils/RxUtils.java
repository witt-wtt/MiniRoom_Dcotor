package com.joyhealth.core.utils;

import android.annotation.SuppressLint;

import com.alibaba.fastjson.JSON;
import com.joyhealth.core.JoyHealth;
import com.joyhealth.core.app.JoyHealthConfigurator;
import com.joyhealth.core.base.SSLBean;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class RxUtils {

@SuppressLint("TrulyRandom")
public static SSLSocketFactory createSSLSocketFactory() {
    SSLSocketFactory sSLSocketFactory = null;
    try {
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, new TrustManager[]{new TrustAllManager()},
                new SecureRandom());
        sSLSocketFactory = sc.getSocketFactory();
    } catch (Exception ignored) {
    }
    return sSLSocketFactory;
}

public static class TrustAllManager implements X509TrustManager {
    @SuppressLint("TrustAllX509TrustManager")
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }

    @SuppressLint("TrustAllX509TrustManager")
    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
//        if (chain == null) {
//            throw new CertificateException("checkServerTrusted: X509Certificate array is null");
//        }
//        if (chain.length < 1) {
//            throw new CertificateException("checkServerTrusted: X509Certificate is empty");
//        }
//        if (!(null != authType && authType.equals("ECDHE_RSA"))) {
//            throw new CertificateException("checkServerTrusted: AuthType is not ECDHE_RSA");
//        }

//        //??????????????????
//        try {
//            TrustManagerFactory factory = TrustManagerFactory.getInstance("X509");
//            factory.init((KeyStore) null);
//            for (TrustManager trustManager : factory.getTrustManagers()) {
//                ((X509TrustManager) trustManager).checkServerTrusted(chain, authType);
//            }
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        }

        //??????????????????????????????
//        String clientEncoded = "";
//        String clientSubject = "";
//        String clientIssUser = "";
//            String ssl = JoyHealth.getSSL();
//            SSLBean sslBean = JSON.parseObject(ssl, SSLBean.class);
//            clientEncoded=sslBean.getClientEncoded();
//            clientSubject=sslBean.getClientSubject();
//            clientIssUser=sslBean.getClientIssUser();
//
//
//        //??????????????????????????????
//        X509Certificate certificate = chain[0];
//        PublicKey publicKey = certificate.getPublicKey();
//        String serverEncoded = new BigInteger(1, publicKey.getEncoded()).toString(16);
//
//        if (!clientEncoded.equals(serverEncoded)) {
//            throw new CertificateException("server's PublicKey is not equals to client's PublicKey");
//        }
//        String subject = certificate.getSubjectDN().getName();
//        if (!clientSubject.equals(subject)) {
//            throw new CertificateException("server's subject is not equals to client's subject");
//        }
//        String issuser = certificate.getIssuerDN().getName();
//        if (!clientIssUser.equals(issuser)) {
//            throw new CertificateException("server's issuser is not equals to client's issuser");
//        }

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}

public static class TrustAllHostnameVerifier implements HostnameVerifier {
    @SuppressLint("BadHostnameVerifier")
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
 }
 public static class X509TrustManagers implements X509TrustManager{

     @Override
     public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

     }

     @Override
     public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

     }

     @Override
     public X509Certificate[] getAcceptedIssuers() {
         return new X509Certificate[0];
     }
 }


}