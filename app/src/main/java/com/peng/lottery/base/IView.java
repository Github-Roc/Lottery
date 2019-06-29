package com.peng.lottery.base;

/**
 * @author Peng
 * Created by Peng on 2017/12/12.
 * View层的根接口
 */
public interface IView {

    /**
     * 显示加载中页面
     */
    void pageLoading();

    /**
     * 显示空页面
     */
    void pageEmpty();

    /**
     * 显示错误页面
     *
     * @param errorCode 错误代码
     */
    void pageError(int errorCode);

    /**
     * 显示Toast
     *
     * @param message 提示内容
     */
    void showToast(String message);
}
