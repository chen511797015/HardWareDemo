package cn.pax.hardwaredemo.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.base.BaseActivity;


/**
 * 网络测试界面
 */

public class NetWorkActivity extends BaseActivity {
    private static final String TAG = "NetWorkActivity";
    ImageView iv_network_back;//返回按钮


    //---------------------Left------------------------
    ToggleButton tb_net_work_open;//WIFI开关
    ListView lv_net_show;//显示WIFI信息
    LinearLayout ll_net_refresh;//点击刷新
    ImageView iv_net_refresh;//刷新动画

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "初始化NewWork界面...");
        super.onCreate(savedInstanceState, R.layout.activity_net_work);

    }


    @Override
    protected void findView() {

        iv_network_back = (ImageView) findViewById(R.id.iv_network_back);
        tb_net_work_open = (ToggleButton) findViewById(R.id.tb_net_work_open);
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

    }

    @Override
    protected void initEvent() {

        iv_network_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void init() {

    }
}
