package cn.pax.hardwaredemo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static cn.pax.hardwaredemo.util.ConvertUtil.hexList2Byte;
import static cn.pax.hardwaredemo.util.ConvertUtil.hexStringToBytes;
import static cn.pax.hardwaredemo.util.ConvertUtil.sysCopy;


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


    /**
     * 解码Bitmap为位图字节流
     *
     * @param mBitmap
     * @return
     */
    public static byte[] decodeBitmap(Bitmap mBitmap) {
        int mWidth = mBitmap.getWidth();
        int mHeight = mBitmap.getHeight();

        List<String> mList = new ArrayList<>();//二进制集合
        StringBuffer mSb;


        //每行字节数(除以8，不足补0)
        int mBitLen = mWidth / 8;
        int mZeroCount = mWidth % 8;

        //每行需要补充的0
        String mZeroStr = "";
        if (mZeroCount > 0) {
            mBitLen = mWidth / 8 + 1;
            for (int i = 0; i < (8 - mZeroCount); i++) {
                mZeroStr = mZeroStr + "0";
            }
        }
        // 逐个读取像素颜色，将非白色改为黑色
        for (int i = 0; i < mHeight; i++) {
            mSb = new StringBuffer();
            for (int j = 0; j < mWidth; j++) {
                int mColor = mBitmap.getPixel(j, i);//获取BitMap图片中每一个点的Color的颜色值
                //颜色值的R G B
                int r = (mColor >> 16) & 0xff;
                int g = (mColor >> 8) & 0xff;
                int b = mColor & 0xff;

                //白色 +0   黑色 +1
                if (r > 160 && g > 160 && b > 160)
                    mSb.append("0");
                else
                    mSb.append("1");
            }

            //每一行结束的时候,补充剩余的0
            if (mZeroCount > 0) {
                mSb.append(mZeroStr);
            }
            mList.add(mSb.toString());
        }


        // binaryStr每8位调用一次转换方法，再拼合
        List<String> mHexList = ConvertUtil.binaryListToHexStringList(mList);
        String mCommandHexString = "1D763000";
        // 宽度指令
        String mWidthHexString = Integer
                .toHexString(mWidth % 8 == 0 ? mWidth / 8
                        : (mWidth / 8 + 1));


        if (mWidthHexString.length() > 2) {
            Log.e("decodeBitmap error", "宽度超出 width is too large");
            return null;
        } else if (mWidthHexString.length() == 1) {
            mWidthHexString = "0" + mWidthHexString;
        }
        mWidthHexString = mWidthHexString + "00";

        // 高度指令
        String mHeightHexString = Integer.toHexString(mHeight);
        //TODO 原始值2
        if (mHeightHexString.length() > 3) {
            Log.e("decodeBitmap error", "高度超出 height is too large" + mHeightHexString.length());
            return null;
        } else if (mHeightHexString.length() == 1) {
            mHeightHexString = "0" + mHeightHexString;
        }
        mHeightHexString = mHeightHexString + "00";
        List<String> mCommandList = new ArrayList<>();
        mCommandList.add(mCommandHexString + mWidthHexString + mHeightHexString);
        mCommandList.addAll(mHexList);
        return ConvertUtil.hexList2Byte(mCommandList);

    }
}
