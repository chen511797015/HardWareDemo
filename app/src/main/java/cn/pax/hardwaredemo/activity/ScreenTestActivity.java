package cn.pax.hardwaredemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;

import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.base.BaseActivity;

/**
 * Created by chendd on 2017/1/7.
 * 颜色测试界面
 */

public class ScreenTestActivity extends BaseActivity {

    private static final String TAG = "ScreenTestActivity";


    View activity_screen_test;//背景颜色变化
    SeekBar sb_screen_test;//亮度进度条


    int mColor[] = {
            Color.parseColor("#ffffff"),//白色
            Color.parseColor("#000000"),//黑色
            Color.parseColor("#ff0000"),//红色
            Color.parseColor("#008000"),//绿色
            Color.parseColor("#0000ff")//蓝色
    };

    int index = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_screen_test);
    }

    @Override
    protected void findView() {
        activity_screen_test = findViewById(R.id.activity_screen_test);
        sb_screen_test = (SeekBar) findViewById(R.id.sb_screen_test);
        sb_screen_test.setMax(255);

    }

    @Override
    protected void initEvent() {

        //切换颜色点击监听
        activity_screen_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "点击了切换颜色:" + mColor.length + "=====" + index);
                if (index == mColor.length) {
                    finish();
                    return;
                }
                activity_screen_test.setBackgroundColor(mColor[index]);
                index++;
            }
        });


        //进度条拖动监听
        sb_screen_test.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    changeAppBrightness(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    protected void init() {
        sb_screen_test.setProgress(getSystemBrightness());
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
}
