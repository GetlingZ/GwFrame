package com.getling.gwframe.sample.ui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.getling.gwframe.sample.ui.main.MainActivity;

/**
 * @Author: getling
 * @CreateDate: 2021/1/4 14:02
 * @Description:
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) {
            Intent mIntent = new Intent(context, MainActivity.class);  // 要启动的Activity
            //1.如果自启动APP，参数为需要自动启动的应用包名
            //Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
            //下面这句话必须加上才能开机自动运行app的界面
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //2.如果自启动Activity
            context.startActivity(mIntent);
            //3.如果自启动服务
//            context.startService(mIntent);
        }
    }
}
