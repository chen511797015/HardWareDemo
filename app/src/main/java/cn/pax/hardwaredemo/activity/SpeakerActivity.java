package cn.pax.hardwaredemo.activity;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.base.BaseActivity;


/**
 * 声音测试界面
 */

public class SpeakerActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SpeakerActivity";

    ImageView iv_speaker_back;//返回
    Button btn_speaker_play;//播放,暂停
    Button btn_speaker_stop;//停止
    TextView tv_speaker_volume;//音量大小
    SeekBar sb_speaker_volume;//音量进度条
    MediaPlayer mPlayer;//多媒体播放器
    boolean isPlaying = false;//是否正在播放

    AudioManager mAudioManager;

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

        //返回
        iv_speaker_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_speaker_play.setOnClickListener(this);
        btn_speaker_stop.setOnClickListener(this);

        //拖动条变化监听
        seekBarSetOnChangeListener();


    }

    private void seekBarSetOnChangeListener() {

        sb_speaker_volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //直接设置音量大小
                    int index = progress / 10;
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
                    tv_speaker_volume.setText(index + "");
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

    @Override
    protected void init() {

        initPlay();

        initAudioManager();


    }

    /**
     * 初始化媒体音量管理
     */
    private void initAudioManager() {
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mAudioManager.setMode(AudioManager.STREAM_MUSIC);

        int mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int mCurrentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        sb_speaker_volume.setMax(mMaxVolume * 10);
        sb_speaker_volume.setProgress(mCurrentVolume * 10);
        tv_speaker_volume.setText(mCurrentVolume + "");

        Log.e(TAG, "当前音量: " + mCurrentVolume);
        Log.e(TAG, "最大音量: " + mMaxVolume);
    }

    private void initPlay() {

        try {
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            AssetFileDescriptor mFileDescriptor = null;
            mFileDescriptor = getAssets().openFd("horn_birthday.mp3");//horn_birthday.mp3    play.mp3
            mPlayer.setDataSource(mFileDescriptor.getFileDescriptor(), mFileDescriptor.getStartOffset(), mFileDescriptor.getLength());
            mPlayer.setLooping(true);
            mPlayer.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "音频解码错误!");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_speaker_play:
                startOrPausePlay();
                break;
            case R.id.btn_speaker_stop:
                stopPlay();
                break;
        }

    }

    /**
     * 开始或者暂停播放
     */
    private void startOrPausePlay() {

        if (isPlaying) {
            isPlaying = false;
            btn_speaker_play.setBackgroundResource(R.mipmap.pause);
            mPlayer.start();

        } else {
            isPlaying = true;
            btn_speaker_play.setBackgroundResource(R.mipmap.play);
            if (mPlayer != null && mPlayer.isPlaying()) {
                mPlayer.pause();
            }
        }
    }

    /**
     * 停止播放
     */
    private void stopPlay() {
        btn_speaker_play.setBackgroundResource(R.mipmap.play);
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
            mPlayer.prepareAsync();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //设置按键操作时,音量的变化
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN && mAudioManager != null) {
            int mCurrentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            sb_speaker_volume.setProgress(mCurrentVolume * 10);
            tv_speaker_volume.setText(mCurrentVolume + "");
        }
        return super.onKeyDown(keyCode, event);
    }
}
