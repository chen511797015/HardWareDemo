package cn.pax.hardwaredemo.activity;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.ui.PaintView;
import cn.pax.hardwaredemo.util.DisplayUtil;

public class SignatureActivity extends AppCompatActivity {
    private static final String TAG = "SignatureActivity";

    PaintView mPaintView = null;
    M m = null;

    @BindView(R.id.btn_clear)
    Button btnClear;
    @BindView(R.id.activity_signature)
    RelativeLayout activitySignature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        ButterKnife.bind(this);
        initView();

        initDisplayView();

    }

    /**
     * 初始化客屏
     */
    private void initDisplayView() {

        try {
            Display display = DisplayUtil.getDisplays(this);
            if (null != display) {
                m = new M(this, display);
                m.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != m) {
            m.dismiss();
        }
    }

    private void initView() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        mPaintView = new PaintView(this, dm.widthPixels, dm.heightPixels);
        activitySignature.addView(mPaintView);

    }

    @OnClick(R.id.btn_clear)
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_clear:
                if (null != mPaintView) {
                    mPaintView.clear();
                }
                break;
        }
    }

    static class M extends Presentation {


        Display display;

        public M(Context outerContext, Display display) {
            super(outerContext, display);
            this.display = display;
        }

        public M(Context outerContext, Display display, int theme) {
            super(outerContext, display, theme);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signature);
            Button btn = (Button) findViewById(R.id.btn_clear);
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_signature);

            DisplayMetrics dm = new DisplayMetrics();
            //getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
            display.getMetrics(dm);
            Log.d(TAG, "客屏宽度: " + dm.widthPixels + ",客屏宽度:" + dm.heightPixels);
            final PaintView pv = new PaintView(getContext(), dm.widthPixels, dm.heightPixels);
            rl.addView(pv);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != pv) {
                        pv.clear();
                    }
                }
            });

        }
    }
}
