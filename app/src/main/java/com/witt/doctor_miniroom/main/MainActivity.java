package com.witt.doctor_miniroom.main;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.witt.doctor_miniroom.R;
import com.witt.doctor_miniroom.baseview.BaseActivity;
import com.witt.doctor_miniroom.baseview.BaseFragment;
import com.witt.doctor_miniroom.mvpmoudle.ui.ConsultationListFragment;
import com.witt.doctor_miniroom.mvpmoudle.ui.PersonInfoFragment;
import com.witt.doctor_miniroom.mvpmoudle.ui.VideoRoomActivity;
import com.witt.doctor_miniroom.utils.AppConfigUtils;
import com.witt.doctor_miniroom.utils.PermissionListener;
import com.witt.doctor_miniroom.utils.TokenUtils;
import com.zinc.jrecycleview.JRecycleView;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * @ClassName:
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/14 10:47
 * @Description:
 */

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    //所需权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE};
    private BaseFragment[] fragments;//碎片集合
    private ConsultationListFragment consultationListFragment;//微诊室咨询列表
    private PersonInfoFragment personInfoFragment;//个人中心
    private Bundle mSavedInstanceState;
    @BindView(R.id.mian_bottomNavigation)
    BottomNavigationView bottomNavigationView;

    private int mPreFragmentFlag = 0;
    private Fragment lastFragment;
    private List<Fragment> fragmentList;


    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSavedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        isShowBackBtn(false);
        setBaseTitle_txt("咨询列表");
        if (!NetworkUtils.isConnected()) {
            showNoNetworkView();
        } else {
            requestPermissions(permissions, new PermissionListener() {
                @Override
                public void onGranted() {
                }

                @Override
                public void onDenied(List<String> deniedPermissions) {
                    ToastUtils.showShort("权限未开启");
                }
            });
            showContentView();
            initData();
        }
    }

    private void initData() {
        if (mSavedInstanceState == null) {
            consultationListFragment = ConsultationListFragment.initFragment();//首页
            personInfoFragment = PersonInfoFragment.initFragment();//个人中心
            fragments = new BaseFragment[]{consultationListFragment, personInfoFragment};

            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
            switchFragment(R.id.main_fragment, 0, fragments);//默认显示首页
            mPreFragmentFlag = 0;
        } else {
            consultationListFragment = ConsultationListFragment.initFragment();//首页
            personInfoFragment = PersonInfoFragment.initFragment();//个人中心
            fragments = new BaseFragment[]{consultationListFragment, personInfoFragment};
            int tag = Integer.parseInt(Objects.requireNonNull(mSavedInstanceState.getString(AppConfigUtils.LAST_FRAGMENT)));
            LogUtils.e(TAG, "tag========" + tag);
            showAndHideFragment(fragments[tag], fragments[mPreFragmentFlag]);
            mPreFragmentFlag = tag;
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void onRefresh() {
        if (!NetworkUtils.isConnected()) {
            showNoNetworkView();
        } else {
            showContentView();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lastFragment = getVisibleFragment();
        if (lastFragment != null && lastFragment.getTag() != null) {
            outState.putString(AppConfigUtils.LAST_FRAGMENT, lastFragment.getTag());
        }
    }

    //得到当前Activity显示的fragment
    private Fragment getVisibleFragment() {
        Fragment lastFragment = null;
        fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragmentList) {
            if (fragment != null && fragment.isVisible()) {
                lastFragment = fragment;
                break;
            }
        }
        return lastFragment;
    }

    //切换fragment
    private void switchFragment(int containerId, int showFragment, BaseFragment[] fragments) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            transaction.add(containerId, fragments[i], i + "");
            if (i != showFragment)
                transaction.hide(fragments[i]);
        }
        transaction.commitAllowingStateLoss();
    }

    //加载不同的Fragment
    private void showAndHideFragment(BaseFragment show, BaseFragment hide) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (show != hide) {
            transaction.show(show).hide(hide).commitAllowingStateLoss();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                setBaseTitle_txt("咨询列表");
                showAndHideFragment(fragments[0], fragments[mPreFragmentFlag]);
                mPreFragmentFlag = 0;
                break;
            case R.id.navigation_person:
                setBaseTitle_txt("个人中心");
                showAndHideFragment(fragments[1], fragments[mPreFragmentFlag]);
                mPreFragmentFlag = 1;
                break;

        }
        return true;
    }
}