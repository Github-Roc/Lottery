package com.peng.lottery.base;


import android.support.annotation.NonNull;

import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.mvp.injector.component.DaggerFragmentComponent;
import com.peng.lottery.mvp.injector.component.FragmentComponent;
import com.peng.lottery.mvp.injector.module.FragmentModule;

import javax.inject.Inject;

import static com.peng.lottery.app.widget.dialog.LoadingDialog.DIALOG_TYPE_LOADING;
import static com.peng.lottery.app.widget.dialog.LoadingDialog.DIALOG_TYPE_PROGRESS;

/**
 * @author Peng
 * Created by Peng on 2017/12/12.
 * Fragment的基类（MVP模式）
 */

public abstract class BaseFragment<P extends BasePresenter> extends SimpleBaseFragment implements IView {

    /** 当前Fragment所对应的业务操作类 */
    @Inject protected P mPresenter;

    @Override
    protected void init() {
        super.init();
        initInject();
        if (mPresenter != null){
            mPresenter.bindView(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.unbindView();
        }
    }

    @Override
    public void showToast(@NonNull String message) {
        ToastUtil.showToast(mActivity, message);
    }

    @Override
    public void showLoadingDialog(@NonNull String text) {
        mActivity.showLoadingDialog(DIALOG_TYPE_LOADING, text);
    }

    @Override
    public void showProgressDialog(@NonNull String text) {
        mActivity.showLoadingDialog(DIALOG_TYPE_PROGRESS, text);
    }

    @Override
    public void hideDialog() {
        mActivity.dismissLoadingDialog();
    }

    protected FragmentComponent getFragmentComponent(){
        return DaggerFragmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    /**
     * 初始化dagger2注入
     */
    public abstract void initInject();
}
