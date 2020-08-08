package com.getling.gwframe.utils;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.getling.gwframe.bus.BusUtil;
import com.getling.gwframe.bus.event.PermissionEvent;
import com.getling.gwframe.config.AppConfig;

import java.util.List;

/**
 * @Author: getling
 * @CreateDate: 2020/3/21 9:04
 * @Description:
 */
public class PermissionBlankjUtil {

    private static String[] permissionGroups = {PermissionConstants.CAMERA, PermissionConstants.STORAGE};

    public static boolean checkPermission(Context context) {
        boolean hasPermission =
                PermissionUtils.isGranted(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (!hasPermission) {
            String msg = "《" + AppConfig.AppName + "》使用过程中需要以下权限：\n" +
                    "·使用相机\n" +
                    "·访问网络\n" +
                    "·访问手机存储空间\n" +
                    "您是否同意？";
            DialogUtil.showNormalDialog(context,
                    false,
                    "权限申请",
                    msg,
                    "同意并继续",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermission(context);
                        }
                    },
                    "退出",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AppUtils.exitApp();
                        }
                    });
        }
        return hasPermission;
    }

    private static void requestPermission(Context context) {
        PermissionUtils.permission(permissionGroups)
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        BusUtil.postEvent(new PermissionEvent());
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        StringBuilder data = new StringBuilder();
                        for (String info : permissionsDenied) {
                            data.append(info);
                        }
                        for (String info : permissionsDeniedForever) {
                            data.append(info);
                        }
                        showSettingDialog(context, data.toString());
                    }
                })
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(ShouldRequest shouldRequest) {
                        shouldRationaleDialog(context, shouldRequest);
                    }
                })
                .request();
    }

    private static void showSettingDialog(final Context context, String data) {
        LogUtils.e(data);
        String msg = "《" + AppConfig.AppName + "》在使用过程中需要以下权限：\n";
        if (data.contains("CAMERA")) {
            msg += "·使用相机\n";
        }
        if (data.contains("STORAGE")) {
            msg += "·访问手机存储空间";
        }
        DialogUtil.showNormalDialog(context,
                false,
                "手动授权",
                msg,
                "去设置",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtils.launchAppDetailsSettings();
                        AppUtils.exitApp();
                    }
                },
                "退出",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppUtils.exitApp();
                    }
                });
    }

    private static void shouldRationaleDialog(Context context, PermissionUtils.OnRationaleListener.ShouldRequest shouldRequest) {
        DialogUtil.showNormalDialog(context,
                false,
                "权限被拒绝",
                "是否重新请求？",
                "确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        shouldRequest.again(true);
                    }
                },
                "取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        shouldRequest.again(false);
                    }
                });
    }
}
