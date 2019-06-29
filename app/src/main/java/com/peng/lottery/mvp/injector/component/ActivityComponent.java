package com.peng.lottery.mvp.injector.component;

import com.peng.lottery.base.BaseActivity;
import com.peng.lottery.mvp.injector.module.ActivityModule;
import com.peng.lottery.mvp.ui.activity.AddLotteryActivity;
import com.peng.lottery.mvp.ui.activity.MineLotteryActivity;
import com.peng.lottery.mvp.ui.activity.SettingActivity;
import com.peng.lottery.mvp.ui.activity.SplashActivity;
import com.peng.lottery.mvp.ui.activity.WebActivity;
import com.peng.lottery.mvp.ui.activity.WebCollectorActivity;

import dagger.Component;

@Component(modules = {ActivityModule.class})
public interface ActivityComponent {

    BaseActivity getActivity();

    void inject(SplashActivity activity);

    void inject(SettingActivity activity);

    void inject(MineLotteryActivity activity);

    void inject(AddLotteryActivity activity);

    void inject(WebActivity activity);

    void inject(WebCollectorActivity activity);
}
