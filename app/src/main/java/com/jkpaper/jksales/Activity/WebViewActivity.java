package com.jkpaper.jksales.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jkpaper.jksales.R;

public class WebViewActivity extends AppCompatActivity {
    WebView wv1;
    String url;
    ProgressBar mProgress;
    ProgressBar crpv;
    TextView tvProgress, tvLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        url = getIntent().getStringExtra("url_web_view");
        if(getIntent().getStringExtra("label") != null){
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(getIntent().getStringExtra("label"));
        }
        //mProgress = (ProgressBar)findViewById(R.id.progress_web_view);
        tvLoading = (TextView)findViewById(R.id.tv_loading_webview);
        crpv = (ProgressBar) findViewById(R.id.progress_web_view);
        wv1 = (WebView)findViewById(R.id.web_view);
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.setWebViewClient(new MyBrowser());
        wv1.loadUrl(url);
        wv1.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                view.setVisibility(View.GONE);
                crpv.setVisibility(View.VISIBLE);
                tvLoading.setVisibility(View.VISIBLE);
                view.loadUrl("javascript:(function() { " +
                        "var head = document.getElementsByTagName('header')[0];"
                        + "head.parentNode.removeChild(head);" +
                        "})()");
                view.loadUrl("javascript:(function() { " +
                        "var footer = document.getElementsByTagName('footer')[0];"
                        + "footer.parentNode.removeChild(footer);" +
                        "})()");
                view.loadUrl("javascript:(function() { " +
                        "var nav = document.getElementsByTagName('nav')[0];"
                        + "nav.parentNode.removeChild(nav);" +
                        "})()");
                view.loadUrl("javascript:(function() { " +
                        "var set = document.getElementsByClassName('banner');"
                        + "set[0].style.margin = '0px';" +
                        "})()");
                if(progress == 100){
                    while(!injectJavaScript(view)){
                        crpv.setVisibility(View.GONE);
                        tvProgress.setVisibility(View.GONE);
                        tvLoading.setVisibility(View.GONE);
                        view.setVisibility(View.VISIBLE);

                    }
                }

            }
        });
        wv1.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                view.loadUrl("javascript:(function() { " +
                        "var head = document.getElementsByTagName('header')[0];"
                        + "head.parentNode.removeChild(head);" +
                        "})()");
                view.loadUrl("javascript:(function() { " +
                        "var footer = document.getElementsByTagName('footer')[0];"
                        + "footer.parentNode.removeChild(footer);" +
                        "})()");
                view.loadUrl("javascript:(function() { " +
                        "var nav = document.getElementsByTagName('nav')[0];"
                        + "nav.parentNode.removeChild(nav);" +
                        "})()");
                view.loadUrl("javascript:(function() { " +
                        "var set = document.getElementsByClassName('banner');"
                        + "set[0].style.margin = '0px';" +
                        "})()");
                crpv.setVisibility(View.GONE);
                tvLoading.setVisibility(View.GONE);
                view.setVisibility(View.VISIBLE);
            }
        });
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (wv1.canGoBack()) {
                        wv1.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
    private boolean injectJavaScript(WebView view){
        view.loadUrl("javascript:(function() { " +
                "var head = document.getElementsByTagName('header')[0];"
                + "head.parentNode.removeChild(head);" +
                "})()");
        view.loadUrl("javascript:(function() { " +
                "var footer = document.getElementsByTagName('footer')[0];"
                + "footer.parentNode.removeChild(footer);" +
                "})()");
        view.loadUrl("javascript:(function() { " +
                "var nav = document.getElementsByTagName('nav')[0];"
                + "nav.parentNode.removeChild(nav);" +
                "})()");
        view.loadUrl("javascript:(function() { " +
                "var set = document.getElementsByClassName('banner');"
                + "set[0].style.margin = '0px';" +
                "})()");
        return true;
    }
}
