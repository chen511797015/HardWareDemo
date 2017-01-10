package cn.pax.hardwaredemo.activity;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

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
    private WebSettings mWebSettings;

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
        final String mUrlString = "https://www.hao123.com/";
        wvRouterTest.loadUrl(mUrlString);
        mWebSettings = wvRouterTest.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        wvRouterTest.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(mUrlString);
                return super.shouldOverrideUrlLoading(view, request);
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
}
