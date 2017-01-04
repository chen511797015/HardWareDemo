package cn.pax.hardwaredemo.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by chendd on 2017/1/4.
 */

public abstract class BaseActivity extends Activity {


    protected void onCreate(Bundle savedInstanceState, int mLayoutID) {
        super.onCreate(savedInstanceState);
        setContentView(mLayoutID);

        findView();

        initEvent();

        init();


    }

    protected abstract void findView();

    protected abstract void initEvent();

    protected abstract void init();


}
