package com.peng.lottery.base;

import android.support.annotation.NonNull;

import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.mvp.injector.component.ActivityComponent;
import com.peng.lottery.mvp.injector.component.DaggerActivityComponent;
import com.peng.lottery.mvp.injector.module.ActivityModule;

import javax.inject.Inject;

import static com.peng.lottery.app.widget.dialog.LoadingDialog.DIALOG_TYPE_LOADING;
import static com.peng.lottery.app.widget.dialog.LoadingDialog.DIALOG_TYPE_PROGRESS;


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
    public void showToast(@NonNull String message) {
        ToastUtil.showToast(this, message);
    }

    @Override
    public void showLoadingDialog(@NonNull String text) {
        showLoadingDialog(DIALOG_TYPE_LOADING, text);
    }

    @Override
    public void showProgressDialog(@NonNull String text) {
        showLoadingDialog(DIALOG_TYPE_PROGRESS, text);
    }

    @Override
    public void hideDialog() {
        dismissLoadingDialog();
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

