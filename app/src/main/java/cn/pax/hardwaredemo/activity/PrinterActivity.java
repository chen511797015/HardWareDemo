package cn.pax.hardwaredemo.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.base.BaseActivity;
import cn.pax.hardwaredemo.tool.PrintThread;
import cn.pax.hardwaredemo.util.PrinterUtil;
import cn.pax.hardwaredemo.util.ToastUtil;
import cn.pax.hardwaredemo.util.UsbAdmin;

import static cn.pax.hardwaredemo.R.mipmap.pax_logo;


/**
 * 打印机测试
 */

public class PrinterActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "PrinterActivity";

    Button btn_printer_bar_code;//打印条码
    Button btn_printer_qr_code;//打印二维码
    Button btn_printer_black_square;//打印黑色块
    ImageView iv_printer_back;//返回

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
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {

        //首先判断一下打印机状态,没有连接,先申请连接
        if (!PrinterUtil.getInstance(PrinterActivity.this).getUsbStatus()) {
            PrinterUtil.getInstance(this).openUsb();
        }
        switch (v.getId()) {
            case R.id.btn_printer_bar_code:
                Log.e(TAG, "打印条码");
                if (PrinterUtil.getInstance(PrinterActivity.this).getUsbStatus())
                    new PrintThread(PrinterActivity.this, R.mipmap.bar_code_2).run();
                else {
                    ToastUtil.showToast("请检查打印机状态!");
                }
                break;

            case R.id.btn_printer_qr_code:
                Log.e(TAG, "打印二维码 ");
                if (PrinterUtil.getInstance(PrinterActivity.this).getUsbStatus())
                    new PrintThread(PrinterActivity.this, R.mipmap.pax_logo).run();
                else
                    ToastUtil.showToast("请检查打印机状态!");
                break;

            case R.id.btn_printer_black_square:
                Log.e(TAG, "打印黑色块");
                if (PrinterUtil.getInstance(PrinterActivity.this).getUsbStatus())
                    new PrintThread(PrinterActivity.this, R.mipmap.black_sp).run();
                else
                    ToastUtil.showToast("请检查打印机状态!");
                break;
        }
    }
}
