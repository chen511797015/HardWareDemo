package cn.pax.hardwaredemo.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pax.hardwaredemo.util.BitmapUtil;
import cn.pax.hardwaredemo.util.PrinterConstants;
import cn.pax.hardwaredemo.util.PrinterUtil;

/**
 * Created by chendd on 2017/1/6.
 * 打印线程
 */

public class PrintThread implements Runnable {

    private static final String TAG = "PrintThread";


    Context mContext;
    int mResId;

    public PrintThread(Context mContext, int resId) {
        this.mContext = mContext;
        this.mResId = resId;
    }

    @Override
    public void run() {
        print();
    }

    /**
     * 打印
     */
    private void print() {
        try {
            ByteArrayOutputStream mBaos = new ByteArrayOutputStream();
            mBaos.write(PrinterConstants.ESC_ALIGN_CENTER);
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), mResId);
            //Bitmap comp = BitmapUtil.comp(bitmap);
            List<Bitmap> bitmaps = getBitmap(bitmap);
            for (int i = 0; i < bitmaps.size(); i++) {
                mBaos.write(PrinterUtil.decodeBitmap(bitmaps.get(i)));
            }
            PrinterUtil.writeData(mContext, mBaos.toByteArray());
            PrinterUtil.writeData(mContext, PrinterConstants.FullCut);

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "打印异常");
        }

    }


    /**
     * 循环读取图片
     *
     * @param bitmap 要读取的源文件bitmap
     * @return bitmap
     */
    public static List<Bitmap> getBitmap(Bitmap bitmap) {
        List<Bitmap> pieces = new ArrayList<>();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Log.e(TAG, "image width: " + width + ",image height:" + height);
        if (width > 576) { // FIXME 382为测出来的数据，有待商榷
            double ratio = (double) 576 / width;
            width = (int) 576;
            height *= ratio;
            bitmap = zoomBitmap(bitmap, width, height);
        }
        //int height = bitmap.getHeight();
        int count = height / 8;
        if (height < 8) {
            pieces.add(bitmap);
            return pieces;
        }
        for (int i = 0; i < count; i++) {
            Bitmap result = Bitmap.createBitmap(bitmap, 0, height / count * i,
                    bitmap.getWidth(), height / count);
            pieces.add(result);
        }
        return pieces;
    }

    /**
     * 压缩图片
     *
     * @param bmp          原始图片
     * @param desireWidth  压缩宽度
     * @param desireHeight 压缩高度
     * @return
     */
    private static Bitmap zoomBitmap(Bitmap bmp, int desireWidth, int desireHeight) {
        // 获得图片的宽高
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        // 设置想要的大小
        int newWidth = desireWidth;
        int newHeight = desireHeight;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newBmp = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix,
                true);
        return newBmp;
    }
}
