package cn.pax.hardwaredemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


import com.pax.api.NewPrinterManager;
import com.pax.api.PrintManager;

import cn.pax.hardwaredemo.util.PrinterUtil;


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
                    //获取usb权限
                    NewPrinterManager.getInstance(getApplicationContext());
                    //打印测试页
                    //PrinterUtil.getInstance(getApplicationContext()).getUsbStatus();
                    //切刀前走纸距离
                    //PrinterUtil.writeData(getApplicationContext(), new byte[]{0x1b, 0x23, 0x23, 0x43, 0x54, 0x46, 0x44, 0x78, 0x00});
                    //PrinterUtil.writeData(getApplicationContext(), "print test ...");

                    //使用新的jar打印
//                    PrintManager printManager = PrintManager.getInstance(getApplicationContext());
//                    printManager.prnStr("print test ...", "GBK");
//                    printManager.prnStartCut(1);
//                    printManager.prnClose();

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
}
