package cn.pax.hardwaredemo.bean;

/**
 * Created by chendd on 2017/1/9.
 * wifi扫描信息
 */

public class WifiScanResultBean {

    String SSID = "";//网络的名字
    String BSSID = "";//mac地址,接入点的地址
    int level = 0;//WIFI网络信号强度
    int frequency = 0;//当前WIFI设备附近热点的频率(MHz)
    String Capabilities = "";//网络接入的性能
    int status = 2;//连接状态: 0--已连接   1--正在连接    2--未连接

    public WifiScanResultBean() {
    }

    public String getCapabilities() {
        return Capabilities;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCapabilities(String capabilities) {
        Capabilities = capabilities;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }


    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "WifiScanResultBean{" +
                "SSID='" + SSID + '\'' +
                ", BSSID='" + BSSID + '\'' +
                ", level=" + level +
                ", frequency=" + frequency +
                ", Capabilities='" + Capabilities + '\'' +
                '}';
    }
}
