package com.peng.lottery.mvp.ui.activity;

import com.peng.lottery.R;
import com.peng.lottery.base.SimpleBaseActivity;

public class SettingActivity extends SimpleBaseActivity {

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        super.initView();

        mActivityTitle.setText(R.string.title_setting);
    }

    @Override
    protected boolean enableSlidingFinish() {
        return true;
    }
}
