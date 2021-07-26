package com.witt.doctor_miniroom.utils;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.witt.doctor_miniroom.baseview.BaseActivity;

import java.util.Stack;

/**
 * @ClassName: ActivityUtils
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/16 13:31
 * @Description:Activity管理工具类(包含对Fragment的添加切换)
 */
public class ActivityUtils {
    private static Stack<BaseActivity> mActivityStack;

    /**
     * 添加一个Activity到堆栈中
     *
     * @param activity
     */
    public static void addActivity(BaseActivity activity) {
        if (null == mActivityStack) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 从堆栈中移除指定的Activity
     *
     * @param activity
     */
    public static void removeActivity(BaseActivity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 获取顶部的Activity
     *
     * @return
     */
    public static Activity getTopActivity() {
        if (mActivityStack.isEmpty()) {
            return null;
        } else {
            return mActivityStack.get(mActivityStack.size() - 1);
        }
    }

    /**
     * 结束所有的Activity，退出应用
     */
    public static void removeAllActivity() {
        if (mActivityStack != null && mActivityStack.size() > 0) {
            for (Activity activity : mActivityStack) {
                activity.finish();
            }
        }
    }

    /**
     * 将一个Fragment添加到Activity中
     *
     * @param fragmentManager fragment管理器
     * @param fragment        需要添加的fragment
     * @param frameId         布局FrameLayout的Id
     */
    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId) {
        if (null != fragmentManager && null != fragment) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(frameId, fragment);
            transaction.commit();
        }
    }

    /**
     * 将一个Fragment添加到Activity中,并添加tag标识
     *
     * @param fragmentManager fragment管理器
     * @param fragment        需要添加的fragment
     * @param frameId         布局FrameLayout的Id
     * @param tag             fragment的唯一tag标识
     * @param addToBackStack  是否添加到栈中，可通过返回键进行切换fragment
     */
    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId, String tag, boolean addToBackStack) {
        if (null != fragmentManager && null != fragment) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(frameId, fragment, tag);
            if (addToBackStack) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
        }
    }

    /**
     * 对Fragment进行显示隐藏的切换，减少fragment的重复创建
     *
     * @param fragmentManager fragment管理器
     * @param hideFragment    需要隐藏的Fragment
     * @param showFragment    需要显示的Fragment
     * @param frameId         布局FrameLayout的Id
     * @param tag             fragment的唯一tag标识
     */
    public static void switchFragment(FragmentManager fragmentManager, Fragment hideFragment, Fragment showFragment, int frameId, String tag) {
        if (fragmentManager != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!showFragment.isAdded()) {
                transaction.hide(hideFragment)
                        .add(frameId, showFragment, tag)
                        .commit();
            } else {
                transaction.hide(hideFragment)
                        .show(showFragment)
                        .commit();
            }
        }
    }

    /**
     * 替换Activity中的Fragment
     *
     * @param fragmentManager fragment管理器
     * @param fragment        需要替换到Activity的Fragment
     * @param frameId         布局FrameLayout的Id
     */
    public static void replaceFragmentFromActivity(FragmentManager fragmentManager, Fragment fragment, int frameId) {
        if (null != fragmentManager && null != fragment) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(frameId, fragment);
            transaction.commit();
        }
    }
}
