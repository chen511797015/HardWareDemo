package cn.pax.hardwaredemo.activity;

import android.os.Bundle;
import android.view.Window;
import android.widget.GridView;
import android.widget.ListAdapter;

import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.adapter.BtnListAdapter;
import cn.pax.hardwaredemo.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    GridView gv_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState, R.layout.activity_main);
    }


    @Override
    protected void findView() {
        gv_main = (GridView) findViewById(R.id.gv_main);

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

        //初始化GridView数据
        BtnListAdapter mAdapter = new BtnListAdapter(this);
        gv_main.setAdapter(mAdapter);


    }
}
