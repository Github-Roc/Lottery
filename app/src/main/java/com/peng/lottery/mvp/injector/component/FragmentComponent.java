package com.peng.lottery.mvp.injector.component;

import com.peng.lottery.base.BaseFragment;
import com.peng.lottery.mvp.injector.module.FragmentModule;
import com.peng.lottery.mvp.ui.fragment.CreateListLotteryFragment;
import com.peng.lottery.mvp.ui.fragment.CreateSingleLotteryFragment;
import com.peng.lottery.mvp.ui.fragment.MainLotteryFragment;
import com.peng.lottery.mvp.ui.fragment.MineLotteryFragment;
import com.peng.lottery.mvp.ui.fragment.SettingFragment;
import com.peng.lottery.mvp.ui.fragment.WebCollectorFragment;

import dagger.Component;

@Component(modules = {FragmentModule.class})
public interface FragmentComponent {

    BaseFragment getFragment();

    void inject(MainLotteryFragment fragment);

    void inject(CreateSingleLotteryFragment fragment);

    void inject(CreateListLotteryFragment fragment);

    void inject(MineLotteryFragment fragment);

    void inject(SettingFragment fragment);

    void inject(WebCollectorFragment fragment);

}
