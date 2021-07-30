package com.witt.doctor_miniroom.mvpmoudle.ui;

import android.os.Bundle;


import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.witt.doctor_miniroom.R;
import com.witt.doctor_miniroom.baseview.BaseFragment;
import com.witt.doctor_miniroom.http.MYOKHttpCallback;
import com.witt.doctor_miniroom.http.MyJoyHealthHttpClient;
import com.witt.doctor_miniroom.http.UrlInterface;
import com.witt.doctor_miniroom.mvpmoudle.adapters.ConsultationListAdapter;
import com.witt.doctor_miniroom.mvpmoudle.entitys.ConsultationEntity;
import com.witt.doctor_miniroom.utils.AppConfigUtils;
import com.zinc.jrecycleview.JRecycleView;
import com.zinc.jrecycleview.adapter.JRefreshAndLoadMoreAdapter;

import java.util.HashMap;

import butterknife.BindView;

/**
 * @ClassName:
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/28 09:35
 * @Description:微诊室咨询列表
 */

public class ConsultationListFragment extends BaseFragment {
    @BindView(R.id.consultation_recycle)
    JRecycleView consultation_recycle;
    private ConsultationListAdapter adapter;
    private JRefreshAndLoadMoreAdapter moreAdapter;

    public static ConsultationListFragment initFragment() {
        ConsultationListFragment fragment = new ConsultationListFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_consultation_list;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        getConsultationData();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        consultation_recycle.setLayoutManager(manager);
    }

    private void getConsultationData() {
        showLoadingView();
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", "2000525");
        MyJoyHealthHttpClient.postJson(UrlInterface.ConsultationList(), map, new MYOKHttpCallback() {
            @Override
            public void onSuccess(String result) {
                showContentView();
                try {
                    ConsultationEntity entity = JSON.parseObject(result, ConsultationEntity.class);
                    adapter = new ConsultationListAdapter(getContext(), entity.getData());
                    moreAdapter=new JRefreshAndLoadMoreAdapter(getContext(),adapter);
                    moreAdapter.setRefreshComplete();
                    moreAdapter.setNoMore();
                    moreAdapter.setOnRefreshListener(()->getConsultationData());
                    consultation_recycle.setAdapter(moreAdapter);
                } catch (Exception e) {

                }

            }

            @Override
            public void onError(int code, String message) {
                showEmptyView();
            }

            @Override
            public void onHttpError(Throwable e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (adapter != null) {
            adapter.cancelAllTimers();
            adapter = null;
        }
    }
}