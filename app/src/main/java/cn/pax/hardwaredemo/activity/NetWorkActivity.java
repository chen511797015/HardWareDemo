package cn.pax.hardwaredemo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.security.Permission;
import java.security.Permissions;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.adapter.NetShowAdapter;
import cn.pax.hardwaredemo.base.BaseActivity;
import cn.pax.hardwaredemo.bean.WifiScanResultBean;
import cn.pax.hardwaredemo.util.PhoneAdmin;
import cn.pax.hardwaredemo.util.ToastUtil;
import cn.pax.hardwaredemo.util.WifiAdmin;


/**
 * 网络测试界面
 */

public class NetWorkActivity extends BaseActivity {
    private static final String TAG = "NetWorkActivity";
    ImageView iv_network_back;//返回按钮
    RelativeLayout m_rl_back;


    //---------------------Left------------------------
    ListView lv_net_show;//显示WIFI信息
    LinearLayout ll_net_refresh;//点击刷新
    ImageView iv_net_refresh;//刷新动画
    Button btn_net_settings;//跳转到设置界面

    //---------------------Right-----------------------
    TextView tv_net_wlan_name;//WLAN
    TextView tv_net_lan_name;//LAN
    TextView tv_net_wlan_ip;//WLAN IP
    TextView tv_net_lan_ip;//LAN IP
    TextView tv_net_wlan_mac;//WLAN MAC
    TextView tv_net_lan_mac;//LAN MAC

    TextView tv_net_signal_intensity;//信号强度
    ImageView iv_net_show_signal;//显示信号强度是否成功
    TextView tv_net_ping_gateway;//网关
    ImageView iv_net_show_ping_gateway;//显示网关成功
    TextView tv_net_website;//官网
    ImageView iv_net_show_website;//显示官网成功
    Button btn_net_start;//开始测试
    ImageView iv_net_show_start;//测试成功


    Animation mRotateAnim;//360度旋转动画
    WifiAdmin mWifiAdmin;
    WifiManager mWifiManager;

    List<WifiScanResultBean> mScanWifiList;
    NetShowAdapter mNetShowAdapter;
    WifiInfo mWifiInfo = null;


