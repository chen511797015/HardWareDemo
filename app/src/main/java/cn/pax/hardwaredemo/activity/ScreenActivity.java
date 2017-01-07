package cn.pax.hardwaredemo.activity;

import android.app.Presentation;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import javax.security.auth.login.LoginException;

import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.base.BaseActivity;

public class ScreenActivity extends BaseActivity {

    private static final String TAG = "ScreenActivity";

    //-----------------Left----------------
    ImageView iv_screen_back;//返回键
    TextView tv_screen_resolution;//分辨率
    RadioGroup rg_screen_show;
    RadioButton rb_screen_same;//同显
    RadioButton rb_screen_different;//异显


    //----------------Right-----------------
    Button btn_screen_test;// Test按钮
    TextView tv_screen_brightness;//亮度
    ToggleButton tb_screen_automatic;//亮度自动调节按钮
    SeekBar sb_screen_brightness;//亮度调节进度条
    private ViceScreen mViceScreen;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_screen);
    }

    @Override
    protected void findView() {
        iv_screen_back = (ImageView) findViewById(R.id.iv_screen_back);
        tv_screen_resolution = (TextView) findViewById(R.id.tv_screen_resolution);
        tv_screen_brightness = (TextView) findViewById(R.id.tv_screen_brightness);

        rg_screen_show = (RadioGroup) findViewById(R.id.rg_screen_show);
        rb_screen_same = (RadioButton) findViewById(R.id.rb_screen_same);
        rb_screen_same.setChecked(true);
        rb_screen_different = (RadioButton) findViewById(R.id.rb_screen_different);

        btn_screen_test = (Button) findViewById(R.id.btn_screen_test);
        tb_screen_automatic = (ToggleButton) findViewById(R.id.tb_screen_automatic);

        sb_screen_brightness = (SeekBar) findViewById(R.id.sb_screen_brightness);


    }

    @Override
    protected void initEvent() {

        //返回
        setOnIvBackClick();

        //同显异显显示
        setOnRgClick();


    }


    @Override
    protected void init() {

        try {
            initDisplay();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initDisplay() throws Exception {

        DisplayManager mDisplayManager = (DisplayManager) getSystemService(DISPLAY_SERVICE);
        Display[] mDisplays = mDisplayManager.getDisplays();
        Log.e(TAG, "当前屏幕个数: " + mDisplays.length);
        //0--主屏        1--副屏
        mViceScreen = new ViceScreen(this, mDisplays[1]);
        //mViceScreen.show();
    }

    /**
     * 同显,异显
     */
    private void setOnRgClick() {
        rb_screen_same.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "同显");
                if (mViceScreen != null)
                    mViceScreen.dismiss();
            }
        });

        rb_screen_different.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "异显");
                if (mViceScreen != null)
                    mViceScreen.show();
            }
        });
    }


    /**
     * 返回点击事件
     */
    private void setOnIvBackClick() {
        iv_screen_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    /**
     * 副屏显示
     */
    class ViceScreen extends Presentation {

        public ViceScreen(Context outerContext, Display display) {
            super(outerContext, display);
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_vice);
        }
    }

}
