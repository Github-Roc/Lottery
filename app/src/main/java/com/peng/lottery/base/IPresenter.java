package com.peng.lottery.base;

/**
 * @author Peng
 * Created by Peng on 2017/12/12.
 * Presenter层的根接口
 */

public interface IPresenter<V extends IView> {

    /**
     * Activity创建时与Presenter绑定
     */
    void bindView(V view);

    /**
     * 解除Activity与Presenter的绑定
     */
    void unbindView();
}
