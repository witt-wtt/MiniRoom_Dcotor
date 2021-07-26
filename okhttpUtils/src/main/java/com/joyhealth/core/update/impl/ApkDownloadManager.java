package com.joyhealth.core.update.impl;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.FileUtils;
import com.joyhealth.core.update.IDownloadManager;
import com.joyhealth.core.utils.JoyHealthPreference;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Description :apk下载管理类，如果需要接受下载状态监听需要实现DownloadStatusListener接口，
 * 并且在需要接受的地方设置DownloadStatusListener，如果是按钮安装则还需要设置isInstall为false，否则会走静默安装流程
 * Created by wangjin on 2019-08-30.
 * Job number：
 * Phone ：18301070822
 * Email： 120182051@qq.com
 * Person in charge : 汪渝栋
 * Leader：
 */
public class ApkDownloadManager implements IDownloadManager {

    //外部存储路径
    private static String PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    //目录名称
    private static final String dirName = "JoyHealth";

    //安装apk文件的路径
    //初始化安装apk的路径  /storage/emulated/0/JoyHealth
    //installApkPath = PATH + File.separator + name;
    private static final String installApkdir = PATH + File.separator + dirName;

    private String apkName = null;

    //下载器
    private DownloadManager downloadManager;

    //上下文
    private Context mContext;

    //下载的ID
    private long downloadId;

    //下载状态监听
    private DownloadStatusListener downloadStatusListener;

    //静默安装需要设置为true，点击按钮安装设置为false，默认静默安装避免微诊室代码再做修改
    private boolean isInstall = true;

    private ApkDownloadManager() {
    }

    public static class ApkDownloadManagerInstance {
        private static final ApkDownloadManager apkDownloadManager = new ApkDownloadManager();
    }

    public static final ApkDownloadManager getInstance() {
        return ApkDownloadManagerInstance.apkDownloadManager;
    }

    public ApkDownloadManager setContext(@NonNull Context context) {
        this.mContext = context;
        return this;
    }

    public String getApkInstallDir() {
        return installApkdir;
    }

    /**
     * 配置下载完成后是否安装
     *
     * @param install true 安装 / false 不安装
     * @return
     */
    public ApkDownloadManager setInstall(boolean install) {
        this.isInstall = install;
        return this;
    }

    public ApkDownloadManager setDownloadStatusListener(DownloadStatusListener downloadStatusListener) {
        this.downloadStatusListener = downloadStatusListener;
        return this;
    }

