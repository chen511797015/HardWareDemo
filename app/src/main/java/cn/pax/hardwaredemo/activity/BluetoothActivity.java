package cn.pax.hardwaredemo.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.adapter.BluetoothShowAdapter;
import cn.pax.hardwaredemo.base.BaseActivity;
import cn.pax.hardwaredemo.util.ToastUtil;

/**
 * 蓝牙测试界面
 */

public class BluetoothActivity extends BaseActivity {

    private static final String TAG = "BluetoothActivity";

    LinearLayout ll_bluetooth_refresh;//刷新蓝牙设备
    ImageView iv_bluetooth_refresh;//刷新旋转图标
    ToggleButton tb_bluetooth_open;//蓝牙开关
    ListView lv_bluetooth_show;// 显示蓝牙信息
    BluetoothAdapter mBluetoothAdapter;

    List<BluetoothDevice> mBluetoothList;

    CountDownTimer mCountDownTimer;

    static final int REQUEST_ENABLE_BLUETOOTH = 1011;//蓝牙请求权限
    private BluetoothShowAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_bluetooth);

    }

    @Override
    protected void findView() {
        ll_bluetooth_refresh = (LinearLayout) findViewById(R.id.ll_bluetooth_refresh);
        iv_bluetooth_refresh = (ImageView) findViewById(R.id.iv_bluetooth_refresh);
        tb_bluetooth_open = (ToggleButton) findViewById(R.id.tb_bluetooth_open);
        lv_bluetooth_show = (ListView) findViewById(R.id.lv_bluetooth_show);

    }

    @Override
    protected void initEvent() {

        setOnLlRefreshListener();

        setOnTbClickListener();

    }


    @Override
    protected void init() {


        mBluetoothList = new ArrayList<>();

        mAdapter = new BluetoothShowAdapter(this, mBluetoothList);
        lv_bluetooth_show.setAdapter(mAdapter);

        initTimer();

        initBluetooth();

    }

    private void initTimer() {
        mCountDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Log.e(TAG, "倒计时结束!");
                stopSearchBluetoothDevice();
            }
        };
    }

    /**
     * 停止搜索蓝牙设备
     */
    private void stopSearchBluetoothDevice() {
        iv_bluetooth_refresh.clearAnimation();
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
    }

    private void initBluetooth() {

        IntentFilter mFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, mFilter);
        //获取本地蓝牙适配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            ToastUtil.showToast("该设备不支持蓝牙!");
        }

        if (mBluetoothAdapter.isEnabled()) {
            tb_bluetooth_open.setChecked(true);
        } else {
            tb_bluetooth_open.setChecked(false);
        }

        if (tb_bluetooth_open.isChecked()) {
            startRotateAnimation();
            searchBluetoothDevice();
        }


    }

    /**
     * 蓝牙开关
     */
    private void setOnTbClickListener() {
        tb_bluetooth_open.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.e(TAG, "打开蓝牙连接");
                    ll_bluetooth_refresh.setClickable(true);
                    openBluetooth();
                } else {
                    Log.e(TAG, "关闭蓝牙连接");
//                    mBluetoothAdapter.disable();
                    startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
                    ll_bluetooth_refresh.setClickable(false);
                }
            }
        });
    }

    /**
     * 打开蓝牙
     */
    private void openBluetooth() {
        if (!mBluetoothAdapter.isEnabled()) {
            //弹出对话框提示用户是否打开
            Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(mIntent, REQUEST_ENABLE_BLUETOOTH);
        }
    }


    /**
     * 刷新蓝牙设备
     */
    private void setOnLlRefreshListener() {
        ll_bluetooth_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "点击了刷新设备 ");
                startRotateAnimation();
                searchBluetoothDevice();
            }
        });
    }

    /**
     * 搜寻蓝牙设备
     */
    private void searchBluetoothDevice() {
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        mBluetoothAdapter.startDiscovery();
        mBluetoothList.clear();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 刷新图标开始旋转动画
     */
    private void startRotateAnimation() {
        mCountDownTimer.start();
        RotateAnimation mAnimation = new RotateAnimation(
                0f, 359f,//旋转开始,结束角度
                Animation.RELATIVE_TO_SELF, 0.5f,//X轴旋转模式,自身
                Animation.RELATIVE_TO_SELF, 0.5f);//Y轴旋转模式,自身
        mAnimation.setDuration(1000);//动画时间
        mAnimation.setInterpolator(new LinearInterpolator());//不停顿
        mAnimation.setFillAfter(true);//保存最后的状态
        mAnimation.setRepeatCount(-1);//无限循环
        iv_bluetooth_refresh.startAnimation(mAnimation);
//        iv_bluetooth_refresh.clearAnimation();//停止动画

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: " + resultCode);
        if (resultCode == REQUEST_ENABLE_BLUETOOTH) {
            //蓝牙授权成功
            Log.e(TAG, "授权成功!");
        } else if (resultCode == 0) {
            //蓝牙授权失败
            tb_bluetooth_open.setChecked(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBluetoothAdapter != null) {
            //关闭蓝牙搜索
            mBluetoothAdapter.cancelDiscovery();
        }
        this.unregisterReceiver(mReceiver);
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }


    /**
     * 用于接收ACTION_FOUND广播的BroadcastReceiver
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //发现设备
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //获取设备对象
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.e(TAG,
                        "Name: " + mDevice.getName() +
                                ",Address:" + mDevice.getAddress() +
                                ",Type:" + mDevice.getType() +
                                ",BondState:" + mDevice.getBondState() +
                                ",Uuids:" + mDevice.getUuids() +
                                ",BluetoothClass:" + mDevice.getBluetoothClass()
                );
                if (!mBluetoothList.contains(mDevice)) {
                    mBluetoothList.add(mDevice);
                    mAdapter.notifyDataSetChanged();
                }

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.e(TAG, "没有发现蓝牙设备");
            }
        }
    };
}
