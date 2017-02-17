package cn.pax.hardwaredemo.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.base.BaseActivity;
import cn.pax.hardwaredemo.util.PrinterConstants;
import cn.pax.hardwaredemo.util.PrinterUtil;
import cn.pax.hardwaredemo.util.UsbAdmin;


/**
 * 钱箱测试
 */

public class CashBoxActivity extends BaseActivity {

    private static final String TAG = "CashBoxActivity";

    Button btn_cash_box_open;// 打开钱箱
    ImageView iv_cash_box_back;//返回
    RelativeLayout m_rl_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_cash_box);
    }

    @Override
    protected void findView() {

        btn_cash_box_open = (Button) findViewById(R.id.btn_cash_box_open);
        iv_cash_box_back = (ImageView) findViewById(R.id.iv_cash_box_back);
        m_rl_back = (RelativeLayout) findViewById(R.id.m_rl_back);

    }

    @Override
    protected void initEvent() {
        btn_cash_box_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开钱箱
                new Thread() {
                    @Override
                    public void run() {
                        PrinterUtil.writeData(CashBoxActivity.this, PrinterConstants.OpenCashBox);
                    }
                }.start();
            }
        });

        iv_cash_box_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
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

    }

}
