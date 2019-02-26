package com.peng.lottery.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.widget.TextView;

import com.peng.lottery.R;
import com.peng.lottery.app.utils.LogUtil;
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

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        super.initView();

        mActivityTitle.setText(R.string.title_about);
        tvAboutVersion.setText("Lottery\t\t" + getVersionName());
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

    private String getVersionName() {
        String versionName = "";
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.e(e.getMessage());
        }
        return versionName;
    }

}
