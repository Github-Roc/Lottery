package com.peng.lottery.mvp.injector.component;

import com.peng.lottery.base.BaseActivity;
import com.peng.lottery.mvp.injector.module.ActivityModule;

import dagger.Component;

@Component(modules = {ActivityModule.class})
public interface ActivityComponent {

    BaseActivity getActivity();

}
