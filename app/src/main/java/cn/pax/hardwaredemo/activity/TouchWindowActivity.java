package cn.pax.hardwaredemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.base.BaseActivity;

/**
 * Created by chendd on 2017/3/6.
 */

public class TouchWindowActivity extends BaseActivity {

    private static final String TAG = "TouchWindowActivity";
    @BindView(R.id.m_rl_back)
    RelativeLayout mRlBack;
    @BindView(R.id.m_btn_multi_touch)
    Button mBtnMultiTouch;
    @BindView(R.id.m_btn_signature_test)
    Button mBtnSignatureTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_touch_window);

    }

    @Override
    protected void findView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.m_btn_multi_touch, R.id.m_btn_signature_test, R.id.m_rl_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.m_rl_back:
                onBackPressed();
                break;
            case R.id.m_btn_multi_touch:
                Log.d(TAG, "*********多点触控********");
                startMultiTouch();
                break;
            case R.id.m_btn_signature_test:
                Log.d(TAG, "*********签名测试********");
                startSignatureTest();
                break;
        }
    }

    private void startSignatureTest() {
        startActivity(new Intent(this, SignatureActivity.class));
    }

    private void startMultiTouch() {
        startActivity(new Intent(this, TouchActivity.class));
    }
}
