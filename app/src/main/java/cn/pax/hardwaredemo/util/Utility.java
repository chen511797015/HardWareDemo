package cn.pax.hardwaredemo.util;

import android.util.Log;


/**
 * Created by chendd on 2017/1/19.
 */

public class Utility {

    private static final String TAG = "Utility";


    /// <summary>
    /// 字节数组节转换成二进制字符串
    /// </summary>
    /// <param name="b">要转换的字节数组</param>
    /// <returns></returns>
    public static String ByteArrayToBinaryString(byte[] byteArray) {
        StringBuilder sb = new StringBuilder();
        String ZERO = "00000000";
        for (int i = 0; i < byteArray.length; i++) {
            String s = Integer.toBinaryString(byteArray[i]);
            if (s.length() >
                    8) {
                s = s.substring(s.length() - 8);
            } else if (s.length()
                    < 8) {
                s = ZERO.substring(s.length()) + s;
            }
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * byte数组转换成十六进制
     *
     * @param bArray
     * @return
     */
    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 数组转成十六进制字符串
     *
     * @param b
     * @return HexString
     */
    public static String toHexString1(byte[] b) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; ++i) {
            buffer.append(toHexString1(b[i]));
        }
        return buffer.toString();
    }

    public static String toHexString1(byte b) {
        String s = Integer.toHexString(b & 0xFF);
        if (s.length() == 1) {
            return "0" + s;
        } else {
            return s;
        }
    }
}
