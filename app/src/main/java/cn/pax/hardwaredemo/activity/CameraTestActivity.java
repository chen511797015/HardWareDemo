package cn.pax.hardwaredemo.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.journeyapps.barcodescanner.CameraPreview;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.ViewfinderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.base.BaseAppCompatActivity;


/**
 * 用于测试二维码扫描
 */

public class CameraTestActivity extends BaseAppCompatActivity {

    private static final String TAG = "CameraTestActivity";
    @BindView(R.id.iv_camera_test_back)
    ImageView ivCameraTestBack;
    @BindView(R.id.dbv_camera_test)
    DecoratedBarcodeView dbvCameraTest;
    @BindView(R.id.btn_camera_test_close)
    Button btnCameraTestClose;


    CaptureManager mCaptureManager;//捕捉管理器
    @BindView(R.id.m_rl_back)
    LinearLayout mRlBack;


    @Override
    protected int setView() {
        return R.layout.activity_camera_test;
    }

    @Override
    protected void findView() {
        ButterKnife.bind(this);

    }

    @Override
    protected void initView() {

        dbvCameraTest.setStatusText(getResources().getString(R.string.Please_put_the_QR_code_into_the_viewfinder));
        ViewfinderView viewFinder = dbvCameraTest.getViewFinder();
        viewFinder.setCameraPreview(new CameraPreview(this));
        //barcodeView.setVisibility(View.GONE);

        //初始化捕获
        mCaptureManager = new CaptureManager(this, dbvCameraTest);
        mCaptureManager.setCameraPermissionReqCode(10010);
        Bundle mBundle = new Bundle();
        mCaptureManager.initializeFromIntent(getIntent(), mBundle);
        mCaptureManager.decode();

    }


    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

    }


    @OnClick({R.id.iv_camera_test_back, R.id.btn_camera_test_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_camera_test_back:
                finish();
                break;
            case R.id.btn_camera_test_close:
                finish();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCaptureManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCaptureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCaptureManager.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mCaptureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnClick(R.id.m_rl_back)
    public void onClick() {
        onBackPressed();
    }
}
