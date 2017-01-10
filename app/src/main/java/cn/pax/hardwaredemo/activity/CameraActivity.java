package cn.pax.hardwaredemo.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.base.BaseActivity;


/**
 * 摄像头界面
 */

public class CameraActivity extends BaseActivity {

    private static final String TAG = "CameraActivity";

    ImageView iv_camera_back;//返回
    @BindView(R.id.iv_camera_back)
    ImageView ivCameraBack;

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


    @OnClick(R.id.iv_camera_back)
    public void back() {
        Log.d(TAG, "back: ");
        onBackPressed();
    }


    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

    }
}
