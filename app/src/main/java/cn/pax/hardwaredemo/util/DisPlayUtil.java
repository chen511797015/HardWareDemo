package cn.pax.hardwaredemo.util;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;

import static android.content.Context.DISPLAY_SERVICE;

/**
 * Created by chendd on 2017/3/6.
 */

public class DisplayUtil {

    /**
     * 获取副屏
     *
     * @param mContext
     * @return
     * @throws Exception
     */
    public static Display getDisplays(Context mContext) throws Exception {
        Display[] var = null;
        DisplayManager mDisplayManage = (DisplayManager) mContext.getSystemService(DISPLAY_SERVICE);
        var = mDisplayManage.getDisplays();
        if (var.length == 1)
            return null;
        else
            return var[1];
    }
}
