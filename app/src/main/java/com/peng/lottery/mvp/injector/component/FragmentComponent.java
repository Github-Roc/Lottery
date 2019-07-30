package com.peng.lottery.mvp.injector.component;

import com.peng.lottery.base.BaseFragment;
import com.peng.lottery.mvp.injector.module.FragmentModule;
import com.peng.lottery.mvp.ui.fragment.AddLotteryFragment;
import com.peng.lottery.mvp.ui.fragment.CollectorFragment;
import com.peng.lottery.mvp.ui.fragment.MineLotteryFragment;
import com.peng.lottery.mvp.ui.fragment.SettingFragment;

import dagger.Component;

@Component(modules = {FragmentModule.class})
public interface FragmentComponent {

    BaseFragment getFragment();

    void inject(AddLotteryFragment fragment);

    void inject(MineLotteryFragment fragment);

    void inject(SettingFragment fragment);

    void inject(CollectorFragment fragment);
}
