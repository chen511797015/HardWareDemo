package cn.pax.hardwaredemo.util;

import android.content.Context;

import java.io.UnsupportedEncodingException;

/**
 * Created by chendd on 2017/1/5.
 * 打印机工具
 */

public class PrinterUtil {

    static UsbAdmin mUsbAdmin;

    public static UsbAdmin getInstance(Context mContext) {
        if (mUsbAdmin == null) {
            mUsbAdmin = new UsbAdmin(mContext);
        }
        return mUsbAdmin;
    }


    /**
     * 写入字节数据
     */
    public static void writeData(Context mContext, byte[] mCommand) {
        if (mUsbAdmin == null) {
            getInstance(mContext);
        }
        mUsbAdmin.sendCommand(mCommand);
    }


    /**
     * 写入字符串
     *
     * @param mContext
     * @param mCommand :需要写入的字符串
     */
    public static void writeData(Context mContext, String mCommand) {
        if (mUsbAdmin == null) {
            getInstance(mContext);
        }

        //byte[] SendCut = {0x0a, 0x0a, 0x1d, 0x56, 0x01};
        try {
            mUsbAdmin.sendCommand(mCommand.getBytes("GBK"));
            mUsbAdmin.sendCommand(PrinterConstants.FullCut);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
