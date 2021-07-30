package com.witt.doctor_miniroom.utils;

import android.util.Log;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: TokenUtils
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/27 14:45
 * @Description:
 */
public class TokenUtils {
    /**
     * create by witt
     * <p>
     * 获取token
     */
    public static String getToken() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy%%MM%%dd%%HH");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String date = formatter.format(curDate);
        Log.d("getToken", "getToken: From__"+date);
        String str1 = Md5(date).substring(0, 25);
        String str2 = Md5("hellow homehealth").substring(0, 12);
        String token = Md5(str1 + str2).substring(0, 25);
        return token;
    }
    public static String Md5(String str) {
        if (str != null && !str.equals("")) {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                        '9', 'a', 'b', 'c', 'd', 'e', 'f'};
                byte[] md5Byte = md5.digest(str.getBytes("UTF8"));
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < md5Byte.length; i++) {
                    sb.append(HEX[(md5Byte[i] & 0xff) / 16]);
                    sb.append(HEX[(md5Byte[i] & 0xff) % 16]);
                }
                str = sb.toString();
            } catch (NoSuchAlgorithmException e) {
            } catch (Exception e) {
            }
        }
        return str;
    }
}
