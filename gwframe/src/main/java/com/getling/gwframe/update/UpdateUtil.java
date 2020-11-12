package com.getling.gwframe.update;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.getling.gwframe.app.GwFrame;
import com.getling.gwframe.constant.SPConstant;
import com.getling.gwframe.http.observer.SimpleObserver;
import com.getling.gwframe.update.entity.UpdateResponseEntity;

import java.io.File;

import rxhttp.RxHttp;


/**
 * @Author: getling
 * @CreateDate: 2019/12/10 9:48
 * @Description:
 */
public class UpdateUtil {
    public static final String GET_UPDATE = GwFrame.getInstance().factory.getUpdateInfoUrl();

    public static void checkUpdate(AppCompatActivity activity, boolean showToast) {
        RxHttp.postJson(GET_UPDATE)
                .add("factorycode", SPUtils.getInstance().getString(SPConstant.SP_FACTORY_CODE))
                .asClass(UpdateResponseEntity.class)
                .subscribe(new SimpleObserver<UpdateResponseEntity>() {
                    @Override
                    public void onSuccess(UpdateResponseEntity t) {
                        if (t.getEntity() != null) {
                            int currentVersion = AppUtils.getAppVersionCode();
                            int serviceVersion = Integer.parseInt(t.getEntity().getAppVersion().replace(".", ""));
                            if (currentVersion != serviceVersion) {
                                if (UpdateDownloadFragment.INSTANCE == null) {
                                    UpdateFragment fragment = UpdateFragment.newInstance(t.getEntity());
                                    fragment.show(activity.getSupportFragmentManager(), "update");
                                }
                            } else {
                                if (showToast) {
                                    ToastUtils.showLong("当前已是最新版本");
                                }
                                //删除旧包
                                deleteApk();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public static void deleteApk() {
        File file = new File(UpdateDownloadFragment.downloadPath);
        if (file.exists() && file.isDirectory()) {
            String[] children = file.list();
            if (children == null) {
                return;
            }
            for (String child : children) {
                if (child.endsWith(".apk")) {
                    new File(file, child).delete();
                }
            }
        }
    }
}
