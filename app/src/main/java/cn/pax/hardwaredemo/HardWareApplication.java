package cn.pax.hardwaredemo;

import android.app.Application;

import com.orhanobut.logger.Logger;

/**
 * Created by chendd on 2017/1/4.
 */

public class HardWareApplication extends Application {

    private static final String TAG = "HardWareApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init(TAG);

    }
}
