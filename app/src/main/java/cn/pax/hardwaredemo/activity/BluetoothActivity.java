package cn.pax.hardwaredemo.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.adapter.BluetoothShowAdapter;
import cn.pax.hardwaredemo.base.BaseActivity;
import cn.pax.hardwaredemo.util.ToastUtil;

import static android.bluetooth.BluetoothDevice.ACTION_BOND_STATE_CHANGED;


/**
 * 蓝牙测试界面
 */

public class BluetoothActivity extends BaseActivity {

    private static final String TAG = "BluetoothActivity";

    ImageView iv_bluetooth_back;//返回按钮
    LinearLayout ll_bluetooth_refresh;//刷新蓝牙设备
    ImageView iv_bluetooth_refresh;//刷新旋转图标
    Button btn_bluetooth_settings;//蓝牙设置,跳转到蓝牙设置界面
    ListView lv_bluetooth_show;// 显示蓝牙信息
    BluetoothAdapter mBluetoothAdapter;
    BluetoothShowAdapter mAdapter;//显示蓝牙信息

    List<BluetoothDevice> mBluetoothList;

    CountDownTimer mCountDownTimer;

    static final int REQUEST_ENABLE_BLUETOOTH = 1011;//蓝牙请求权限


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_bluetooth);

    }

    @Override
    protected void findView() {
        ll_bluetooth_refresh = (LinearLayout) findViewById(R.id.ll_bluetooth_refresh);
        iv_bluetooth_refresh = (ImageView) findViewById(R.id.iv_bluetooth_refresh);
        lv_bluetooth_show = (ListView) findViewById(R.id.lv_bluetooth_show);
        iv_bluetooth_back = (ImageView) findViewById(R.id.iv_bluetooth_back);
        btn_bluetooth_settings = (Button) findViewById(R.id.btn_bluetooth_settings);

        initBluetooth();

    }

    @Override
    protected void initEvent() {

        setOnLlRefreshListener();


        iv_bluetooth_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //选中的蓝牙设备
        lv_bluetooth_show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
            }
        });


        //跳转到设置界面
        btn_bluetooth_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
            }
        });
    }


    @Override
    protected void init() {

        mBluetoothList = new ArrayList<>();
        mAdapter = new BluetoothShowAdapter(this, mBluetoothList);
        lv_bluetooth_show.setAdapter(mAdapter);

        if (mBluetoothAdapter.isEnabled()) {
            ll_bluetooth_refresh.setClickable(true);
            searchBluetoothDevice();//如果是打开状态就扫描蓝牙信息
        } else {
            ll_bluetooth_refresh.setClickable(false);
        }

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
        mFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        mFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, mFilter);
        //获取本地蓝牙适配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //判断是否拥有蓝牙设备
        if (mBluetoothAdapter == null) {
            // ToastUtil.showToast("该设备不支持蓝牙!");
            ToastUtil.showToast("The device does not support Bluetooth!");

        } else {
            // ToastUtil.showToast("本机拥有蓝牙设备!");
            ToastUtil.showToast("Bluetooth device!");

        }


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
        //mCountDownTimer.start();
        RotateAnimation mAnimation = new RotateAnimation(
                0f, 359f,//旋转开始,结束角度
                Animation.RELATIVE_TO_SELF, 0.5f,//X轴旋转模式,自身
                Animation.RELATIVE_TO_SELF, 0.5f);//Y轴旋转模式,自身
        mAnimation.setDuration(1000);//动画时间
        mAnimation.setInterpolator(new LinearInterpolator());//不停顿
        mAnimation.setFillAfter(true);//保存最后的状态
        mAnimation.setRepeatCount(-1);//无限循环
        iv_bluetooth_refresh.startAnimation(mAnimation);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_ENABLE_BLUETOOTH) {
            //蓝牙授权成功
            Log.e(TAG, "蓝牙授权成功!");
        } else if (resultCode == 0) {
            //蓝牙授权失败
            Log.e(TAG, "蓝牙授权失败!");
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
     * 用于接收蓝牙状态广播的BroadcastReceiver
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //发现设备
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //获取设备对象
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.i(TAG,
                        "Name: " + mDevice.getName() +
                                ",Address:" + mDevice.getAddress() +
                                ",Type:" + mDevice.getType() +
                                ",BondState:" + mDevice.getBondState() +
                                ",Uuids:" + mDevice.getUuids() +
                                ",BluetoothClass:" + mDevice.getBluetoothClass()
                );
                //获取已连接的蓝牙信息
                Iterator<BluetoothDevice> iterator = mBluetoothAdapter.getBondedDevices().iterator();
                while (iterator.hasNext()) {
                    BluetoothDevice device = iterator.next();
                    if (!mBluetoothList.contains(device)) {
                        mBluetoothList.add(device);
                    }
                }

                if (!mBluetoothList.contains(mDevice)) {
                    mBluetoothList.add(mDevice);
                    mAdapter.notifyDataSetChanged();
                }
            }

            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.e(TAG, "扫描结束");
                iv_bluetooth_refresh.clearAnimation();
            }

            //判断设置界面蓝牙状态监听
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                int intExtra = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                switch (intExtra) {
                    case BluetoothAdapter.STATE_ON:
                        Log.e(TAG, "onReceive: 蓝牙打开...");
                        ll_bluetooth_refresh.setClickable(true);
                        searchBluetoothDevice();
                        break;

                    case BluetoothAdapter.STATE_OFF:
                        Log.e(TAG, "onReceive: 蓝牙关闭...");
                        ll_bluetooth_refresh.setClickable(false);
                        mBluetoothList.clear();
                        mAdapter.notifyDataSetChanged();
                        break;

                }
            }
        }
    };
}
