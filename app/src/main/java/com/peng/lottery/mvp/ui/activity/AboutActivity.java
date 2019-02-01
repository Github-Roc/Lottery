package com.peng.lottery.mvp.ui.activity;

import android.widget.TextView;

import com.peng.lottery.R;
import com.peng.lottery.base.SimpleBaseActivity;

import butterknife.BindView;

public class AboutActivity extends SimpleBaseActivity {

    @BindView(R.id.tv_about_project_url)
    TextView tvAboutProjectUrl;
    @BindView(R.id.tv_about_author_url)
    TextView tvAboutAuthorUrl;
    @BindView(R.id.tv_about_version)
    TextView tvAboutVersion;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        super.initView();

        mActivityTitle.setText(R.string.title_about);
    }

    @Override
    protected void initListener() {
        tvAboutProjectUrl.setOnClickListener(v -> WebActivity.start(mActivity, "https://github.com/AndroidPengPeng/Lottery"));
        tvAboutAuthorUrl.setOnClickListener(v -> WebActivity.start(mActivity, "http://pengblog.club/"));
    }

    @Override
    protected boolean enableSlidingFinish() {
        return true;
    }

}
