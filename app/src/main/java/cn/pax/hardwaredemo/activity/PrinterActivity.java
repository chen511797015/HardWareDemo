package cn.pax.hardwaredemo.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pax.api.NewPrinterManager;
import com.pax.api.PrintManager;
import com.pax.api.ThreadPoolManager;

import java.io.ByteArrayOutputStream;

import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.base.BaseActivity;
import cn.pax.hardwaredemo.tool.PrintThread;
import cn.pax.hardwaredemo.util.PrinterConstants;
import cn.pax.hardwaredemo.util.PrinterUtil;
import cn.pax.hardwaredemo.util.ToastUtil;


/**
 * 打印机测试
 */

public class PrinterActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "PrinterActivity";

    Button btn_printer_bar_code;//打印条码
    Button btn_printer_qr_code;//打印二维码
    Button btn_printer_black_square;//打印黑色块
    ImageView iv_printer_back;//返回
    RelativeLayout m_rl_back;
    ThreadPoolManager mThreadPoolManager;

    int index = 0;
    private PrintManager printerManager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_printer);

    }

    @Override
    protected void findView() {

        btn_printer_bar_code = (Button) findViewById(R.id.btn_printer_bar_code);
        btn_printer_qr_code = (Button) findViewById(R.id.btn_printer_qr_code);
        btn_printer_black_square = (Button) findViewById(R.id.btn_printer_black_square);
        iv_printer_back = (ImageView) findViewById(R.id.iv_printer_back);
        m_rl_back = (RelativeLayout) findViewById(R.id.m_rl_back);

    }

    @Override
    protected void initEvent() {
        btn_printer_bar_code.setOnClickListener(this);
        btn_printer_qr_code.setOnClickListener(this);
        btn_printer_black_square.setOnClickListener(this);

        //返回
        iv_printer_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        m_rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void init() {
        mThreadPoolManager = ThreadPoolManager.getInstance();

    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");

        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        if (null != printerManager) {
            try {
                printerManager.prnClose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (null != printerManager) {
            try {
                printerManager.closeUsbReceiver();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        //首先判断一下打印机状态,没有连接,先申请连接
//        if (!PrinterUtil.getInstance(PrinterActivity.this).getUsbStatus()) {
//            PrinterUtil.getInstance(this).openUsb();
//        }
        switch (v.getId()) {
            case R.id.btn_printer_bar_code:
                Log.e(TAG, "打印条码");
                index = R.mipmap.bar_code_2;
                break;

            case R.id.btn_printer_qr_code:
                Log.e(TAG, "打印二维码 ");
                index = R.mipmap.pax_logo;
                break;

            case R.id.btn_printer_black_square:
                Log.e(TAG, "打印黑色块");
                index = R.mipmap.black_sp;
                break;
        }

        printBitmap(index);
        //prnBitmap(index);

    }

    private void prnBitmap(int index) {
        if (PrinterUtil.getInstance(PrinterActivity.this).getUsbStatus())
            new PrintThread(PrinterActivity.this, index).run();
        else
            ToastUtil.showToast(getResources().getString(R.string.Please_check_the_printer_status));
    }

    private void printBitmap(final int index) {
        mThreadPoolManager.execute(new Thread() {
            @Override
            public void run() {
                try {
                    printerManager = PrintManager.getInstance(PrinterActivity.this);
                    printerManager.prnInit();
                    //printerManager.prnBytes(PrinterConstants.ESC_ALIGN_CENTER);
                    printerManager.prnBitmap(BitmapFactory.decodeResource(getResources(), index));
                    // printerManager.prnBytes(PrinterConstants.ESC_ALIGN_LEFT);
                    printerManager.prnStart();
                    printerManager.prnStartCut(1);
                    //printerManager.prnClose();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // .start();
    }


}
