package cn.pax.hardwaredemo.util;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;

import static android.R.attr.id;
import static android.hardware.usb.UsbConstants.USB_ENDPOINT_XFER_CONTROL;

/**
 * Created by chendd on 2017/1/5.
 * USB管理工具
 */

public class UsbAdmin {


    Context mContext;

    UsbManager mUsbManager;//USB管理者
    UsbDevice mDevice;//USB设备
    PendingIntent mPermissionIntent = null;
    UsbEndpoint mEndpointIntr;//输出方向端点数    0--输出
    UsbDeviceConnection mConnection;

    static final String TAG = "UsbAdmin";
    static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";


    public UsbAdmin(Context mContext) {
        this.mContext = mContext;
        mUsbManager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
        mPermissionIntent = PendingIntent.getBroadcast(mContext, 0, new Intent(ACTION_USB_PERMISSION), 0);

        IntentFilter mFilter = new IntentFilter(ACTION_USB_PERMISSION);
        mContext.registerReceiver(mUsbReceiver, mFilter);

    }

    /**
     * 打开Usb设备
     */
    public void openUsb() {

        if (mDevice == null) {
            // 获取USB设备
            HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
            if (deviceList.size() == 0) {
                Log.d(TAG, "没有检测到USB设备!");
                return;
            }
            Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
            while (deviceIterator.hasNext()) {
                UsbDevice device = deviceIterator.next();
                //请求设备权限
                mUsbManager.requestPermission(device, mPermissionIntent);
            }
        } else {
            //设置USB设备信息
            setDevice(mDevice);

        }
    }

    /**
     * 关闭USB连接
     */
    public void closeUsb() {
        if (mConnection != null) {
            mConnection = null;
        }
    }


    /**
     * 判断USB连接状态
     */
    public boolean getUsbStatus() {
        if (mConnection != null)
            return true;
        else
            return false;
    }

    /**
     * 设置USB信息
     *
     * @param device
     */
    private void setDevice(UsbDevice device) {
        if (device != null) {
            mDevice = device;
            UsbInterface mUsbInterface = null;
            int interfaceCount = mDevice.getInterfaceCount();//usb接口数
            for (int i = 0; i < interfaceCount; i++) {
                UsbInterface mDeviceInterface = mDevice.getInterface(i);
                mUsbInterface = mDeviceInterface;
                if (mUsbInterface.getInterfaceClass() == UsbConstants.USB_CLASS_PRINTER) {
                    //打印机设备
                    int mUsbInterfaceEndpointCount = mUsbInterface.getEndpointCount();
                    for (int j = 0; j < mUsbInterfaceEndpointCount; j++) {
                        UsbEndpoint mUsbInterfaceEndpoint = mUsbInterface.getEndpoint(j);
                        if (mUsbInterfaceEndpoint.getDirection() == USB_ENDPOINT_XFER_CONTROL && mUsbInterfaceEndpoint.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
                            //获取打印机设备输出端点
                            mEndpointIntr = mUsbInterfaceEndpoint;
                            Log.d(TAG, "接口是: " + i + "      类是: " + mUsbInterface.getInterfaceClass() +
                                    "       端点是: " + j
                                    + "     方向是: " + mUsbInterfaceEndpoint.getDirection() +
                                    "       类型是: " + mUsbInterfaceEndpoint.getType());
                            break;

                        }
                    }
                }
            }

            // 建立USB连接
            if (mDevice != null && mUsbInterface != null) {
                UsbDeviceConnection connection = mUsbManager.openDevice(mDevice);
                if (connection != null && connection.claimInterface(mUsbInterface, true)) {//申明接口
                    mConnection = connection;
                    Log.d(TAG, "USB设备连接成功!");
                } else {
                    mConnection = null;
                    Log.d(TAG, "USB设备连接失败");
                }
            }
        } else {
            Log.d(TAG, "没有USB设备");
        }
    }


    /**
     * 发送指令
     *
     * @param mCommand
     * @return
     */
    public boolean sendCommand(byte[] mCommand) {
        synchronized (this) {
            int length = -1;
            if (mConnection != null) {
                // 批量传输数据: 传输方向,传输内容,传输内容长度,超时时间
                length = mConnection.bulkTransfer(mEndpointIntr, mCommand, mCommand.length, 10000);
            }
            if (length < 0) {
                Log.e(TAG, "数据传输失败: " + length);
                return false;
            } else {
                Log.e(TAG, "数据传输成功: " + length + "个字节");
                return true;
            }
        }
    }


    /**
     * USB的拔插是通过系统广播的形式传播的
     */
    BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            //获取权限成功,进行操作
                            setDevice(device);
                            Log.d(TAG, "获取USB权限成功!");
                        }

                    } else {
                        Log.d(TAG, "无法获取usb权限: " + device);
                    }
                }

            }

        }
    };


}