    //广播监听下载的各个状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkStatus();
        }
    };

    @Override
    public void downloadAPK(@NonNull String url, @NonNull String name) {
        if (mContext == null) {
            throw new NullPointerException("Context is null,please setting Context!!");
        }

        if (downloadStatusListener == null) {
            throw new IllegalArgumentException("DownloadStatusListener is null");
        }

        this.apkName = name;

        if (!createDownloadDir(this.installApkdir)) return;

        //获取DownloadManager
        downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);

        //如果正在下载安装包，则直接返回
        if (isDownloading(url)) return;

        //如果有旧的apk任务存在则根据之前保存的任务id删除任务
        long taskId = JoyHealthPreference.getApkTaskId();
        if (taskId != -1L) {
            int result = downloadManager.remove(taskId);
            if (result == -1) {
                Logger.e("删除下载任务失败  result == -1 , taskId is " + taskId);
            }
        }

        //开始下载之前先删除原有存在的包
        if (FileUtils.isFileExists(installApkdir + File.separator + apkName)) {
            boolean delResult = FileUtils.delete(installApkdir + File.separator + apkName);
            Logger.e("删除下载apk delResult is ：" + delResult + ",path is " + installApkdir + File.separator + apkName);
        }


        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //移动网络情况下是否允许漫游
        request.setAllowedOverRoaming(false);

        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle("新版本Apk");
        request.setDescription("Apk Downloading");
        request.setVisibleInDownloadsUi(true);

        //设置下载的路径 dirType如果设置了 存储路径会拼接，默认拼接路径是/storage/emulated/0/，
        //示例 request.setDestinationInExternalPublicDir("wyd", name)，保存地址-->/storage/emulated/0/wyd/name
        request.setDestinationInExternalPublicDir(dirName, this.apkName);

        //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
        downloadId = downloadManager.enqueue(request);
        JoyHealthPreference.putApkTaskId(downloadId);

        //注册广播接收者，监听下载状态
        mContext.registerReceiver(receiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }


    //检查下载状态
    private void checkStatus() {

        DownloadManager.Query query = new DownloadManager.Query();
        //通过下载的id查找
        query.setFilterById(downloadId);
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                //下载暂停
                case DownloadManager.STATUS_PAUSED:
                    downloadStatusListener.paused(status);
                    Logger.d("core->download apk STATUS_PAUSED!!");
                    break;
                //下载延迟
                case DownloadManager.STATUS_PENDING:
                    downloadStatusListener.pending(status);
                    Logger.d("core->download apk STATUS_PENDING!!");
                    break;
                //正在下载
                case DownloadManager.STATUS_RUNNING:
                    downloadStatusListener.running(status);
                    Logger.d("core->download apk STATUS_RUNNING!!");
                    break;
                //下载完成
                case DownloadManager.STATUS_SUCCESSFUL:
                    //TODO 后期规范下载过程状态，不需要这么多回调接口
                    if (isInstall) {
                        //静默安装流程
                        boolean result = ApkInstallManager.getInstance().silentInstall(mContext.getPackageManager(), installApkdir + File.separator + this.apkName);
                        if (result) {
                            //下载完成并安装APK成功
                            downloadStatusListener.success(status);
                            if (receiver != null) {
                                mContext.unregisterReceiver(receiver);
                                receiver = null;
                            }
                            Logger.d("core->download apk success!!");
                        }
                    } else {
                        //按钮点击安装流程
                        JoyHealthPreference.putApkSavePath(installApkdir + File.separator + this.apkName);
                        downloadStatusListener.success(status);
                    }
                    break;
                //下载失败
                case DownloadManager.STATUS_FAILED:
                    deleteApk();
                    downloadStatusListener.failed(status);
                    Logger.d("core->download apk faild!!");
                    Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private boolean deleteApk() {
        if (JoyHealthPreference.getApkSavePath() != null) {
            return FileUtils.delete(JoyHealthPreference.getApkSavePath());
        }
        return false;
    }


    /**
     * 判断url相关的任务是否在下载中，如果任务列表中已经有了相关的任务则不作再次下载的操作
     *
     * @param url
     * @return
     */
    public boolean isDownloading(String url) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterByStatus(DownloadManager.STATUS_RUNNING | DownloadManager.STATUS_PENDING);
        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst()) {
            if (cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI)).equals(url)) {
                Logger.d("ApkDownloadManager->isDownloading->url :" + url + "  is downloading!!!");
                cursor.close();
                return true;
            }
        }
        Logger.d("ApkDownloadManager->isDownloading->url :" + url + "  is not downloading!!!");
        cursor.close();
        return false;
    }

    /**
     * 创建下载目录
     *
     * @param dirPath 目录地址
     * @return
     */
    public boolean createDownloadDir(String dirPath) {
        if (FileUtils.createOrExistsDir(dirPath)) {
            Logger.d("createDownloadDir success!!!");
            return chmodDir777(dirPath);
        }
        return false;
    }

    /**
     * 为指定的文件路径设置777操作权限
     *
     * @param dirPath
     * @return
     */
    public boolean chmodDir777(String dirPath) {
        Process p;
        int status;
        try {
            p = Runtime.getRuntime().exec("chmod 777 " + dirPath);
            status = p.waitFor();
            if (status == 0) {
                //chmod success
                Logger.d("chmodDir777 dirPath is success!!");
                return true;
            } else {
                //chmod failed
                Logger.d("chmodDir777 dirPath is failed!!");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 下载状态监听
     */
    public interface DownloadStatusListener {
        void failed(int status);

        void success(int status);

        void running(int status);

        void pending(int status);

        void paused(int status);
    }
}
