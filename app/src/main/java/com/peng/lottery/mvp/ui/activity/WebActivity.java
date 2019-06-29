package com.peng.lottery.mvp.ui.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luck.picture.lib.rxbus2.Subscribe;
import com.luck.picture.lib.rxbus2.ThreadMode;
import com.peng.lottery.R;
import com.peng.lottery.app.config.AppConfig;
import com.peng.lottery.app.utils.Base64Util;
import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.base.BaseActivity;
import com.peng.lottery.mvp.model.db.bean.WebUrl;
import com.peng.lottery.mvp.presenter.activity.WebPresenter;

import butterknife.BindView;

/**
 * 加载html的web页
 *
 * @author Peng
 */
public class WebActivity extends BaseActivity<WebPresenter> {

    @BindView(R.id.web_layout)
    LinearLayout mWebLayout;
    @BindView(R.id.tv_web_title)
    TextView mWebTitle;
    @BindView(R.id.ll_web_share)
    LinearLayout mWebShare;
    @BindView(R.id.ll_web_finish)
    LinearLayout mWebFinish;
    @BindView(R.id.ll_web_back)
    LinearLayout mWebBack;
    @BindView(R.id.ll_web_forward)
    LinearLayout mWebForward;
    @BindView(R.id.ll_web_home)
    LinearLayout mWebHome;
    @BindView(R.id.ll_web_collection)
    LinearLayout mWebCollection;
    @BindView(R.id.ll_web_collector)
    LinearLayout mWebCollector;

    private String url;

    @Override
    protected void init() {
        super.init();

        url = getIntent().getStringExtra("url");
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
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
    protected void initListener() {
        getWebHelper().addUrlChangeListener(this::onUrlChange);
        mWebTitle.setOnLongClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            if (clipboardManager != null) {
                ClipData clipData = ClipData.newPlainText("webUrl", url);
                clipboardManager.setPrimaryClip(clipData);
                ToastUtil.showToast(mActivity, "链接地址复制成功！");
            }
            return true;
        });
        mWebShare.setOnClickListener(v -> {
            // 分享
            String url = getWebHelper().getWebView().getUrl();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, url);
            intent.putExtra(Intent.EXTRA_SUBJECT, AppConfig.APP_TAG);
            startActivity(intent);
        });
        mWebFinish.setOnClickListener(v -> {
            // 关闭页面
            finish();
        });
        mWebBack.setOnClickListener(v -> {
            // 返回
            getWebHelper().goBack();
        });
        mWebForward.setOnClickListener(v -> {
            // 前进
            getWebHelper().goForward();
        });
        mWebHome.setOnClickListener(v -> {
            // 首页
            String homeUrl = mPresenter.getHomeUrl();
            getWebHelper().loadUrl(homeUrl);
        });
        mWebCollection.setOnClickListener(v -> {
            // 收藏
            String url = getWebHelper().getWebView().getUrl();
            String title = getWebHelper().getWebView().getTitle();
            String icon = Base64Util.bitmapToBase64(getWebHelper().getWebView().getFavicon());
            ToastUtil.showToast(mActivity, mPresenter.collectionUrl(icon, title, url));
        });
        mWebCollector.setOnClickListener(v -> {
            //收藏夹
            Intent intent = new Intent(mActivity, WebCollectorActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected boolean enableInitToolBar() {
        return false;
    }

    @Override
    protected boolean enableSlidingFinish() {
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (getWebHelper().getAgentWeb().back()) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadUrl(WebUrl webUrl) {
        getWebHelper().loadUrl(webUrl.getCollectionUrl());
    }

    public void onUrlChange(String title, String url) {
        this.url = url;
        mWebTitle.setText(title);
    }

    public static void start(Activity activity, String url) {
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }
}
