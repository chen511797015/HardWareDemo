package cn.pax.hardwaredemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by chendd on 2017/1/11.
 */

public abstract class BaseAppCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setView());

        findView();

        initView();

        initEvent();

        init();

    }

    protected abstract int setView();

    protected abstract void findView();

    protected abstract void initView();

    protected abstract void initEvent();

    protected abstract void init();
}
