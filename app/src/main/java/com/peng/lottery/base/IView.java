package com.peng.lottery.base;

import android.support.annotation.NonNull;

/**
 * @author Peng
 * Created by Peng on 2017/12/12.
 * View层的根接口
 */
public interface IView {

    /**
     * 显示一个Toast
     * @param message toast内容
     */
    void showToast(@NonNull String message);

    /**
     * 显示加载中Dialog
     */
    void showLoadingDialog(@NonNull String text);

    /**
     * 显示进度条Dialog
     */
    void showProgressDialog(@NonNull String text);

    /**
     * 隐藏Dialog
     */
    void hideDialog();
}
