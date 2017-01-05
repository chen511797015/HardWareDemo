package cn.pax.hardwaredemo.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by chendd on 2017/1/5.
 * 吐丝工具:全局唯一Toast对象,避免Toast的重复出现
 */

public class ToastUtil {


    static Toast mToast = null;
    static Context mApplicationContext = null;


    /**
     * 初始化Context
     *
     * @param mContext:全局上下文对象
     */
    public static void init(Context mContext) {
        mApplicationContext = mContext;
    }


    /**
     * 显示Toast
     *
     * @param mText
     */
    public static void showToast(String mText) {
        if (mToast == null) {
            mToast = Toast.makeText(mApplicationContext, mText, Toast.LENGTH_LONG);
        } else {
            mToast.setText(mText);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }


    /**
     * 取消Toast
     */
    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }


}
