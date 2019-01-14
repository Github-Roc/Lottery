package com.peng.lottery.mvp.injector.component;

import com.peng.lottery.base.BaseFragment;
import com.peng.lottery.mvp.injector.module.FragmentModule;

import dagger.Component;

@Component(modules = {FragmentModule.class})
public interface FragmentComponent {

    BaseFragment getFragment();

}
