package com.peng.lottery.mvp.injector.module;

import com.peng.lottery.base.BaseFragment;
import com.peng.lottery.mvp.model.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    private BaseFragment mFragment;

    public FragmentModule(BaseFragment fragment) {
        this.mFragment = fragment;
    }

    @Provides
    public BaseFragment provideFragment() {
        return mFragment;
    }

    @Provides
    DataManager provideRetrofitHelper() {
        return DataManager.getInstance();
    }
}
