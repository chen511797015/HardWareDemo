package cn.pax.hardwaredemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.pax.api.PrinterManager;

import cn.pax.hardwaredemo.activity.MainActivity;
import cn.pax.hardwaredemo.service.PrinterTestService;

import static android.content.ContentValues.TAG;

/**
 * Created by chendd on 2017/2/22.
 */

public class BootupReceiver extends BroadcastReceiver {

    private static final String TAG = "BootupReceiver";
    private static final String ACTION_USB_PERMISSION =
            "com.android.example.USB_PERMISSION";
    private PrinterManager printerManager = null;


    @Override
    public void onReceive(final Context context, Intent intent) {
        final String action = intent.getAction();

        if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
            //MainActivity就是开机显示的界面
            Intent mBootIntent = new Intent(context, MainActivity.class);
            //下面这句话必须加上才能开机自动运行app的界面
            mBootIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mBootIntent);

            Intent serviceIntent = new Intent(context, PrinterTestService.class);
            context.startService(serviceIntent);
        }

//        if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
//            printerManager = PrinterManager.getInstance(context);
//            try {
//                printerManager.prnStr("print test....");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

//        if (ACTION_USB_PERMISSION.equals(action)) {
//            new Thread() {
//                @Override
//                public void run() {
//                    try {
//                        //if (printerManager != null) {
//                            printerManager = PrinterManager.getInstance(context);
//                            Thread.sleep(3000);
//                            printerManager.prnStr("print test ...");
//                            printerManager.prnStartCut(1);
//                            printerManager = null;
//                            Log.e(TAG, "onReceive: " + action);
//                       // }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }.start();
//        }
    }
}
