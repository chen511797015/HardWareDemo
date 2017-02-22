package cn.pax.hardwaredemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by chendd on 2017/2/22.
 */

public class PrinterTestService extends Service {
    private static final String TAG = "PrinterTestService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind: -----------------" + Thread.currentThread().getName());
        return null;
    }
}
