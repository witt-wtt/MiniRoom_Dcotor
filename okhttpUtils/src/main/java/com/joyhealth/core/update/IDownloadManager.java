package com.joyhealth.core.update;

/**
 * Description :下载管理器
 * Created by wangjin on 2019-08-30.
 * Job number：
 * Phone ：18301070822
 * Email： 120182051@qq.com
 * Person in charge : 汪渝栋
 * Leader：
 */
public interface IDownloadManager {
    /**
     * 下载apk
     * @param url apk下载全路径
     * @param name 下载后apk的全名
     */
    void downloadAPK(String url, String name);

    //下载成功

    //下载失败
}
