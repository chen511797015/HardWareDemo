package cn.pax.hardwaredemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cn.pax.hardwaredemo.service.PrinterTestService;
import cn.pax.hardwaredemo.util.PreferencesUtil;


/**
 * Created by chendd on 2017/2/22.
 */

public class BootupReceiver extends BroadcastReceiver {

    private static final String TAG = "BootupReceiver";
    private static final String ACTION_USB_PERMISSION =
            "com.android.example.USB_PERMISSION";


    boolean isBootCompleted = false;


    @Override
    public void onReceive(final Context context, Intent intent) {
        final String action = intent.getAction();
        Log.e(TAG, "onReceive: " + action);
        if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
//            //MainActivity就是开机显示的界面
//            Intent mBootIntent = new Intent(context, MainActivity.class);
//            //下面这句话必须加上才能开机自动运行app的界面
//            mBootIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(mBootIntent);

            PreferencesUtil.putBoolean(context, "isBootCompleted", true);
            isBootCompleted = true;
        }

        if (ACTION_USB_PERMISSION.equals(action)) {
            Log.e(TAG, "onReceive: " + action);
            if (PreferencesUtil.getBoolean(context, "isBootCompleted", false)) {
                Intent serviceIntent = new Intent(context, PrinterTestService.class);
                context.startService(serviceIntent);
                PreferencesUtil.putBoolean(context, "isBootCompleted", false);
                //isBootCompleted = false;
            }
        }
    }
}
