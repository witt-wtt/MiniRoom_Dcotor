package com.joyhealth.core.utils;

import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.TimeUtils;
import com.orhanobut.logger.Logger;

/**
 * Description :JoyHealth内部时间工具类
 * Created by wangjin on 2019-10-31.
 * Job number：
 * Phone ：18301070822
 * Email： 120182051@qq.com
 * Person in charge : 汪渝栋
 * Leader：
 */
public class JoyHealthTimeUtils {

    //微信登录二维码过期时间300秒
    public static final int QRCODE_INVALID_TIME = 300;

    //微信ticket过期时间7200秒
    public static final int TICKET_INVALID_TIEM = 7200;


    //计算微信二维码是否过期
    //1、每次生成二维码的时候保存当前时间，在判断二维码是否过期的时候使用系统当前时间进行比较
    //2、判断二维码是否过期，过期先删除二维码缓存
    //3、检测网络变化，如果网络在待机过程中出现了连接断开的情况，那么重新生成二维码


    //计算微信ticket是否过期
    //1、每次生成二维码的时候判断ticket是否过期
    //2、如果过期则删除当前ticket，从微信服务器重新获取ticket
    //3、重新根据ticket生成二维码

    /**
     * 检测二维码是否过期
     *
     * @param saveTime
     * @return true 没有过期 false 过期
     */
    public static boolean checkQrcodeInvalid(long saveTime) {
        return checkTimeInvalid(saveTime,QRCODE_INVALID_TIME);
    }

    /**
     * 检测ticket是否过期
     * @param saveTime
     * @return
     */
    public static boolean checkTicketInvalid(long saveTime) {
      return checkTimeInvalid(saveTime,TICKET_INVALID_TIEM);
    }

    private static boolean checkTimeInvalid(long saveTime,int offset){
        boolean isInvalid = false;
        long curTime = System.currentTimeMillis();

        //计算当前时间和保存的时间的时间差是多少
        long convertTime = TimeUtils.getTimeSpan(curTime, saveTime, TimeConstants.SEC);

        //如果时间差小于指定的时间，则说明还没失效
        if (convertTime < offset) {
            isInvalid = true;
        }

        return isInvalid;
    }


    /**
     * 计算保存时间和时间偏移量的差值
     * @param saveTime 当前系统的时间
     * @param offset 二维码失效时间或者ticket失效时间，单位秒
     * @return 时间差，单位秒
     */
    public static long timeOffset(long saveTime,int offset){
        long curTime = System.currentTimeMillis();
        long convertTime = TimeUtils.getTimeSpan(curTime, saveTime, TimeConstants.SEC);
        return offset - convertTime;
    }


    /**
     * 检验ticket有效期
     */
    public static void checkTicketTime(){
        long ticketSaveTime = JoyHealthPreference.getTicketTime();
        boolean ticketResult = JoyHealthTimeUtils.checkTicketInvalid(ticketSaveTime);
        Logger.d("ticket save time is : " + ticketSaveTime + " and ticket valid is " + ticketResult);
        if (!ticketResult){
            JoyHealthPreference.clearTicket();
            JoyHealthPreference.clearWxAccessToken();
        }
    }


    /**
     * 检验二维码有效期
     */
    public static void checkQrcodeTime(){
        long qrcodeSaveTime = JoyHealthPreference.getQrcodeTime();
        boolean qrcodeResult = JoyHealthTimeUtils.checkQrcodeInvalid(qrcodeSaveTime);
        Logger.d("qrcode save time is : " + qrcodeSaveTime + " and qrcode valid is " + qrcodeResult);
        if (!qrcodeResult) {
            JoyHealthPreference.clearLoginQrcode();
        }
    }


//    public static void main(String[] args) {
//        long oldtime = System.currentTimeMillis();
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        long newtime = System.currentTimeMillis();
//
//        String timeUtils = TimeUtils.getFitTimeSpan(oldtime, newtime, 5);
//
//        System.out.println("String is " + timeUtils);
//        System.out.println(newtime - oldtime);
//
//        long msec = TimeUtils.getTimeSpan(oldtime, newtime, TimeConstants.SEC);
//        System.out.println("msec is " + msec);
//    }
}
