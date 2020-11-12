package com.getling.gwframe.update;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.getling.gwframe.R;
import com.getling.gwframe.app.GwFrame;
import com.getling.gwframe.config.PathConfig;
import com.getling.gwframe.http.observer.SimpleObserver;
import com.getling.gwframe.update.entity.UpdateResponseEntity;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import rxhttp.RxHttp;


/**
 * @Author: getling
 * @CreateDate: 2019/6/29 14:58
 * @Description:
 */
public class UpdateDownloadFragment extends AppCompatDialogFragment {

    private static final String BUNDLE_KEY_APP_INFO = "bundle_key_app_info";
    public static UpdateDownloadFragment INSTANCE;

    private Context context;

    private ProgressBar pb;
    private TextView tvProgress;
    private TextView tvTitle;
    private TextView tvInstall;

    private UpdateResponseEntity.FactoryInfo entity;

    /**
     * 下载地址
     * download/lms
     */
    public static String downloadPath = PathConfig.INSTANCE.getDownloadPath();
    /**
     * apk地址
     * LMS/Decorate/Download/天津内外饰1.0.0.apk
     */
    private String apkPath;
    /**
     * apk文件名
     */
    private String apkName;

    public static UpdateDownloadFragment newInstance(UpdateResponseEntity.FactoryInfo entity) {
        if (INSTANCE == null) {
            INSTANCE = new UpdateDownloadFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(BUNDLE_KEY_APP_INFO, entity);
            INSTANCE.setArguments(bundle);
        }
        return INSTANCE;
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        setCancel();

        View contentView = inflater.inflate(R.layout.fragment_download, container, false);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ScreenUtils.getScreenWidth() - 100,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        tvProgress = contentView.findViewById(R.id.tv_progress);
        tvTitle = contentView.findViewById(R.id.tv_download_title);
        tvInstall = contentView.findViewById(R.id.tv_install_now);

        pb = contentView.findViewById(R.id.pb_update);
        if (getArguments() != null) {
            entity = (UpdateResponseEntity.FactoryInfo) getArguments().getSerializable(BUNDLE_KEY_APP_INFO);
            if (entity != null) {
                apkName = GwFrame.getInstance().factory.getAppName() + entity.getAppVersion() + ".apk";
                apkPath = downloadPath + apkName;
                File file = new File(apkPath);
                if (file.exists()) {
                    install();
                } else {
                    download();
                }
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

    private void download() {
        tvInstall.setVisibility(View.GONE);
        RxHttp.get(GwFrame.getInstance().factory.getDownloadApkUrl())
                .asDownload(apkPath, AndroidSchedulers.mainThread(), progress -> {
                    //下载进度回调,0-100，仅在进度有更新时才会回调，最多回调101次，最后一次回调文件存储路径
                    int currentProgress = progress.getProgress(); //当前进度 0-100
                    long currentSize = progress.getCurrentSize(); //当前已下载的字节大小
                    long totalSize = progress.getTotalSize();     //要下载的总字节大小

                    pb.setProgress(currentProgress);
                    String percent = currentProgress + "%";
                    tvProgress.setText(percent);
                })
                .subscribe(new SimpleObserver<String>() {

                    @Override
                    public void onSuccess(String s) {
                        install();
                    }

                    @Override
                    public void onError(Throwable e) {
                        tvTitle.setText(R.string.update_download_error);
                        ToastUtils.showLong(R.string.update_download_error);
                        pb.setProgress(0);
                        tvProgress.setText("0%");
                        e.printStackTrace();
                        UpdateUtil.deleteApk();
                        setInstallText(false);
                    }
                });
    }

    private void install() {
        tvTitle.setText(R.string.update_download_complete);
        tvProgress.setText(R.string.update_download_100);
        pb.setProgress(100);
        setInstallText(true);

        if (StringUtils.isEmpty(apkPath)) {
            ToastUtils.showLong(R.string.update_install_error);
            setInstallText(false);
            return;
        }
        File apkFile = new File(apkPath);
        if (!apkFile.exists()) {
            ToastUtils.showLong(R.string.update_install_error);
            setInstallText(false);
            return;
        }
        AppUtils.installApp(apkFile);
    }

    private void setInstallText(boolean install) {
        if (install) {
            tvInstall.setVisibility(View.VISIBLE);
            tvInstall.setText(R.string.update_install_now);
            ClickUtils.applySingleDebouncing(tvInstall, v -> install());
        } else {
            tvInstall.setVisibility(View.VISIBLE);
            tvInstall.setText(R.string.update_re_download);
            ClickUtils.applySingleDebouncing(tvInstall, v -> download());
        }
    }
}
