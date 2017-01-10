package cn.pax.hardwaredemo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by chendd on 2017/1/9.
 * WIFI,网络工具
 */

public class NetWorkUtil {
    private static final String TAG = "NetWorkUtil";
    //-----------------WIFI信号强度------------------------------
    public static final int WIFI_STATUS_BEST = 0;
    public static final int WIFI_STATUS_GOOD = 1;
    public static final int WIFI_STATUS_GENERAL = 2;
    public static final int WIFI_STATUS_POOR = 3;
    public static final int WIFI_STATUS_NO_SIGNAL = 4;


    /**
     * 检测当前网络是否可用
     *
     * @param mContext
     * @return
     */
    public static boolean checkNetStatus(Context mContext) {
        try {
            //获取手机所有连接管理对象
            ConnectivityManager mManger = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取网络连接管理对象
            NetworkInfo mNetInfo = mManger.getActiveNetworkInfo();
            //判断当前网络是否连接
            if (mNetInfo != null && mNetInfo.isConnected()) {
                if (mNetInfo.getState() == NetworkInfo.State.CONNECTED)
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 检测WIFI状态
     *
     * @param mContext
     * @return
     */
    public static int checkWifiStatus(Context mContext) {
        try {
            WifiManager mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();
            //获得信号强度值
            int mRssi = mWifiInfo.getRssi();
            if (mRssi >= -50) {
                return WIFI_STATUS_BEST;
            } else if (mRssi < -50 && mRssi >= -70) {
                return WIFI_STATUS_GOOD;
            } else if (mRssi < -70 && mRssi >= -80) {
                return WIFI_STATUS_GENERAL;
            } else if (mRssi < -80 && mRssi >= -100) {
                return WIFI_STATUS_POOR;
            } else {
                return WIFI_STATUS_NO_SIGNAL;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return WIFI_STATUS_NO_SIGNAL;
    }
}
