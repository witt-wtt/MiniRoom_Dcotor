package com.witt.doctor_miniroom.mvpmoudle.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.squareup.picasso.Picasso;
import com.witt.doctor_miniroom.R;
import com.witt.doctor_miniroom.mvpmoudle.entitys.ConsultationEntity;
import com.witt.doctor_miniroom.mvpmoudle.ui.VideoRoomActivity;
import com.witt.doctor_miniroom.utils.AppConfigUtils;
import com.witt.doctor_miniroom.view.CircularImageView;

import java.util.List;

/**
 * @ClassName: ConsultationListAdapter
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/28 11:12
 * @Description: 微诊室咨询列表
 */
public class ConsultationListAdapter extends RecyclerView.Adapter<ConsultationListAdapter.ConsultationListViewHolder> {
    private Context mContext;
    private LayoutInflater inflater;
    private List<ConsultationEntity.DataBean> list;
    private SparseArray<CountDownTimer> countDownCounters;


    public ConsultationListAdapter(Context mContext, List<ConsultationEntity.DataBean> data) {
        this.mContext = mContext;
        this.list = data;
        this.countDownCounters = new SparseArray<>();
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ConsultationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConsultationListViewHolder(inflater.inflate(R.layout.item_consultation_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultationListViewHolder holder, int position) {
        ConsultationEntity.DataBean bean = list.get(position);
        holder.name_txt.setText(bean.getUsername());
        holder.sex_txt.setText(bean.getSex());
        holder.age_txt.setText(bean.getAge()+"");
        if (!"".equals(bean.getHeader_img())) {
            Picasso.with(mContext)
                    .load(bean.getHeader_img())
                    .error(R.mipmap.ic_launcher)//错误图片
                    .into(holder.user_headimage);
        }
        if (bean.getVideo_time() != null && !"".equals(bean.getVideo_time())) {
            String[] str = bean.getVideo_time().split(" ");
            String[] data = str[0].split("-");
            holder.time_txt.setText(data[1] + "月" + data[2] + "日" + "  " + str[1]);
        }
        holder.callin_txt.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, VideoRoomActivity.class);
            if (null != list.get(position).getRoom_id()) {
                intent.putExtra(AppConfigUtils.ROOM_ID, list.get(position).getRoom_id());
                mContext.startActivity(intent);
            }
        });
        //倒计时
        CountDownTimer countDownTimer = countDownCounters.get(holder.chronometer.hashCode());
        //将前一个缓存清除
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        int spantime = bean.getJudge();
        LogUtils.e("时间", "是否过期==" + spantime);
        if (spantime == 1) {
            countDownTimer = new CountDownTimer(120 * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    holder.chronometer.setText(AppConfigUtils.getCountTimeByLong(millisUntilFinished));
                }

                public void onFinish() {
                    try {
                        if (list.size() >= 1) {
                            list.remove(position);
                            notifyDataSetChanged();
                        }
                    } catch (Exception e) {

                    }

                }
            }.start();
            countDownCounters.put(holder.chronometer.hashCode(), countDownTimer);
        }else {
            holder.callin_txt.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    /**
     * 清空资源
     */
    public void cancelAllTimers() {
        if (countDownCounters == null) {
            return;
        }
        LogUtils.e("TAG", "size :  " + countDownCounters.size());
        for (int i = 0, length = countDownCounters.size(); i < length; i++) {
            CountDownTimer cdt = countDownCounters.get(countDownCounters.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
    }

    static class ConsultationListViewHolder extends RecyclerView.ViewHolder {
        private TextView name_txt, sex_txt, age_txt, callin_txt, time_txt;
        private CircularImageView user_headimage;
        private TextView chronometer;//计时

        public ConsultationListViewHolder(@NonNull View itemView) {
            super(itemView);
            name_txt = itemView.findViewById(R.id.name_txt);
            sex_txt = itemView.findViewById(R.id.sex_txt);
            age_txt = itemView.findViewById(R.id.patient_age_txt);
            callin_txt = itemView.findViewById(R.id.callin_txt);
            time_txt = itemView.findViewById(R.id.time_txt);
            chronometer = itemView.findViewById(R.id.chronometer);

            user_headimage = itemView.findViewById(R.id.user_headimage);
        }
    }
}
