package com.peng.lottery.base;

/**
 * @author Peng
 * Created by Peng on 2017/12/12.
 * View层的根接口
 */
public interface IView {

    /**
     * 显示Toast
     *
     * @param message 提示内容
     */
    void showToast(String message);

    /**
     * 显示TipDialog
     *
     * @param text 提示内容
     */
    void showTip(String text);

    /**
     * 显示LoadingDialog
     *
     * @param type dialog类型
     * @param text 提示内容
     */
    void showLoading(int type, String text);

    /**
     * 隐藏LoadingDialog
     */
    void dismissLoading();
}
