package cn.pax.hardwaredemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.base.BaseActivity;


/**
 * 本机信息
 */

public class DeviceActivity extends BaseActivity {

    private static final String TAG = "DeviceActivity";

    @BindView(R.id.iv_device_back)
    ImageView ivDeviceBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_device);

    }

    @Override
    protected void findView() {
        ButterKnife.bind(this);//绑定activity

    }

    @Override
    protected void initEvent() {

        ivDeviceBack.setOnClickListener(new View.OnClickListener() {
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
