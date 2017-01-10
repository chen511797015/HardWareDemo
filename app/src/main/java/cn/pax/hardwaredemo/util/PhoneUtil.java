package cn.pax.hardwaredemo.util;

import android.app.Activity;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * Created by chendd on 2017/1/4.
 * 手机工具
 */

public class PhoneUtil {

    Context mContext;

    public static PhoneUtil init(Context mContext) {
        return new PhoneUtil(mContext);
    }

    public PhoneUtil(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 获取屏幕分辨率
     *
     * @param mContext
     * @return 宽*高
     */

    public static String getScreenWidthAndHeiht(Context mContext) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels + "*" + dm.heightPixels;

    }

    /**
     * 获取当前屏幕
     */
    public static Display[] getDisplayArray(Context mContext) throws Exception {
        DisplayManager mManager = (DisplayManager) mContext.getSystemService(Context.DISPLAY_SERVICE);
        return mManager.getDisplays();
    }


    /**
     * 获取系统亮度
     *
     * @param mContext
     * @return
     * @throws Exception
     */
    public static int getSystemBrightness(Context mContext) throws Exception {
        return Settings.System.getInt(mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
    }


}
