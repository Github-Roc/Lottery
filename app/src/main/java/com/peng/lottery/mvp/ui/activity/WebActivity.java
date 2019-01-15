package com.peng.lottery.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import com.peng.lottery.R;
import com.peng.lottery.base.SimpleBaseActivity;

import butterknife.BindView;

/**
 * 加载html的web页
 *
 * @author Peng
 */
public class WebActivity extends SimpleBaseActivity {

    @BindView(R.id.web_layout)
    LinearLayout mWebLayout;

    private String url;

    @Override
    protected void init() {
        super.init();

        url = getIntent().getStringExtra("url");
        if (TextUtils.isEmpty(url)) {
            finish();
        }
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        super.initView();

        getWebHelper().initWeb(mWebLayout, url);
    }

    @Override
    protected boolean enableSlidingFinish() {
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (getWebHelper().getAgentWeb().handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void start(Activity activity, String url) {
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }
}
