package cn.pax.hardwaredemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.base.BaseActivity;


/**
 * 声音测试界面
 */

public class SpeakerActivity extends BaseActivity {

    private static final String TAG = "SpeakerActivity";

    ImageView iv_speaker_back;//返回
    Button btn_speaker_play;//播放,暂停
    Button btn_speaker_stop;//停止
    TextView tv_speaker_volume;//音量大小
    SeekBar sb_speaker_volume;//音量进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_speaker);
    }

    @Override
    protected void findView() {
        iv_speaker_back = (ImageView) findViewById(R.id.iv_speaker_back);
        btn_speaker_play = (Button) findViewById(R.id.btn_speaker_play);
        btn_speaker_stop = (Button) findViewById(R.id.btn_speaker_stop);

        tv_speaker_volume = (TextView) findViewById(R.id.tv_speaker_volume);
        sb_speaker_volume = (SeekBar) findViewById(R.id.sb_speaker_volume);

    }

    @Override
    protected void initEvent() {

        iv_speaker_back.setOnClickListener(new View.OnClickListener() {
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
