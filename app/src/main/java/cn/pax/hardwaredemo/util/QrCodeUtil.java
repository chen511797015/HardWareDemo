package cn.pax.hardwaredemo.util;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * Created by chendd on 2017/1/11.
 * 二维码工具
 */

public class QrCodeUtil {

    private static final String TAG = "QrCodeUtil";

    public QrCodeUtil() {
    }

    public static QrCodeUtil getInstance() {
        return new QrCodeUtil();
    }


    /**
     * 创建二维码Bitmap
     *
     * @param mCodeString 要生成的文字
     * @param mWidth      二维码宽度
     * @param mHeight     二维码高度
     * @return 返回值B: Bitmap
     */
    public Bitmap createBitmap(String mCodeString, int mWidth, int mHeight) {
        Bitmap mBitmap = null;
        BitMatrix mBieMatrix = null;
        MultiFormatWriter mMultiFormatWriter = new MultiFormatWriter();

        try {
            mBieMatrix = mMultiFormatWriter.encode(mCodeString, BarcodeFormat.QR_CODE, mWidth, mHeight);
//            BarcodeEncoder mEncoder = new BarcodeEncoder();
//            mBitmap = mEncoder.createBitmap(mBieMatrix);
            return mBitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            Log.e(TAG, "创建二维码失败");
        } catch (IllegalArgumentException iae) {
            return null;
        }
        return mBitmap;
    }


}
