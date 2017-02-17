package cn.pax.hardwaredemo.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.base.BaseActivity;
import cn.pax.hardwaredemo.tool.PrintThread;
import cn.pax.hardwaredemo.util.PrinterUtil;
import cn.pax.hardwaredemo.util.ToastUtil;


public class ScannerActivity extends BaseActivity {

    private static final String TAG = "ScannerActivity";

    EditText et_scanner_code;// 扫描输入的条码
    ImageView iv_scanner_ok;//显示扫码成功
    Button btn_scanner_bar_code;//条码打印
    ImageView iv_scanner_back;//返回健
    RelativeLayout m_rl_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_scanner);
    }

    @Override
    protected void findView() {

        et_scanner_code = (EditText) findViewById(R.id.et_scanner_code);
        et_scanner_code.setInputType(InputType.TYPE_NULL);
        iv_scanner_ok = (ImageView) findViewById(R.id.iv_scanner_ok);
        btn_scanner_bar_code = (Button) findViewById(R.id.btn_scanner_bar_code);
        iv_scanner_back = (ImageView) findViewById(R.id.iv_scanner_back);
        m_rl_back = (RelativeLayout) findViewById(R.id.m_rl_back);

    }

    @Override
    protected void initEvent() {

        et_scanner_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "编辑框文本变化 " + s.toString());
                iv_scanner_ok.setVisibility(View.VISIBLE);
            }
        });

        btn_scanner_bar_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PrinterUtil.getInstance(ScannerActivity.this).getUsbStatus())
                    new PrintThread(ScannerActivity.this, R.mipmap.bar_code_2).run();
                else {
                    ToastUtil.showToast("请检查打印机状态!");
                    PrinterUtil.getInstance(ScannerActivity.this).openUsb();
                }
            }
        });


        iv_scanner_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
