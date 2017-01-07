package cn.pax.hardwaredemo.activity;

import android.app.Presentation;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import cn.pax.hardwaredemo.util.PhoneUtil;

public class ScreenActivity extends BaseActivity implements View.OnClickListener {

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
    ViceScreen mViceScreen;


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
        sb_screen_brightness.setMax(255);


    }

    @Override
    protected void initEvent() {

        iv_screen_back.setOnClickListener(this);
        rb_screen_same.setOnClickListener(this);
        rb_screen_different.setOnClickListener(this);
        btn_screen_test.setOnClickListener(this);

        setOnSeekBarChangedListener();


    }


    @Override
    protected void init() {

        //初始化副屏信息
        initDisplay();

        initView();

    }


    private void setOnSeekBarChangedListener() {
        sb_screen_brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {//是否是用户在调节
                    tv_screen_brightness.setText(progress + "");
                    changeAppBrightness(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * 改变亮度
     *
     * @param progress
     */
    private void changeAppBrightness(int progress) {
        //通过Window对象来获取当前窗口
        Window mWindow = this.getWindow();
        WindowManager.LayoutParams mWindowAttributes = mWindow.getAttributes();
        if (progress == -1)
            mWindowAttributes.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        else
            mWindowAttributes.screenBrightness = (progress <= 0 ? 1 : progress) / 255f;

        //设置当前亮度
        mWindow.setAttributes(mWindowAttributes);
    }


    /**
     * 初始化数据信息
     */
    private void initView() {
        int mBrightness = getSystemBrightness();
        tv_screen_brightness.setText(mBrightness + "");
        sb_screen_brightness.setProgress(mBrightness);
    }

    /**
     * 获取屏幕亮度
     */
    private int getSystemBrightness() {
        int mSystemBrightness = 0;
        try {
            mSystemBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return mSystemBrightness;
    }

    private void initDisplay() {
        DisplayManager mDisplayManager = (DisplayManager) getSystemService(DISPLAY_SERVICE);
        Display[] mDisplays = mDisplayManager.getDisplays();
        Log.e(TAG, "当前屏幕个数: " + mDisplays.length);
        //0--主屏        1--副屏
        mViceScreen = new ViceScreen(this, mDisplays[1]);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_screen_back:
                Log.e(TAG, "返回 ");
                onBackPressed();
                break;

            case R.id.rb_screen_same:
                Log.e(TAG, "同显");
                if (mViceScreen != null)
                    mViceScreen.dismiss();
                break;

            case R.id.rb_screen_different:
                Log.e(TAG, "异显");
                if (mViceScreen != null)
                    mViceScreen.show();
                break;
            case R.id.btn_screen_test:
                Log.e(TAG, "颜色测试");
                startActivity(new Intent(ScreenActivity.this, ScreenTestActivity.class));
                break;
        }
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
