package cn.pax.hardwaredemo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.base.BaseActivity;
import cn.pax.hardwaredemo.tool.PrintThread;
import cn.pax.hardwaredemo.util.PrinterConstants;
import cn.pax.hardwaredemo.util.PrinterUtil;
import cn.pax.hardwaredemo.util.ToastUtil;


/**
 * 摄像头界面
 */

public class CameraActivity extends BaseActivity {

    private static final String TAG = "CameraActivity";

    @BindView(R.id.iv_camera_back)
    ImageView ivCameraBack;
    @BindView(R.id.btn_camera_test)
    Button btn_camera_test;
    @BindView(R.id.iv_camera_show_qr)
    ImageView ivCameraShowQr;
    @BindView(R.id.btn_camera_print)
    Button btnCameraPrint;
    @BindView(R.id.tv_camera_show)
    TextView tv_camera_show;

    boolean isBitmapChanged = false;//是否打印扫描的二维码  false--不打印  true--打印
    @BindView(R.id.m_rl_back)
    RelativeLayout mRlBack;
    @BindView(R.id.activity_camera)
    RelativeLayout activityCamera;
    private PrintQrCodeThread mThread;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "初始化Camera界面...");
        super.onCreate(savedInstanceState, R.layout.activity_camera);
    }

    @Override
    protected void findView() {
        //绑定ButterKnife
        ButterKnife.bind(this);

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {


    }


    /**
     * 返回
     */
    @OnClick(R.id.iv_camera_back)
    public void back() {
        onBackPressed();
    }


    /**
     * 开始扫描
     */
    @OnClick(R.id.btn_camera_test)
    public void customScan() {
        Log.d(TAG, "启动二维码扫描...");
        new IntentIntegrator(this)
                .setOrientationLocked(false)
                .setCaptureActivity(CameraTestActivity.class)
                .initiateScan();//初始化扫描

    }


    /**
     * 获取返回的扫描值
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult mIntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (mIntentResult != null) {
            Log.e(TAG, "onActivityResult:... ");
            if (mIntentResult.getContents() == null) {
                ToastUtil.showToast(getResources().getString(R.string.Content_is_empty));
                ivCameraShowQr.setImageResource(R.mipmap.camera_pic);
                ivCameraShowQr.setVisibility(View.VISIBLE);
                tv_camera_show.setVisibility(View.GONE);
            } else {
                //扫码返回值
                String mScanResult = mIntentResult.getContents();

                ivCameraShowQr.setVisibility(View.GONE);
                tv_camera_show.setText(mScanResult);
                tv_camera_show.setVisibility(View.VISIBLE);

                //创建二维码
                //bitmap = QrCodeUtil.getInstance().createBitmap(mScanResult, 250, 250);
                //isBitmapChanged = true;
                //mThread = new PrintQrCodeThread(bitmap);
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


    @OnClick(R.id.btn_camera_print)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_camera_print:

                new PrintThread(this, R.mipmap.pax_logo).run();
                break;
        }
    }

    @OnClick(R.id.m_rl_back)
    public void onClick() {
        onBackPressed();
    }

    class PrintQrCodeThread extends Thread {

        Bitmap mBitmap;

        public PrintQrCodeThread(Bitmap mBitmap) {
            this.mBitmap = mBitmap;
        }

        @Override
        public void run() {

            try {
                ByteArrayOutputStream mBaos = new ByteArrayOutputStream();
                mBaos.write(PrinterConstants.ESC_ALIGN_CENTER);
                mBaos.write(PrinterUtil.decodeBitmap(mBitmap));
                PrinterUtil.writeData(CameraActivity.this, mBaos.toByteArray());
                PrinterUtil.writeData(CameraActivity.this, PrinterConstants.FullCut);
                //PrinterUtil.writeData(mContext, new byte[]{0x1b, 0x23, 0x23, 0x43, 0x54, 0x46, 0x44, (byte) 0x12c});//切刀前走纸距离,无效
                //PrinterUtil.writeData(mContext, new byte[]{0x1d, 0x56, 0x60});
                //PrinterUtil.writeData(mContext, new byte[]{0x1b, 0x23, 0x23, 0x41, 0x43, 0x46, 0x44, 0x00});打印切纸后走纸

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "打印异常");
            }
        }
    }

}
