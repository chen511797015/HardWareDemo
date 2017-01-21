package cn.pax.hardwaredemo.util;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.List;


/**
 * Created by chendd on 2017/1/9.
 * WIFI管理
 */

public class WifiAdmin {

    private static final String TAG = "WifiAdmin";
    Context mContext;
    WifiManager mWifiManager;

    WifiInfo mWifiInfo;
    // 定义一个WifiLock
    WifiManager.WifiLock mWifiLock;

    // 扫描出的网络连接列表
    private List<ScanResult> mWifiList;
    // 网络连接列表
    private List<WifiConfiguration> mWifiConfiguration;


    /**
     * 构造有参
     *
     * @param mContext
     */
    public WifiAdmin(Context mContext) {
        this.mContext = mContext;
        // 取得WifiManager对象
        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        //取得WifiInfo对象
        mWifiInfo = mWifiManager.getConnectionInfo();
    }


    /**
     * 打开wifi
     */
    public void openWifi() {

        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
    }

    /**
     * 关闭wifi
     */
    public void closeWifi() {
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
        }
    }


    /**
     * 检查当前WIFI状态
     *
     * @return
     */
    public int checkWifiStatus() {
        return mWifiManager.getWifiState();
    }


    /**
     * 锁定WIFI LOCK
     */
    public void acquireWifiLock() {
        mWifiLock.acquire();
    }

    /**
     * 解锁WIFI LOCK
     */
    public void releaseWifiLock() {
        // 判断时候锁定
        if (mWifiLock.isHeld()) {
            mWifiLock.acquire();
        }
    }


    /**
     * 创建一个WifiLock
     */
    public void createWifiLock() {
        mWifiLock = mWifiManager.createWifiLock("Test");
    }


    /**
     * 得到配置好的网络
     *
     * @return
     */
    public List<WifiConfiguration> getWifiConfiguration() {
        return mWifiConfiguration;
    }

    /**
     * 指定配置好的网络进行连接
     *
     * @param index
     */
    public void connectConfiguration(int index) {
        // 索引大于配置好的网络索引返回
        if (index > mWifiConfiguration.size()) {
            return;
        }
        // 连接配置好的指定ID的网络
        mWifiManager.enableNetwork(mWifiConfiguration.get(index).networkId,
                true);
    }

    /**
     * 开始扫描
     */
    public void startScan() {
        mWifiManager.startScan();
        // 得到扫描结果
        mWifiList = mWifiManager.getScanResults();
        // 得到配置好的网络连接
        mWifiConfiguration = mWifiManager.getConfiguredNetworks();

    }

    /**
     * 得到网络列表
     *
     * @return
     */
    public List<ScanResult> getWifiList() {
        if (mWifiList != null)
            return mWifiList;
        else
            return null;
    }


    /**
     * 查看扫描结果
     *
     * @return
     */
    public StringBuilder lookUpScan() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mWifiList.size(); i++) {
            stringBuilder
                    .append("Index_" + new Integer(i + 1).toString() + ":");
            // 将ScanResult信息转换成一个字符串包
            // 其中把包括：BSSID、SSID、capabilities、frequency、level
            stringBuilder.append((mWifiList.get(i)).toString());
            stringBuilder.append("/n");
        }
        return stringBuilder;
    }


    /**
     * 得到MAC地址
     */
    public String getMacAddress() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
    }

    /**
     * 得到接入点的BSSID
     *
     * @return
     */
    public String getBSSID() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
    }

    /**
     * 得到Rssi值
     *
     * @return
     */
    public int getRssi() {
        return 0;
    }

    /**
     * 得到IP地址
     *
     * @return
     */
    public int getIPAddress() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
    }

    /**
     * 得到连接的ID
     *
     * @return
     */
    public int getNetworkId() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
    }

    /**
     * 得到WifiInfo的所有信息包
     *
     * @return
     */
    public WifiInfo getWifiInfo() {
        return (mWifiInfo == null) ? null : mWifiInfo;
    }

    /**
     * 添加一个网络并连接
     *
     * @param wcg
     */
    public void addNetwork(WifiConfiguration wcg) {
        int wcgID = mWifiManager.addNetwork(wcg);
        mWifiManager.enableNetwork(wcgID, true);
    }

    /**
     * 断开指定ID的网络
     *
     * @param netId
     */
    public void disconnectWifi(int netId) {
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
    }


}
