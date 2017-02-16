package cn.pax.hardwaredemo.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.base.BaseActivity;


/**
 * wifi设置界面
 */

public class RouterActivity extends BaseActivity {

    private static final String TAG = "RouterActivity";

    @BindView(R.id.iv_router_back)
    ImageView ivRouterBack;
    @BindView(R.id.wv_router_test)
    WebView wvRouterTest;
    WebSettings mWebSettings;
    @BindView(R.id.ll_router_show_anim)
    LinearLayout mShowAnim;
    @BindView(R.id.activity_router)
    LinearLayout activityRouter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_router);
    }

    @Override
    protected void findView() {
        ButterKnife.bind(this);

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {


        initWebView();


    }

    private void initWebView() {
        // final String mUrlString = "https://www.hao123.com/";
        final String mUrlString = "http://10.10.10.252/";
        wvRouterTest.loadUrl(mUrlString);
        mWebSettings = wvRouterTest.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        wvRouterTest.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(mUrlString);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mShowAnim.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mShowAnim.setVisibility(View.GONE);
            }
        });

        wvRouterTest.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //网页加载进度...
            }
        });


    }


    @OnClick(R.id.iv_router_back)
    public void onBack() {
        finish();
    }


    @Override
    public void onBackPressed() {
        if (wvRouterTest != null && wvRouterTest.canGoBack()) {
            wvRouterTest.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (wvRouterTest != null) {
            wvRouterTest.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wvRouterTest != null) {
            wvRouterTest.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wvRouterTest != null) {
            wvRouterTest.destroy();
        }
    }
}
