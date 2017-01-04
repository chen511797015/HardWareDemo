package cn.pax.hardwaredemo.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by chendd on 2017/1/4.
 * 手机工具
 */

public class PhoneUtil {

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
}
