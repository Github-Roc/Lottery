package com.peng.lottery.base;

import android.support.annotation.NonNull;

import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.mvp.injector.component.ActivityComponent;
import com.peng.lottery.mvp.injector.component.DaggerActivityComponent;
import com.peng.lottery.mvp.injector.module.ActivityModule;

import javax.inject.Inject;


/**
 * @author Peng
 * Created by Peng on 2017/12/12.
 * Activity的基类（MVP模式）
 */

public abstract class BaseActivity<P extends BasePresenter> extends SimpleBaseActivity implements IView {
    /**
     * 当前Activity所对应的业务操作类
     */
    @Inject
    protected P mPresenter;

    @Override
    protected void init() {
        super.init();
        initInject();
        if (mPresenter != null) {
            mPresenter.bindView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) {
            mPresenter.unbindView();
        }
    }

    @Override
    public void pageLoading() {

    }

    @Override
    public void pageEmpty() {

    }

    @Override
    public void pageError(int errorCode) {

    }

    @Override
    public void showToast(@NonNull String message) {
        ToastUtil.showToast(mActivity, message);
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .build();
    }

    /**
     * 初始化dagger2注入
     */
    protected abstract void initInject();
}

