package cn.com.megait.commonlib.widget.adbrowser;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * @author lenovo
 * @function
 * @date 2018/11/28
 * TODO 了解WebView相关设置
 */
public class BrowserWebView extends WebView {


    public BrowserWebView(Context context) {
        super(context);
        init();
    }

    private void init() {
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        //webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        setInitialScale(1);


    }
}
