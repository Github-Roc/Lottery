package com.peng.lottery.mvp.model.web;

import android.support.annotation.NonNull;

import com.peng.lottery.app.utils.LogUtil;
import com.peng.lottery.mvp.model.web.bean.BaseBean;

import io.reactivex.observers.DefaultObserver;

/**
 * @author Peng
 */

public abstract class LotteryObserver<D> extends DefaultObserver<BaseBean<D>> {

    @Override
    public void onNext(@NonNull BaseBean<D> baseBean) {
        LogUtil.i("HebeiHealthObserver", "onNext");
        if (baseBean.code == 1 && baseBean.data != null) {
            onSuccess(baseBean.data);
        } else {
            onError("数据有误!");
        }
    }

    @Override
    public void onError(@NonNull Throwable throwable) {
        LogUtil.e("HebeiHealthObserver", throwable.toString());
        String errorMsg = "服务器连接失败,请稍后重试!";
        if ("java.net.SocketTimeoutException".equals(throwable.toString())) {
            errorMsg = "连接超时,请稍后重试!";
        }
        onError(errorMsg);
    }

    @Override
    public void onComplete() {
        LogUtil.i("HebeiHealthObserver", "onComplete");
    }

    /**
     * 失败的回调
     *
     * @param errorMsg 错误信息
     */
    public abstract void onError(String errorMsg);

    /**
     * 成功的回调
     *
     * @param data 数据集合
     */
    public abstract void onSuccess(D data);
}