    final static int WIFI_SETTINGS_REQUEST_CODE = 10086;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "初始化NewWork界面...");
        super.onCreate(savedInstanceState, R.layout.activity_net_work);

    }


    @Override
    protected void findView() {

        iv_network_back = (ImageView) findViewById(R.id.iv_network_back);
        m_rl_back = (RelativeLayout) findViewById(R.id.m_rl_back);
        btn_net_settings = (Button) findViewById(R.id.btn_net_settings);
        lv_net_show = (ListView) findViewById(R.id.lv_net_show);
        ll_net_refresh = (LinearLayout) findViewById(R.id.ll_net_refresh);
        iv_net_refresh = (ImageView) findViewById(R.id.iv_net_refresh);
        tv_net_wlan_name = (TextView) findViewById(R.id.tv_net_wlan_name);
        tv_net_lan_name = (TextView) findViewById(R.id.tv_net_lan_name);
        tv_net_wlan_ip = (TextView) findViewById(R.id.tv_net_wlan_ip);
        tv_net_lan_ip = (TextView) findViewById(R.id.tv_net_lan_ip);
        tv_net_wlan_mac = (TextView) findViewById(R.id.tv_net_wlan_mac);
        tv_net_lan_mac = (TextView) findViewById(R.id.tv_net_lan_mac);
        tv_net_signal_intensity = (TextView) findViewById(R.id.tv_net_signal_intensity);
        iv_net_show_signal = (ImageView) findViewById(R.id.iv_net_show_signal);
        tv_net_ping_gateway = (TextView) findViewById(R.id.tv_net_ping_gateway);
        iv_net_show_ping_gateway = (ImageView) findViewById(R.id.iv_net_show_ping_gateway);
        tv_net_website = (TextView) findViewById(R.id.tv_net_website);
        iv_net_show_website = (ImageView) findViewById(R.id.iv_net_show_website);
        btn_net_start = (Button) findViewById(R.id.btn_net_start);
        iv_net_show_start = (ImageView) findViewById(R.id.iv_net_show_start);


        //动态监听wifi状态
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        mFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);//扫描状态
        this.registerReceiver(mReceiver, mFilter);

    }

    @Override
    protected void initEvent() {

        iv_network_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        m_rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        setOnRefreshClick();

        setOnBtnTestClick();


        lv_net_show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //自动跳转到系统wifi连接界面
                startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
            }
        });


        // 设置wifi
        btn_net_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), WIFI_SETTINGS_REQUEST_CODE);
            }
        });


    }


    @Override
    protected void init() {
        mScanWifiList = new ArrayList<>();

        mNetShowAdapter = new NetShowAdapter(this, mScanWifiList);
        lv_net_show.setAdapter(mNetShowAdapter);

        mRotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        mRotateAnim.setInterpolator(new LinearInterpolator());//匀速旋转
        mWifiManager = (WifiManager) getSystemService(WIFI_SERVICE);

        //初始化wifi信息
        mWifiAdmin = new WifiAdmin(this);
        mWifiAdmin.openWifi();
        ToastUtil.showToast("当前Wifi状态为: " + mWifiAdmin.checkWifiStatus());
        mWifiInfo = mWifiAdmin.getWifiInfo();
        initMyView();
        startAnimAndSearchWifi();

    }


    /**
     * 点击测试
     */
    private void setOnBtnTestClick() {
        btn_net_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWifiInfo = mWifiAdmin.getWifiInfo();
                if (mWifiInfo != null) {
                    iv_net_show_signal.setImageResource(R.mipmap.net_ok);
                    iv_net_show_ping_gateway.setImageResource(R.mipmap.net_ok);
                    iv_net_show_website.setImageResource(R.mipmap.net_ok);
                    tv_net_signal_intensity.setText(mWifiInfo.getRssi() + "db");
                    tv_net_ping_gateway.setText(intToIp(mWifiInfo.getIpAddress()));
                    iv_net_show_start.setVisibility(View.VISIBLE);
                }
                ToastUtil.showToast("WIFI测试...");
            }
        });
    }

    /**
     * 初始化数据信息
     */
    private void initMyView() {
        try {
            tv_net_wlan_name.setText(mWifiInfo.getSSID());
            tv_net_wlan_ip.setText("- - - -");
            tv_net_wlan_mac.setText(mWifiInfo.getMacAddress());

            //以太网设置
            tv_net_lan_mac.setText(new PhoneAdmin(this).getRouterMacAddress());
            tv_net_lan_ip.setText(intToIp(mWifiInfo.getIpAddress()));

            iv_net_show_website.setImageResource(R.mipmap.net_failure);
            iv_net_show_signal.setImageResource(R.mipmap.net_failure);
            iv_net_show_ping_gateway.setImageResource(R.mipmap.net_failure);
            tv_net_signal_intensity.setText("");
            tv_net_ping_gateway.setText("");


        } catch (Exception e) {
            e.printStackTrace();
        }
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
     * 点击刷新
     */
    private void setOnRefreshClick() {
        ll_net_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimAndSearchWifi();
            }
        });
    }

    /**
     * 开始搜索动画及查询wifi
     */
    private void startAnimAndSearchWifi() {
        iv_net_refresh.startAnimation(mRotateAnim);
        getAllNetWorkList();
    }

    /**
     * 获取所有的wifi热点
     */
    private void getAllNetWorkList() {
        //每次扫描之前清空上一次扫描结果
        if (mScanWifiList != null) {
            mScanWifiList.clear();
        }

        //扫描wifi信息
        mWifiAdmin.startScan();
        List<ScanResult> mScanResults = mWifiAdmin.getWifiList();
        mWifiInfo = mWifiAdmin.getWifiInfo();
        if (mScanResults != null) {
            for (ScanResult mScanResult : mScanResults) {
                WifiScanResultBean bean = new WifiScanResultBean();
                bean.setSSID(mScanResult.SSID);
                if (mWifiInfo.getSSID().contains(bean.getSSID())) {
                    bean.setStatus(0);
                }

                if (!mScanWifiList.contains(bean)) {
                    mScanWifiList.add(bean);
                }
            }

            //关闭动画
            iv_net_refresh.clearAnimation();
            mNetShowAdapter.notifyDataSetChanged();
        }

//        //获取已连接数据信息
//        List<WifiConfiguration> mWifiConfiguration = mWifiAdmin.getWifiConfiguration();
//        Log.e(TAG, "getAllNetWorkList: " + mWifiInfo.getBSSID());//连接状态,已完成
//        //得到连接多的wifi的数据信息
//        for (int i = 0; i < mWifiConfiguration.size(); i++) {
//            WifiConfiguration configuration = mWifiConfiguration.get(i);
//            //status: 0--   已连接   1--正在连接 2--未连接
//        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(mReceiver);
    }


    /**
     * 网络改变监听
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action)) {//监听wifi的打开与关闭,与wifi的连接无关
                int intExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);

                switch (intExtra) {
                    case WifiManager.WIFI_STATE_DISABLED:
                        //关闭
                        Log.e(TAG, "关闭: ");
                        //每次扫描之前清空上一次扫描结果
                        if (mScanWifiList != null) {
                            mScanWifiList.clear();
                            mNetShowAdapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
            //打开
            if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {//扫描完成
                startAnimAndSearchWifi();
            }


        }
    };
}