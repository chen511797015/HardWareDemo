package cn.pax.hardwaredemo.util;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import static android.R.attr.start;
import static android.R.id.list;


/**
 * Created by chendd on 2017/1/7.
 * 蓝牙管理工具
 */

public class BluetoothUtil {
    private static final String TAG = "BluetoothUtil";

    Context mContext;
    BluetoothAdapter mBluetoothAdapter;

    public static BluetoothUtil getInstance(Context mContext) {
        return new BluetoothUtil(mContext);
    }

    private BluetoothUtil(Context mContext) {
        this.mContext = mContext;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        IntentFilter mFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        mContext.registerReceiver(mReceiver, mFilter);
    }

    /**
     * 打开蓝牙设备
     */
    public boolean openBluetooth() {

        if (mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled()) {
            Log.e(TAG, "openBluetooth: 蓝牙关闭");
            //如果没有打开,弹出对话框是否打开蓝牙设备
            Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            ((Activity) mContext).startActivityForResult(mIntent, 1001);//参数二:请求码
        } else {
            Log.e(TAG, "openBluetooth: 蓝牙打开");
        }
        return true;
    }

    /**
     * 搜索蓝牙设备
     */
    public void searchBluetoothDevice() {
        boolean mDiscovery = mBluetoothAdapter.startDiscovery();
        Log.e(TAG, "searchBluetoothDevice: " + mDiscovery);

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
                Log.e(TAG, "onReceive: " + mDevice.getName());
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.e(TAG, "没有发现蓝牙设备");

            }
        }
    };
}
