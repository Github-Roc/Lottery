package com.peng.lottery.mvp.ui.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.blankj.rxbus.RxBus;
import com.peng.lottery.R;
import com.peng.lottery.app.config.AppConfig;
import com.peng.lottery.app.utils.Base64Util;
import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.base.BaseActivity;
import com.peng.lottery.mvp.model.db.bean.WebUrl;
import com.peng.lottery.mvp.presenter.activity.WebPresenter;
import com.peng.lottery.mvp.ui.fragment.WebCollectorFragment;

import butterknife.BindView;

import static com.peng.lottery.app.config.TipConfig.WEB_INPUT_URL_ERROR;
import static com.peng.lottery.app.config.TipConfig.WEB_URL_COPY_SUCCESS;

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
        if (TextUtils.isEmpty(url)) {
            url = mPresenter.getHomeUrl();
        }
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
        mWebTitle.setOnClickListener(v -> InputTextActivity.start(mActivity, "网址输入", "http://", AppConfig.REQUEST_INPUT_URL));
        mWebTitle.setOnLongClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            if (clipboardManager != null) {
                ClipData clipData = ClipData.newPlainText("webUrl", url);
                clipboardManager.setPrimaryClip(clipData);
                ToastUtil.showToast(mActivity, WEB_URL_COPY_SUCCESS);
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
            ContainerActivity.start(mActivity, WebCollectorFragment.class.getCanonicalName(), getString(R.string.title_web_collector));
        });
        RxBus.getDefault().subscribe(this, new RxBus.Callback<WebUrl>() {
            @Override
            public void onEvent(WebUrl webUrl) {
                getWebHelper().loadUrl(webUrl.getCollectionUrl());
            }
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == AppConfig.REQUEST_INPUT_URL) {
                String url = data.getStringExtra(InputTextActivity.INPUT_TEXT);
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    getWebHelper().loadUrl(url);
                } else {
                    ToastUtil.showToast(mActivity, WEB_INPUT_URL_ERROR);
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (getWebHelper().getAgentWeb().back()) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        RxBus.getDefault().unregister(this);
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
