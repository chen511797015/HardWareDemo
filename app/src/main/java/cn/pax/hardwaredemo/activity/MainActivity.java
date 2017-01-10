package cn.pax.hardwaredemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.base.BaseActivity;
import cn.pax.hardwaredemo.ui.CustomBtnView;
import cn.pax.hardwaredemo.util.ToastUtil;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    CustomBtnView cb_device_info, cb_screen, cb_touchpanel, cb_printer, cb_scanning_gun, cb_cash_box;
    CustomBtnView cb_3g_4g, cb_router, cb_wifi, cb_bluetooth, cb_speaker, cb_camera;

    Class[] mClass = {DeviceActivity.class,
            ScreenActivity.class,
            TouchActivity.class,
            PrinterActivity.class,
            ScannerActivity.class,
            CashBoxActivity.class,
            GenerationActivity.class,
            RouterActivity.class,
            NetWorkActivity.class,
            BluetoothActivity.class,
            SpeakerActivity.class,
            CameraActivity.class
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
    }

    @Override
    protected void findView() {
        cb_device_info = (CustomBtnView) findViewById(R.id.cb_device_info);
        cb_screen = (CustomBtnView) findViewById(R.id.cb_screen);
        cb_touchpanel = (CustomBtnView) findViewById(R.id.cb_touchpanel);
        cb_printer = (CustomBtnView) findViewById(R.id.cb_printer);
        cb_scanning_gun = (CustomBtnView) findViewById(R.id.cb_scanning_gun);
        cb_cash_box = (CustomBtnView) findViewById(R.id.cb_cash_box);
        cb_3g_4g = (CustomBtnView) findViewById(R.id.cb_3g_4g);
        cb_router = (CustomBtnView) findViewById(R.id.cb_router);
        cb_wifi = (CustomBtnView) findViewById(R.id.cb_wifi);
        cb_bluetooth = (CustomBtnView) findViewById(R.id.cb_bluetooth);
        cb_speaker = (CustomBtnView) findViewById(R.id.cb_speaker);
        cb_camera = (CustomBtnView) findViewById(R.id.cb_camera);
    }

    @Override
    protected void initEvent() {

        cb_device_info.setOnClickListener(this);
        cb_screen.setOnClickListener(this);
        cb_touchpanel.setOnClickListener(this);
        cb_printer.setOnClickListener(this);
        cb_scanning_gun.setOnClickListener(this);
        cb_cash_box.setOnClickListener(this);
        cb_3g_4g.setOnClickListener(this);
        cb_router.setOnClickListener(this);
        cb_wifi.setOnClickListener(this);
        cb_bluetooth.setOnClickListener(this);
        cb_speaker.setOnClickListener(this);
        cb_camera.setOnClickListener(this);


    }

    @Override
    protected void init() {

        //初始化GridView数据

    }

    int index = 0;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_device_info:
                //TODO 本机信息
                index = 0;
                break;
            case R.id.cb_screen:
                //TODO 屏幕
                index = 1;
                break;
            case R.id.cb_touchpanel:
                //TODO 触摸
                index = 2;
                break;
            case R.id.cb_printer:
                //TODO 打印机
                index = 3;
                break;
            case R.id.cb_scanning_gun:
                //TODO 扫码枪
                index = 4;
                break;
            case R.id.cb_cash_box:
                //TODO 钱箱
                index = 5;
                break;
            case R.id.cb_3g_4g:
                //TODO 3G/4G --后续开发
                index = 6;

                break;
            case R.id.cb_router:
                //TODO 路由器
                index = 7;
                break;
            case R.id.cb_wifi:
                //TODO WIFI-NetWork
                index = 8;
                break;
            case R.id.cb_bluetooth:
                //TODO 蓝牙
                index = 9;
                break;
            case R.id.cb_speaker:
                //TODO 音量
                index = 10;
                break;
            case R.id.cb_camera:
                //TODO 相机
                index = 11;
                break;
        }

        Log.e(TAG, "选中界面: " + mClass[index].getSimpleName());
        if (index == 6 || index == 11) {
            ToastUtil.showToast("Stay tuned for!");
            return;
        }
        startActivity(new Intent(MainActivity.this, mClass[index]));
    }

}
