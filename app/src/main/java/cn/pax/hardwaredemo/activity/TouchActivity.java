package cn.pax.hardwaredemo.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.base.BaseActivity;
import cn.pax.hardwaredemo.ui.MultiTouchView;


/**
 * 触摸测试
 */

public class TouchActivity extends BaseActivity {

    private static final String TAG = "TouchActivity";
    ImageView iv_touch_back;//触摸返回

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置成全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState, R.layout.activity_touch);

        // 设置为上面的MTView
        //setContentView(new MultiTouchView(this));

    }

    @Override
    protected void findView() {
        iv_touch_back = (ImageView) findViewById(R.id.iv_touch_back);

    }

    @Override
    protected void initEvent() {

        iv_touch_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void init() {

        //startApk();


    }


    /**
     * 启动其他app
     */
    private void startApk() {
        try {
            ComponentName componetName = new ComponentName("com.itxinke.multitouch", "com.itxinke.multitouch.Multitouch");
            Intent intent = new Intent();
            intent.setComponent(componetName);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "没有该app存在");
        }
    }
}
