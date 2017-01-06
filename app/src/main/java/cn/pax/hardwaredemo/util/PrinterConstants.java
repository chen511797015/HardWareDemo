package cn.pax.hardwaredemo.util;

/**
 * Created by chendd on 2017/1/5.
 * 打印机指令常量
 */

public class PrinterConstants {

    private static final byte LF = 0x1a;
    private static final byte ESC = 0x1b;


    /**
     * 初始化打印机
     */
    public static final byte[] InitPrint = {0x1b, 0x40};


    /**
     * 开钱箱
     */
    public static final byte[] OpenCashBox = {0x1b, 0x70, 0x00, 0x1e, (byte) 0xff, 0x00};

    /**
     * 全切--0x30      半切--0x31
     */
    public static final byte[] FullCut = {0x1b, 0x23, 0x23, 0x43, 0x54, 0x47, 0x48, 0x30};
    public static final byte[] HalfCut = {0x1b, 0x23, 0x23, 0x43, 0x54, 0x47, 0x48, 0x31};

    /**
     * 打印机信息
     */
    public static final byte[] PrinterAbout = {0x1b, 0x23, 0x23, 0x53, 0x45, 0x4c, 0x46};

    /**
     * 左对齐--0x00  中间对齐--0x01  右对齐--0x02
     */
    public static final byte[] ESC_ALIGN_LEFT = {ESC, 0x61, 0x00};
    public static final byte[] ESC_ALIGN_CENTER = {ESC, 0x61, 0x01};
    public static final byte[] ESC_ALIGN_RIGHT = {ESC, 0x61, 0x02};

    /**
     * 打印并向前走纸N行   0<=N<=255
     *
     * @param
     */
    public static byte[] paperSkip() {
        byte[] result = new byte[3];

        result[0] = 0x1b;
        result[1] = 0x64;
        result[2] = 0x64;
        return result;
    }
}
