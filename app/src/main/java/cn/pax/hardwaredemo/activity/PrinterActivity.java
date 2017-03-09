package cn.pax.hardwaredemo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pax.api.PrintException;
import com.pax.api.PrintManager;
import com.pax.api.ThreadPoolManager;

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
    TextView mTvPrinterStatus;//打印机状态
    RelativeLayout mBtnPrinterSettingSpeed;//打印机设置
    RelativeLayout mBtnPrinterSettingGray;

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
        mBtnPrinterSettingSpeed = (RelativeLayout) findViewById(R.id.m_btn_printer_setting_speed);
        iv_printer_back = (ImageView) findViewById(R.id.iv_printer_back);
        mTvPrinterStatus = (TextView) findViewById(R.id.m_tv_printer_status);
        mBtnPrinterSettingGray = (RelativeLayout) findViewById(R.id.m_btn_printer_setting_gray);
        m_rl_back = (RelativeLayout) findViewById(R.id.m_rl_back);

    }

    @Override
    protected void initEvent() {
        btn_printer_bar_code.setOnClickListener(this);
        btn_printer_qr_code.setOnClickListener(this);
        btn_printer_black_square.setOnClickListener(this);
        mBtnPrinterSettingSpeed.setOnClickListener(this);
        mBtnPrinterSettingGray.setOnClickListener(this);

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
        super.onResume();
        try {
            PrintManager.getInstance(getApplicationContext()).prnInit();
        } catch (PrintException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != printerManager) {
            try {
                printerManager.prnClose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_printer_bar_code:
                Log.d(TAG, "打印条码");
                //index = R.mipmap.bar_code_2;
                printBarCode();
                break;

            case R.id.btn_printer_qr_code:
                Log.d(TAG, "打印二维码 ");
                // index = R.mipmap.pax_logo;
                printQrCode();
                break;

            case R.id.btn_printer_black_square:
                Log.d(TAG, "打印黑色块");
                index = R.mipmap.black_sp;
                printBitmap(index);
                break;

            case R.id.m_btn_printer_setting_speed:
                Log.d(TAG, "打印机速度");
                setPrinterSpeed();
                //弹出一个popwindow
                break;

            case R.id.m_btn_printer_setting_gray:
                Log.d(TAG, "打印机浓度");
                selectGray();
                //弹出一个popwindow
                break;

        }

        //printBitmap(index);
        //prnBitmap(index);

    }

    /**
     * 选择打印机浓度
     */
    private void selectGray() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PrinterActivity.this, R.style.AlertDialog);
        builder.setIcon(R.mipmap.btn_print);
        builder.setTitle(getResources().getString(R.string.Select_Printer_Gray));
        //    指定下拉列表的显示数据
        final String[] grays = new String[40];
        for (int i = 0; i < grays.length; i++) {
            grays[i] = i + "";
        }
        //设置一个下拉的列表选择项
        builder.setItems(grays, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToastUtil.showToast(getResources().getString(R.string.Select_Gray) + grays[which]);
                try {
                    PrintManager.getInstance(getApplicationContext()).prnSetGray(Integer.parseInt(grays[which]));
                    PrintManager.getInstance(getApplication()).prnBytes(com.pax.api.util.PrinterConstants.PRINT_PRINTER_GRAY_LEVEL);
                    PrintManager.getInstance(getApplicationContext()).prnStep(100);
                } catch (PrintException e) {
                    e.printStackTrace();
                }

            }
        });
        builder.show();

    }

    /**
     * 设置打印机速度
     */
    private void setPrinterSpeed() {
        //设置自定义样式
        AlertDialog.Builder builder = new AlertDialog.Builder(PrinterActivity.this, R.style.AlertDialog);
        builder.setIcon(R.mipmap.btn_print);
        builder.setTitle(getResources().getString(R.string.Select_Printer_Speed));
        //    指定下拉列表的显示数据
        final String[] var = {"50", "60", "70", "80", "90", "100", "120", "150"};
        builder.setItems(var, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(UpdateActivity.this, "选择的升级文件为：" + cities[which], Toast.LENGTH_SHORT).show();
                ToastUtil.showToast(getResources().getString(R.string.Select_Speed) + var[which]);
                try {
                    PrintManager.getInstance(getApplicationContext()).prnPrintSpeed(Integer.parseInt(var[which]));
                    PrintManager.getInstance(getApplication()).prnBytes(com.pax.api.util.PrinterConstants.PRINT_PRINTER_SPEED);
                    PrintManager.getInstance(getApplicationContext()).prnStep(100);
                } catch (PrintException e) {
                    e.printStackTrace();
                }

            }
        });
        builder.show();
    }

    private void printQrCode() {
        mThreadPoolManager.execute(new Thread() {
            @Override
            public void run() {
                try {
                    printerManager = PrintManager.getInstance(getApplicationContext());
                    printerManager.prnInit();
                    printerManager.prnBytes(PrinterConstants.ESC_ALIGN_CENTER);
                    printerManager.prnQrCode("http://www.pax.com.cn/");
                    printerManager.prnBytes(PrinterConstants.ESC_ALIGN_LEFT);
                    printerManager.prnStartCut(1);
                    printerManager.prnStart();
                    printerManager.prnClose();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void printBarCode() {
        mThreadPoolManager.execute(new Thread() {
            @Override
            public void run() {
                try {
                    printerManager = PrintManager.getInstance(getApplicationContext());
                    printerManager.prnInit();
                    printerManager.prnBytes(PrinterConstants.ESC_ALIGN_CENTER);
                    printerManager.prnBarCode("1234567890123");
                    printerManager.prnBytes(PrinterConstants.ESC_ALIGN_LEFT);
                    printerManager.prnStartCut(1);
                    printerManager.prnStart();
                    printerManager.prnClose();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
                    printerManager = PrintManager.getInstance(getApplicationContext());
                    printerManager.prnInit();
                    printerManager.prnBytes(PrinterConstants.ESC_ALIGN_CENTER);
                    printerManager.prnBitmap(BitmapFactory.decodeResource(getResources(), index));
                    printerManager.prnBytes(PrinterConstants.ESC_ALIGN_LEFT);
                    printerManager.prnStartCut(1);
                    printerManager.prnStart();
                    printerManager.prnClose();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
