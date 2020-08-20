package com.getling.gwframe.rv.viewholder;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author: getling
 * @CreateDate: 2019/11/21 8:33
 * @Description:
 */
public class BaseBindingViewHolder extends RecyclerView.ViewHolder {
    private Context context;
    private ViewDataBinding binding;

    public BaseBindingViewHolder(@NonNull ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        context = binding.getRoot().getContext();
    }

    public Context getContext() {
        return context;
    }

    public ViewDataBinding getBinding() {
        return binding;
    }
}
