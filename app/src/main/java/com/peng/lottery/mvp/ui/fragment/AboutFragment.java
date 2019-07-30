package com.peng.lottery.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.widget.TextView;

import com.peng.lottery.R;
import com.peng.lottery.app.config.AppConfig;
import com.peng.lottery.app.utils.LogUtil;
import com.peng.lottery.base.SimpleBaseFragment;
import com.peng.lottery.mvp.ui.activity.WebActivity;

import butterknife.BindView;

public class AboutFragment extends SimpleBaseFragment {
    @BindView(R.id.tv_about_project_url)
    TextView tvAboutProjectUrl;
    @BindView(R.id.tv_about_author_url)
    TextView tvAboutAuthorUrl;
    @BindView(R.id.tv_about_version)
    TextView tvAboutVersion;

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_about;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        super.initView();

        tvAboutVersion.setText(AppConfig.APP_TAG + "\t\t" + getVersionName());
    }

    @Override
    protected void initListener() {
        tvAboutProjectUrl.setOnClickListener(v -> WebActivity.start(mActivity, "https://github.com/AndroidPengPeng/Lottery"));
        tvAboutAuthorUrl.setOnClickListener(v -> WebActivity.start(mActivity, "http://pengblog.club/"));
    }

    private String getVersionName() {
        String versionName = "";
        try {
            versionName = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.e(e.getMessage());
        }
        return versionName;
    }
}

