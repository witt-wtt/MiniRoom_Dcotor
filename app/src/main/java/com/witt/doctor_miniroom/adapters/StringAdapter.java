package com.witt.doctor_miniroom.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.witt.doctor_miniroom.R;

import java.util.List;

/**
 * @ClassName: StringAdapter
 * @Author: witt
 * @E-mail: wittvip@163.com
 * @Date: Create time 2021/7/22 16:56
 * @Description:
 */
public class StringAdapter extends RecyclerView.Adapter<StringAdapter.StringViewHolder> {
    private List<String> list;
    private LayoutInflater inflater;
    private Context mContext;

    public StringAdapter(Context mContext, List<String> data) {
        this.list = data;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public StringViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StringViewHolder(inflater.inflate(R.layout.item_string_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StringViewHolder holder, int position) {
            holder.title_txt.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class StringViewHolder extends RecyclerView.ViewHolder {
        private TextView title_txt;
        public StringViewHolder(@NonNull View itemView) {
            super(itemView);
            title_txt=itemView.findViewById(R.id.title_txt);
        }
    }
}
