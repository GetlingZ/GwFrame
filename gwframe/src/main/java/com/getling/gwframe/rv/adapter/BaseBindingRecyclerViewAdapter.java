package com.getling.gwframe.rv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.getling.gwframe.rv.viewholder.BaseBindingViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: getling
 * @CreateDate: 2019/11/21 8:32
 * @Description:
 */
public abstract class BaseBindingRecyclerViewAdapter<E> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<E> data;
    private int variableId;
    private OnItemClickListener onItemClickListener;

    public BaseBindingRecyclerViewAdapter(int variableId) {
        this.variableId = variableId;
    }

    public void setData(List<E> data) {
        this.data = data;
        if (CollectionUtils.isEmpty(this.data)) {
            this.data = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    public void addData(List<E> newData) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        if (newData != null) {
            this.data.addAll(newData);
            notifyDataSetChanged();
        }
    }

    public void addOneData(E data) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        this.data.add(data);
        notifyDataSetChanged();
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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, getItemLayoutId(), parent, false);
        BaseBindingViewHolder vh = new BaseBindingViewHolder(binding);
        if (onItemClickListener != null) {
            ClickUtils.applySingleDebouncing(binding.getRoot(), v ->
                    onItemClickListener.onClick(binding.getRoot(), vh.getLayoutPosition()));
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BaseBindingViewHolder viewHolder = (BaseBindingViewHolder) holder;
        viewHolder.getBinding().setVariable(variableId, data.get(position));
        if (data != null) {
            convert((BaseBindingViewHolder) holder, data.get(position), position);
        } else {
            convert((BaseBindingViewHolder) holder, null, position);
        }
        viewHolder.getBinding().executePendingBindings();
    }

    /**
     * 给条目填充数据
     *
     * @param holder vh
     * @param item   实体类
     */
    protected abstract void convert(@NonNull BaseBindingViewHolder holder, @NonNull E item, int position);

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public void clearData() {
        if (data != null) {
            data.clear();
        }
        notifyDataSetChanged();
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
