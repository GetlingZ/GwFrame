package com.getling.gwframe.utils;

import android.view.LayoutInflater;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.getling.gwframe.R;
import com.getling.gwframe.databinding.ViewToastBinding;

/**
 * @Author: getling
 * @CreateDate: 2019/11/11 13:59
 * @Description: 提示吐司
 */
public class NotifyUtil {

    public static void showWarning(String msg) {
        Utils.runOnUiThread(() -> {
            ViewToastBinding binding = ViewToastBinding.inflate(LayoutInflater.from(ActivityUtils.getTopActivity()));
            binding.tvToast.setText(msg);
            binding.ivToast.setImageResource(R.drawable.icon_warning);
            ToastUtils.showCustomLong(binding.getRoot());
        });
    }

    public static void showSuccess(String msg) {
        Utils.runOnUiThread(() -> {
            ViewToastBinding binding = ViewToastBinding.inflate(LayoutInflater.from(ActivityUtils.getTopActivity()));
            binding.tvToast.setText(msg);
            binding.ivToast.setImageResource(R.drawable.icon_success);
            ToastUtils.showCustomLong(binding.getRoot());
        });
    }

    public static void showError(String msg) {
        Utils.runOnUiThread(() -> {
            ViewToastBinding binding = ViewToastBinding.inflate(LayoutInflater.from(ActivityUtils.getTopActivity()));
            binding.tvToast.setText(msg);
            binding.ivToast.setImageResource(R.drawable.icon_error);
            ToastUtils.showCustomLong(binding.getRoot());
        });
    }

}
