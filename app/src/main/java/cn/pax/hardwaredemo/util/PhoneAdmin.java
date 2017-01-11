package cn.pax.hardwaredemo.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chendd on 2017/1/10.
 * 系统信息工具
 */

public class PhoneAdmin {
    private static final String TAG = "PhoneAdmin";

    Context mContext;
    private final WifiManager mWifiManager;

    public PhoneAdmin(Context mContext) {
        this.mContext = mContext;

        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
    }


    /**
     * 系统的版本信息
     *
     * @return
     */
    public String getVersion() {
        PackageInfo info = null;
        try {
            info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info.versionName;
    }


    /**
     * 获取系统版本号
     *
     * @return
     */
    public int getVersionCode() {
        PackageInfo pi = null;
        try {
            pi = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi.versionCode;
    }


    /**
     * 获取手机型号
     *
     * @return
     */
    public String getDeviceName() {
        return Build.MODEL;
    }


    /**
     * 获取android版本信息
     *
     * @return
     */
    public String getAndroidVersion() {
        return Build.VERSION.RELEASE;

    }


    /**
     * 获取WLAN MAC地址
     */
    public String getWlanMacAddress() {
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        if (wifiInfo.getMacAddress() != null)
            return wifiInfo.getMacAddress();
        else
            return null;
    }


    /**
     * 获取以太网ip
     */
    public String getRouterIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 将int转换成ip地址
     *
     * @param ipAdress
     * @return
     */
    private String intToIp(int ipAdress) {
        return (ipAdress & 0xFF) + "." + ((ipAdress >> 8) & 0xFF) + "." + ((ipAdress >> 16) & 0xFF) + "." + ((ipAdress >> 24) & 0xFF);
    }

    /**
     * 获取以太网mac地址
     */
    public String getRouterMacAddress() {
        try {
            StringBuffer sb = new StringBuffer(1000);
            BufferedReader br = new BufferedReader(new FileReader("/sys/class/net/eth0/address"));
            char[] buf = new char[1024];
            int num = 0;
            while ((num = br.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, num);
                sb.append(readData);
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
