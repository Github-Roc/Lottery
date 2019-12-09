package com.peng.lottery.app.helper;

import androidx.annotation.NonNull;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebSettingsImpl;
import com.peng.lottery.app.listener.WebUrlChangeListener;
import com.peng.lottery.base.SimpleBaseActivity;


/**
 * AgentWeb封装
 *
 * @author Peng
 */
public class AgentWebHelper {
    private AgentWeb mAgentWeb;
    private SimpleBaseActivity mActivity;
    private WebUrlChangeListener mUrlChangeListener;
    private WebChromeClient mWebChromeClient = new WebChromeClient() {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (mUrlChangeListener != null) {
                String url = view.getUrl();
                mUrlChangeListener.onUrlChange(title, url);
            }
        }
    };

    public AgentWebHelper(SimpleBaseActivity activity) {
        this.mActivity = activity;
    }

    public void initWeb(@NonNull ViewGroup viewGroup, String url) {
        mAgentWeb = AgentWeb.with(mActivity)
                .setAgentWebParent(viewGroup, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setAgentWebWebSettings(AgentWebSettingsImpl.getInstance())
                .setWebChromeClient(mWebChromeClient)
                .createAgentWeb()
                .ready()
                .go(url);
    }

    public AgentWeb getAgentWeb() {
        return mAgentWeb;
    }

    public WebView getWebView() {
        return mAgentWeb.getWebCreator().getWebView();
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

    public void goBack() {
        getWebView().goBack();
    }

    public void goForward() {
        getWebView().goForward();
    }

    public void reload() {
        getWebView().reload();
    }

    public void addUrlChangeListener(WebUrlChangeListener listener) {
        this.mUrlChangeListener = listener;
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
