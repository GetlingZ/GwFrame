package com.getling.gwframe.update;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.getling.gwframe.R;
import com.getling.gwframe.update.entity.UpdateResponseEntity;

import org.jetbrains.annotations.NotNull;

/**
 * @Author: getling
 * @CreateDate: 2019/6/29 15:11
 * @Description:
 */
public class UpdateFragment extends AppCompatDialogFragment {

    private static final String BUNDLE_KEY_APP_INFO = "bundle_key_app_info";
    private static UpdateFragment INSTANCE;

    private FragmentActivity activity;
    private TextView tvTitle;
    private TextView tvVersion;
    private TextView tvCurrentVersion;
    private TextView tvSize;
    private TextView tvInfo;
    private TextView tvNow;

    private UpdateResponseEntity.FactoryInfo entity;

    public static UpdateFragment newInstance(UpdateResponseEntity.FactoryInfo entity) {
        if (INSTANCE == null) {
            INSTANCE = new UpdateFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(BUNDLE_KEY_APP_INFO, entity);
            INSTANCE.setArguments(bundle);
        }
        return INSTANCE;
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivity) {
            activity = (FragmentActivity) context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        View contentView = inflater.inflate(R.layout.fragment_update, container, false);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ScreenUtils.getScreenWidth() - 100,
                ScreenUtils.getScreenHeight() / 2));
        tvInfo = contentView.findViewById(R.id.tv_update_info);
        tvNow = contentView.findViewById(R.id.tv_update_now);
        tvSize = contentView.findViewById(R.id.tv_update_size);
        tvTitle = contentView.findViewById(R.id.tv_update_title);
        tvVersion = contentView.findViewById(R.id.tv_update_version);
        tvCurrentVersion = contentView.findViewById(R.id.tv_current_version);

        tvCurrentVersion.setText(AppUtils.getAppVersionName());

        //不显示包大小信息
        contentView.findViewById(R.id.ll_size_update).setVisibility(View.GONE);

        ClickUtils.applySingleDebouncing(tvNow, v -> {
            UpdateDownloadFragment fragment = UpdateDownloadFragment.newInstance(entity);
            FragmentManager manager = getParentFragmentManager();
            fragment.show(manager, "download");
            dismiss();
        });

        if (getArguments() != null) {
            entity = (UpdateResponseEntity.FactoryInfo) getArguments().getSerializable(BUNDLE_KEY_APP_INFO);
            if (entity != null) {
//                tvInfo.setText(entity.getReleaseNote());
                tvVersion.setText(entity.getAppVersion());
                setCancel();
//                if (entity.isShouldForce()) {
//                }
            }

        }
        return contentView;
    }

    private void setCancel() {
        if (getDialog() != null) {
            getDialog().setCancelable(false);
            getDialog().setCanceledOnTouchOutside(false);
            getDialog().setOnKeyListener((dialog, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK);
        }
    }
}
