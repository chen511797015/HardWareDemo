package cn.pax.hardwaredemo;

import android.app.Application;

import com.orhanobut.logger.Logger;
import com.pax.api.PrintException;
import com.pax.api.PrintManager;

import cn.pax.hardwaredemo.util.PrinterUtil;
import cn.pax.hardwaredemo.util.ToastUtil;

/**
 * Created by chendd on 2017/1/4.
 * 全局Application
 */

public class HardWareApplication extends Application {

    private static final String TAG = "HardWareApplication";

    HardWareApplication mHardWareApplication;

    public static HardWareApplication getInstance() {
        return new HardWareApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init(TAG);
        ToastUtil.init(this);
        //PrinterUtil.getInstance(this).openUsb();

        //初始化打印机
        try {
            PrintManager.getInstance(this);
        } catch (PrintException e) {
            e.printStackTrace();
        }

    }

}
