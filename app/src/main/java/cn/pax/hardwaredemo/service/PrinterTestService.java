package cn.pax.hardwaredemo.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;


import com.pax.api.PrintManager;


import cn.pax.hardwaredemo.R;


/**
 * Created by chendd on 2017/2/22.
 */

public class PrinterTestService extends Service {
    private static final String TAG = "PrinterTestService";

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate: " + Thread.currentThread().getName());
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: " + Thread.currentThread().getName());
        new Thread() {
            @Override
            public void run() {
                try {
                    PrintManager printManager = PrintManager.getInstance(getApplicationContext());
                    Thread.sleep(3000);
                    printManager.prnInit();
                    //printManager.prnStr("print test ...", "GBK");
                    printManager.prnBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.white));
                    //printManager.prnStartCut(1);
                    printManager.prnClose();

                    Log.e(TAG, "****************打印走纸结束*****************");
                    stopSelf();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "***************onDestroy***************");
    }
}
