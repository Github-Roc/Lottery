package com.peng.lottery.base;


import androidx.annotation.NonNull;

import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.mvp.injector.component.DaggerFragmentComponent;
import com.peng.lottery.mvp.injector.component.FragmentComponent;
import com.peng.lottery.mvp.injector.module.FragmentModule;

import javax.inject.Inject;

/**
 * @author Peng
 * Created by Peng on 2017/12/12.
 * Fragment的基类（MVP模式）
 */

public abstract class BaseFragment<P extends BasePresenter> extends SimpleBaseFragment implements IView {

    /** 当前Fragment所对应的业务操作类 */
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
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unbindView();
        }
    }

    @Override
    public void showToast(@NonNull String message) {
        ToastUtil.showToast(mActivity, message);
    }

    @Override
    public void showTip(String text) {
        showTipDialog(text, null);
    }

    @Override
    public void showLoading(String text) {
        showLoadingDialog(text);
    }

    @Override
    public void dismissLoading() {
        dismissLoadingDialog();
    }

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    /**
     * 初始化dagger2注入
     */
    protected abstract void initInject();
}
