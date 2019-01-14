package com.peng.lottery.mvp.injector.module;

import com.peng.lottery.base.BaseActivity;
import com.peng.lottery.mvp.model.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private BaseActivity mActivity;

    public ActivityModule(BaseActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    public BaseActivity provideActivity() {
        return mActivity;
    }

    @Provides
    DataManager provideRetrofitHelper() {
        return DataManager.getInstance();
    }
}
