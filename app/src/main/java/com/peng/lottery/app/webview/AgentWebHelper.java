package com.peng.lottery.app.webview;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebHistoryItem;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.peng.lottery.base.SimpleBaseActivity;


/**
 * AgentWeb封装
 * @author Peng
 */
public class AgentWebHelper {
    private AgentWeb mAgentWeb;
    private SimpleBaseActivity mActivity;

    public AgentWebHelper(SimpleBaseActivity activity) {
        this.mActivity = activity;
    }

    public void initWeb(@NonNull ViewGroup viewGroup, String url) {
        mAgentWeb = AgentWeb.with(mActivity)
                .setAgentWebParent(viewGroup, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);
    }

    public void initWeb(@NonNull ViewGroup viewGroup, WebChromeClient webChromeClient, String url) {
        mAgentWeb = AgentWeb.with(mActivity)
                .setAgentWebParent(viewGroup, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(webChromeClient)
                .createAgentWeb()
                .ready()
                .go(url);
    }

    public void initWeb(@NonNull ViewGroup viewGroup, WebChromeClient webChromeClient, WebViewClient webViewClient, String url) {
        mAgentWeb = AgentWeb.with(mActivity)
                .setAgentWebParent(viewGroup, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(webChromeClient)
                .setWebViewClient(webViewClient)
                .createAgentWeb()
                .ready()
                .go(url);
    }

    public AgentWeb getAgentWeb() {
        return mAgentWeb;
    }

    public void loadUrl(String url) {
        if (mAgentWeb != null) {
            mAgentWeb.getUrlLoader().loadUrl(url);
        }
    }

    public void addJavaScriptInterface(String key, Object value) {
        if (mAgentWeb != null) {
            mAgentWeb.getJsInterfaceHolder().addJavaObject(key, value);
        }
    }

    public void loadJs(String method, String... params) {
        if (mAgentWeb != null) {
            mAgentWeb.getJsAccessEntrace().quickCallJs(method, params);
        }
    }

    public String getLastUrl() {
        WebBackForwardList backForwardList = mAgentWeb.getWebCreator().getWebView().copyBackForwardList();
        if (backForwardList != null && backForwardList.getSize() != 0) {
            //当前页面在历史队列中的位置
            int currentIndex = backForwardList.getCurrentIndex();
            WebHistoryItem historyItem = backForwardList.getItemAtIndex(currentIndex - 1);
            if (historyItem != null) {
                return historyItem.getUrl();
            }
        }
        return null;
    }

    public void onPause() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
    }

    public void onResume() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
    }

    public void onDestroy() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
    }
}
