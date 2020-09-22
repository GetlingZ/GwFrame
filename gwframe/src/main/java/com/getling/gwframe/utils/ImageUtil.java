package com.getling.gwframe.utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.Log;

import com.getling.gwframe.R;
import com.getling.gwframe.constant.CodeConstant;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

/**
 * @Author: getling
 * @CreateDate: 2019/11/28 11:19
 * @Description:
 */
public class ImageUtil {
    /**
     * 图片选择器
     *
     * @param activity activity
     */
    public static void selectImage(Activity activity, int max) {
        if (PermissionBlankjUtil.checkPermission(activity)) {
            Matisse.from(activity)
                    .choose(MimeType.ofImage(), false)
//                    .theme(R.style.MyMatisse)
                    .countable(true)
                    .capture(true)
                    .captureStrategy(new CaptureStrategy(true,
                            activity.getString(R.string.file_provider_path),
                            activity.getString(R.string.file_provider_directory)))
                    .maxSelectable(max)
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                    .thumbnailScale(0.85f)
                    .imageEngine(new GlideEngine()).setOnSelectedListener((uriList, pathList) -> Log.e(
                    "onSelected",
                    "onSelected: pathList=$pathList"
            ))
                    .showSingleMediaType(true)
                    .originalEnable(true)
                    .maxOriginalSize(10)
                    .autoHideToolbarOnSingleTap(true).setOnCheckedListener(isChecked -> Log.e(
                    "isChecked",
                    "onCheck: isChecked=$isChecked"
            ))
                    .forResult(CodeConstant.CODE_SELECT_PICTURE);
        }
    }
}
