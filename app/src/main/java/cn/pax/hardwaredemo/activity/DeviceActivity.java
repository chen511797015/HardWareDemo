package cn.pax.hardwaredemo.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.base.BaseActivity;
import cn.pax.hardwaredemo.util.PhoneAdmin;


/**
 * 本机信息
 */

public class DeviceActivity extends BaseActivity {

    private static final String TAG = "DeviceActivity";

    @BindView(R.id.iv_device_back)
    ImageView ivDeviceBack;
    @BindView(R.id.tv_device_name)
    TextView mTvDeviceName;//设备名称
    @BindView(R.id.tv_device_version)
    TextView mTvDeviceVersion;//系统版本号
    @BindView(R.id.tv_device_wlan)
    TextView mTvDeviceWlan;//WLAN MAC号
    @BindView(R.id.tv_device_lan)
    TextView mTvDeviceLan;//LAN MAC号
    @BindView(R.id.tv_device_router_mac)
    TextView mTvDeviceRouterMac;//Router mac号
    @BindView(R.id.activity_device)
    LinearLayout mActivityDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_device);

    }

    @Override
    protected void findView() {
        ButterKnife.bind(this);//绑定activity

    }

    @Override
    protected void initEvent() {

        ivDeviceBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void init() {


        //初始化数据信息
        PhoneAdmin mPhoneAdmin = new PhoneAdmin(this);
        mTvDeviceName.setText(mPhoneAdmin.getDeviceName());
        mTvDeviceVersion.setText(mPhoneAdmin.getAndroidVersion());
        mTvDeviceWlan.setText(mPhoneAdmin.getWlanMacAddress());
        mTvDeviceRouterMac.setText(mPhoneAdmin.getRouterMacAddress());

    }
}
