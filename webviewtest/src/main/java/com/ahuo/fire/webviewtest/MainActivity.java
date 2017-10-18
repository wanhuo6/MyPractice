package com.ahuo.fire.webviewtest;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private WebView mWebView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private WebSettings mWebSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initWebView();
        initData();

    }


    private void initView() {
        mWebView = (WebView) findViewById(R.id.webView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

    }


    public String getVersionName(Context context) {
        try {
            PackageInfo manager = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return manager.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "Unknown";
        }
    }

    public String getUser_Agent(Context context) {
        StringBuffer sb = new StringBuffer();
        sb.append("KKUSER-")
                .append(getVersionName(context))
                .append("/Android/")
                .append(Build.VERSION.RELEASE)
                .append("/")
                .append(Build.BRAND)
                .append("/")
                .append(Build.MODEL);
//                .append("/")
//                .append(Build.MANUFACTURER);
        return sb.toString();
    }


    private void initWebView() {
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        //mWebView.addJavascriptInterface(new JsObject(), "Android");
     /*   String defaultUA = mWebSettings.getUserAgentString();
        mWebSettings.setUserAgentString(defaultUA + " " + getUser_Agent(this));
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);*/
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mSwipeRefreshLayout.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mWebView.setWebChromeClient(mWebChromeClient);
        mWebView.setWebViewClient(new MyWebViewClient());
    }

    private void initData() {
        String url = "https://ts.kuaikuaikeji.com/kas/report3/move3.jsp?i=3583db5b-b851-46fd-8c00-395e87cd02a5";
        //String url="https://www.zhihu.com/question/34652814";
        //String url="https://www.baidu.com";
        // String url ="https://s.kuaikuaikeji.com/kas/";
        mWebView.loadUrl(url);
    }

    @Override
    public void onRefresh() {
        mWebView.loadUrl(mWebView.getUrl());
    }

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress > 90) {
                mSwipeRefreshLayout.setRefreshing(false);
            } else {
                if (!mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);

        }

    };

    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url2) {
            view.loadUrl(url2);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            KLog.i("---setTitleConfig");
//            mToolbar.setTitleConfig(buildDefaultConfig(mTitle));
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            //mTvError.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, "失败" + errorCode + "描述" + description, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //super.onReceivedSslError(view, handler, error);//注意:这句一定要注释掉
            handler.proceed();//接受证书
        }

    }
}
