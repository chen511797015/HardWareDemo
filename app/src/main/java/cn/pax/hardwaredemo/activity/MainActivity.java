package cn.pax.hardwaredemo.activity;

import android.os.Bundle;
import android.view.Window;
import android.widget.GridView;

import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState, R.layout.activity_main);


    }

    @Override
    protected void findView() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

        //初始化GridView数据


    }
}
