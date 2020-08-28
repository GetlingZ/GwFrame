package com.getling.gwframe.rv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ClickUtils;
import com.getling.gwframe.rv.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author getling
 * @email jialin.zhang@ucarinc.com
 * @date 2019/02/23
 * @description 基础适配器
 */
public abstract class BaseRecyclerViewAdapter<E> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<E> data;
    private OnItemClickListener onItemClickListener;

    public void setData(List<E> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addOneData(E data) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        this.data.add(data);
        notifyDataSetChanged();
    }

    public void addData(List<E> data) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        if (data != null) {
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    public List<E> getData() {
        return data;
    }

    /**
     * 获取条目id
     *
     * @return id
     */
    public abstract int getItemLayoutId();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayoutId(), parent, false);
        final BaseViewHolder vh = new BaseViewHolder(view);
        if (onItemClickListener != null) {
            ClickUtils.applySingleDebouncing(view, v ->
                    onItemClickListener.onClick(v, vh.getLayoutPosition()));
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        convert((BaseViewHolder) holder, data.get(position));
    }

    /**
     * 给条目填充数据
     *
     * @param holder vh
     * @param item   实体类
     */
    protected abstract void convert(BaseViewHolder holder, @NotNull E item);

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public interface OnItemClickListener {
        /**
         * 点击事件
         *
         * @param v        view
         * @param position position
         */
        void onClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
