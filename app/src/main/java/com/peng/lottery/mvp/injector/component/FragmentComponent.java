package com.peng.lottery.mvp.injector.component;

import com.peng.lottery.base.BaseFragment;
import com.peng.lottery.mvp.injector.module.FragmentModule;
import com.peng.lottery.mvp.ui.fragment.CollectorFragment;
import com.peng.lottery.mvp.ui.fragment.DaLeDouFragment;
import com.peng.lottery.mvp.ui.fragment.MineLotteryFragment;
import com.peng.lottery.mvp.ui.fragment.PkShiFragment;
import com.peng.lottery.mvp.ui.fragment.SettingFragment;
import com.peng.lottery.mvp.ui.fragment.ShiYiXuanWuFragment;
import com.peng.lottery.mvp.ui.fragment.ShuangSeQiuFragment;

import dagger.Component;

@Component(modules = {FragmentModule.class})
public interface FragmentComponent {

    BaseFragment getFragment();

    void inject(ShiYiXuanWuFragment fragment);

    void inject(MineLotteryFragment fragment);

    void inject(SettingFragment fragment);

    void inject(CollectorFragment fragment);
}
