package cn.pax.hardwaredemo.util;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.view.WindowManager;

/**
 * Created by chendd on 2017/3/4.
 */

public class DeviceUtil {

    /**
     * 获取系统亮度
     * 取值在(0 -- 255)之间
     */
    public static int getSystemScreenBrightness(Context context) {
        int values = 0;
        try {
            values = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return values;
    }

    /**
     * 设置系统亮度
     *
     * @param systemBrightness 返回的亮度值是处于0-255之间的整型数值
     */
    public static boolean setSystemScreenBrightness(Context context, int systemBrightness) {
        return Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, systemBrightness);
    }

    /**
     * 系统是否自动调节亮度
     * return true 是自动调节亮度   return false 不是自动调节亮度
     */
    public static boolean isAutoBrightness(Activity activity) {
        int autoBrightness = Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        try {
            autoBrightness = Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        if (autoBrightness == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 关闭系统自动调节亮度
     */
    public static void stopAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    /**
     * 打开系统自动调节亮度
     */
    public static void startAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    /**
     * 请求屏幕常亮
     *
     * @param activity
     */
    public static void requireScreenOn(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * 取消屏幕常亮
     *
     * @param activity
     */
    public static void releaseScreenOn(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
